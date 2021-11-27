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




    route.get('/tagauto/i', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var resu;
        const connection = await pool.getConnection(async conn => conn);
        try {
            var sql = 'SELECT i.tagno,it.tag_name, COUNT(*) as cnt FROM iPost_iTag as i join iTag AS it ON i.tagno = it.tagno GROUP BY tagno HAVING COUNT(*) > 0 ORDER BY it.tag_name';
            let result = await connection.execute(sql);
            if (result[0].affectedRows == 0) {
                throw new Error("cant make tagauto");
                resultCode = 404;
                message = 'cant make tagauto';
                console.log(message);
            } else {
                resultCode = 200;
                message = 'make autotag.';

                resu = result[0];
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
        res.json(resu);


    });
    route.get('/tagauto/n', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var resu;
        const connection = await pool.getConnection(async conn => conn);
        try {
            var sql = 'SELECT i.tagno,it.tag_name, COUNT(*) as cnt FROM Post_Tag as i join Tag AS it ON i.tagno = it.tagno GROUP BY tagno HAVING COUNT(*) > 0 ORDER BY it.tag_name';
            let result = await connection.execute(sql);
            if (result[0].affectedRows == 0) {
                throw new Error("cant make tagauto");
                resultCode = 404;
                message = 'cant make tagauto';
                console.log(message);
            } else {
                resultCode = 200;
                message = 'make autotag.';
                resu = result[0];
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
        res.json(resu);


    });

    route.get('/favoritetag/n', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var resu;
        const connection = await pool.getConnection(async conn => conn);
        try {
            var sql = 'SELECT i.tagno,it.tag_name, COUNT(*) as cnt FROM Post_Tag as i join Tag AS it ON i.tagno = it.tagno GROUP BY tagno HAVING COUNT(*) > 0 ORDER BY COUNT(*) DESC limit 10';
            let result = await connection.execute(sql);
            if (result[0].affectedRows == 0) {
                throw new Error("cant make tagauto");
                resultCode = 404;
                message = 'cant make tagauto';
                console.log(message);
            } else {
                resultCode = 200;
                message = 'make autotag.';
                resu = result[0];
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
        res.json(resu);


    });
    route.get('/favoritetag/i', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var resu;
        const connection = await pool.getConnection(async conn => conn);
        try {
            var sql = 'SELECT i.tagno,it.tag_name, COUNT(*) as cnt FROM iPost_iTag as i join iTag AS it ON i.tagno = it.tagno GROUP BY tagno HAVING COUNT(*) > 0 ORDER BY COUNT(*) DESC limit 10';
            let result = await connection.execute(sql);
            if (result[0].affectedRows == 0) {
                throw new Error("cant make tagauto");
                resultCode = 404;
                message = 'cant make tagauto';
                console.log(message);
            } else {
                resultCode = 200;
                message = 'make autotag.';
                resu = result[0];
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
        res.json(resu);


    });

    route.post('/addlike', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var UserID = req.body.UserID;
        var postno = req.body.postno;
        const connection = await pool.getConnection(async conn => conn);
        try {
            var sql = 'SELECT count(*) as cnt FROM server.iLike where postno = ? and UserID = ?';
            var parms = [postno, UserID]
            let result = await connection.execute(sql, parms);
            if (result[0].affectedRows == 0) {
                throw new Error("cant add likes");
                resultCode = 404;
                message = 'cant make tagauto';
                console.log(message);
            } else {

                if (result[0][0].cnt == 0) {
                    var sql99 = 'INSERT INTO  iLike(postno, UserID) VALUES (?, ?)';
                    var parms99 = [postno, UserID]
                    let result99 = await connection.execute(sql99, parms99);
                    if (result[0].affectedRows == 0) {
                        throw new Error("cant make tagauto");
                        resultCode = 404;
                        message = 'cant make tagauto';
                        console.log(message);
                    } else {
                        var sql2 = 'SELECT count(*) as cnt FROM server.iLike where postno = ? ';
                        var parms2 = [postno]
                        let result2 = await connection.execute(sql2, parms2);
                        if (result2[0].affectedRows == 0) {
                            throw new Error("cant add likes");
                            resultCode = '201';
                            message = 'cant make tagauto';
                            console.log(message);
                        } else {
                            resultCode = '201';
                            message = result2[0][0].cnt;
                        }
                    }
                }
                else {
                    var sql3 = 'SELECT count(*) as cnt FROM server.iLike where postno = ? ';
                    var parms3 = [postno]
                    let result3 = await connection.execute(sql3, parms3);
                    if (result3[0].affectedRows == 0) {
                        throw new Error("cant add likes");
                        resultCode = '201';
                        message = 'cant make tagauto';
                        console.log(message);
                    } else {
                        resultCode = '201';
                        message = result3[0][0].cnt;
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
            connection.release();
        }
        res.json({
            'code': resultCode,
            'message': message
        });
    });
    route.post('/dellike', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var UserID = req.body.UserID;
        var postno = req.body.postno;
        const connection = await pool.getConnection(async conn => conn);
        try {
            var sql = 'SELECT count(*) as cnt FROM server.iLike where postno = ? and UserID = ?';
            var parms = [postno, UserID]
            let result = await connection.execute(sql, parms);
            if (result[0].affectedRows == 0) {
                throw new Error("cant add likes");
                resultCode = 404;
                message = 'cant make tagauto';
                console.log(message);
            } else {

                if (result[0][0].cnt != 0) {
                    var sql97 = 'DELETE FROM iLike where postno = ? and UserID = ?';
                    var parms97 = [postno, UserID]
                    let result97 = await connection.execute(sql97, parms97);
                    if (result97[0].affectedRows == 0) {
                        throw new Error("cant make tagauto");
                        resultCode = 404;
                        message = 'cant make tagauto';
                        console.log(message);
                    } else {
                        var sql2 = 'SELECT count(*) as cnt FROM server.iLike where postno = ? ';
                        var parms2 = [postno]
                        let result2 = await connection.execute(sql2, parms2);
                        if (result2[0].affectedRows == 0) {
                            throw new Error("cant add likes");
                            resultCode = '200';
                            message = 'cant make tagauto';
                            console.log(message);
                        } else {
                            resultCode = '200';
                            message = result2[0][0].cnt;
                        }
                    }
                }
                else {
                    var sql3 = 'SELECT count(*) as cnt FROM server.iLike where postno = ? ';
                    var parms3 = [postno]
                    let result3 = await connection.execute(sql3, parms3);
                    if (result3[0].affectedRows == 0) {
                        throw new Error("cant add likes");
                        resultCode = '201';
                        message = 'cant make tagauto';
                        console.log(message);
                    } else {
                        resultCode = '201';
                        message = result3[0][0].cnt;
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
            connection.release();
        }
        res.json({
            'code': resultCode,
            'message': message
        });
    });
    route.post('/chklike', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var UserID = req.body.UserID;
        var postno = req.body.postno;
        const connection = await pool.getConnection(async conn => conn);
        try {
            var sql = 'SELECT count(*) as cnt FROM server.iLike where postno = ? and UserID = ?';
            var parms = [postno, UserID]
            let result = await connection.execute(sql, parms);
            if (result[0].affectedRows == 0) {
                throw new Error("cant add likes");
                resultCode = 404;
                message = 'cant make tagauto';
                console.log(message);
            } else {

                if (result[0][0].cnt == 0) {
                    resultCode = '200';
                }
                else {
                    resultCode = '201';
                }

            }
            var sql2 = 'SELECT count(*) as cnt FROM server.iLike where postno = ? ';
            var parms2 = [postno]
            let result2 = await connection.execute(sql2, parms2);
            if (result[0].affectedRows == 0) {
                throw new Error("cant add likes");
                resultCode = 404;
                message = 'cant make tagauto';
                console.log(message);
            } else {
                message = result2[0][0].cnt;
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

    route.get('/cntlike', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var resu;

        const connection = await pool.getConnection(async conn => conn);
        try {
            var sql = 'SELECT postno, count(*) as cnt FROM server.iLike group by postno ORDER BY cnt DESC limit 3';
            let result = await connection.execute(sql);
            if (result[0].affectedRows == 0) {
                throw new Error("cant add likes");
                resultCode = 404;
                message = 'cant make tagauto';
                console.log(message);
            } else {
                resu = result[0];
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
        res.json(resu);


    });

    return route;
};

