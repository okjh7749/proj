module.exports = function (app) {
    var express = require('express');
    var route = express.Router();
    var bodyParser = require('body-parser');
    var moment = require('moment');
    require('moment-timezone');
    moment.tz.setDefault("Asia/Seoul");
    var date = moment().format('YYYY-MM-DD HH:mm:ss');
    var async = require("async");
    route.use(bodyParser.json());
    route.use(bodyParser.urlencoded({ extended: true }));
    var mysql = require('mysql2/promise');
    const pool = mysql.createPool({
        host: "ec2-3-36-182-213.ap-northeast-2.compute.amazonaws.com",
        user: "jh",
        database: "server",
        password: "ckddlfwnd3349!",
        port: 3306
    });




    route.post('/addclass', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        console.log(req.body);

        var title = req.body.title;
        var content = req.body.content;
        var writer = req.body.writer;
        var addtime = req.body.addtime;
        var reservation_date = moment().add(addtime, 'h').format('YYYY-MM-DD HH:mm:ss');
        console.log(reservation_date);
        var latitue = req.body.latitue;
        var longitude = req.body.longitude;
        var postno;
        var member = writer;
        try {
            var sql = 'INSERT INTO class_reservation (title, content,writer,reservation_date,latitue,longitude) VALUES (?, ?, ?, ?,?,?)';
            var params = [title, content, writer, reservation_date, latitue, longitude];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                throw new Error("cantinsert class");
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            } else {
                resultCode = 200;
                message = 'post complate.';
                var sql1 = 'SELECT LAST_INSERT_ID() as lid';
                //추가된 포스트의 고유 id 를 가져와 저장
                let result1 = await connection.execute(sql1);
                if (result1[0].affectedRows == 0) {
                    throw new Error("cantinsert class");
                    resultCode = 404;
                    message = 'write fail';
                    console.log(message);
                } else {
                    postno = result1[0][0].lid;
                }
                var sql2 = 'INSERT INTO class_member(postno,member) VALUES (?,?)';
                var params2 = [postno, member];
                let result2 = await connection.execute(sql2, params2);
                if (result2[0].affectedRows == 0) {
                    throw new Error("cantinsert class_member");
                    resultCode = 404;
                    message = 'write fail';
                    console.log(message);
                } else {
                    resultCode = 200;
                    message = 'write complate';
                    console.log(message);
                }

            }
        }
        catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': "등록 실패"
            });
            throw err;
        } finally {
            connection.release();
        }
        res.json({
            'code': resultCode,
            'message': message
        });


    });

    route.post('/joinclass', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        console.log(req.body);

        var postno = req.body.postno;
        var member = req.body.userID;
        
        try {
            var sql = 'SELECT COUNT(*) as count FROM class_member WHERE postno = ? AND member = "'+member +'"';
            var params = [postno];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                throw new Error("cantinsert class");
                resultCode = 404;
                message = 'join fail';
                console.log(message);
            } else {
                if (result[0][0].count != 0) {
                    resultCode = 404;
                    message = 'already join this class';
                }
                else {
                    var sql2 = 'INSERT INTO class_member (postno, member) VALUES (?,?)';
                    var params2 = [postno, member];
                    let result2 = await connection.execute(sql2, params2);
                    if (result2[0].affectedRows == 0) {
                        throw new Error("cantinsert class");
                        resultCode = 404;
                        message = 'join fail';
                        console.log(message);
                    } else {
                        resultCode = 200;
                        message = 'join suc';
                    }
                }
            }
           
        }
        catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': "등록 실패"
            });
            throw err;
        } finally {
        }
        res.json({
            'code': resultCode,
            'message': message
        });


    });

    route.post('/classmember', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var resu = [];
        const connection = await pool.getConnection(async conn => conn);
        console.log(req.body);

        var postno = req.body.postno;
        var resu;
        try {
            var sql3 = 'SELECT member FROM class_member WHERE postno = ?';
            var params3 = [postno];
            let result3 = await connection.execute(sql3, params3);
            if (result3[0].affectedRows == 0) {
                throw new Error("cantinsert class");
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            } else {
                resultCode = 200;
                message = 'post complate.';
                var size = result3[0].length;
                for (var i = 0; i < size; i++) {
                    resu[i] = result3[0][i].member;
                 }
                
                console.log(resu);
            }
        }
        catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': "등록 실패"
            });
            throw err;
        } finally {
            connection.release();
        }
        res.send(resu);


    });
    return route;
};