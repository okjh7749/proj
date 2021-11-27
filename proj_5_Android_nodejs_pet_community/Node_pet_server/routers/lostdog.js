module.exports = function (app) {
    var express = require('express');
    var route = express.Router();
    var bodyParser = require('body-parser');
    var moment = require('moment');
    const spawn = require('child_process').spawn;

    const result = spawn('python', ['파이썬파일.py']);

    const multer = require("multer");
    const path = require("path");

    var storage = multer.diskStorage({
        destination: function (req, file, cb) {
            cb(null, "public/images/");
        },
        filename: function (req, file, cb) {
            const ext = path.extname(file.originalname);
            cb(null, path.basename(file.originalname, ext) + "-" + Date.now() + ext);
        },
    });
    var upload = multer({ storage: storage });

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


    route.post('/addfinda', upload.single("image"), async (req, res) => {
        
        const connection = await pool.getConnection(async conn => conn);
        console.log(req.body);
        var rdata;
        var title = req.body.title;
        var writer = req.body.writer;
        var content = req.body.content;
        var write_date = date;
        var latitue = req.body.latitue;
        var longitude = req.body.longitude;

        var postno;
        console.log(req.file);
        var img = '/images/' + req.file.filename;
        console.log(img);
        resultCode = 404;
        message = 'fail';
        
        try {
            //게시글 등록
            var sql = 'INSERT INTO find_animal (title, content,writer,write_date,img,latitue,longitude) VALUES (?, ?, ?, ?, ?,?,?)';
            var params = [title, content, writer, write_date, img, latitue, longitude];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                throw new Error("affectedRows is zero where post");
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                var sql1 = 'SELECT LAST_INSERT_ID() as lid';
                let result1 = await connection.execute(sql1);
                if (result1[0].affectedRows == 0) {
                    throw new Error("affectedRows is zero where menu posttable");
                    resultCode = 404;
                    message = 'get postno fail';
                    console.log(message);

                }
                else {

                    postno = result1[0][0].lid;
                    console.log(postno);

                    const python = spawn('python', ['test.py', req.file.filename]);
                    python.stdout.on('data', function (data) {
                        rdata = data.toString();
                        console.log(rdata.replace(/\-/g, ''));
                        var sql8 = 'UPDATE find_animal SET kind =? WHERE postno = ?';
                        var params8 = [rdata.replace("\r\n", " ").trim(),postno];
                        let result8 = connection.execute(sql8,params8);
                        console.log(params8);
                        if (result1[0].affectedRows == 0) {
                            throw new Error("affectedRows is zero where menu posttable");
                            resultCode = 404;
                            message = 'get postno fail';
                            console.log(message);

                        }
                        else {
                            console.log(result8);
                        }

                    });
                }

                
            }
            
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            
            connection.release();
        }
        res.json({
            'code': resultCode,
            'message': message,
            file: null, files: req.files
        });
    });
    route.post('/addfindm', upload.single("image"), async (req, res) => {

        const connection = await pool.getConnection(async conn => conn);
        console.log(req.body);
        var rdata;
        var title = req.body.title;
        var writer = req.body.writer;
        var content = req.body.content;
        var write_date = date;
        var latitue = req.body.latitue;
        var longitude = req.body.longitude;

        var postno;
        console.log(req.file);
        var img = '/images/' + req.file.filename;
        console.log(img);
        resultCode = 404;
        message = 'fail';

        try {
            //게시글 등록
            var sql = 'INSERT INTO find_master (title, content,writer,write_date,img,latitue,longitude) VALUES (?, ?, ?, ?, ?,?,?)';
            var params = [title, content, writer, write_date, img, latitue, longitude];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                throw new Error("affectedRows is zero where post");
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                var sql1 = 'SELECT LAST_INSERT_ID() as lid';
                let result1 = await connection.execute(sql1);
                if (result1[0].affectedRows == 0) {
                    throw new Error("affectedRows is zero where menu posttable");
                    resultCode = 404;
                    message = 'get postno fail';
                    console.log(message);

                }
                else {

                    postno = result1[0][0].lid;
                    console.log(postno);

                    const python = spawn('python', ['test.py', req.file.filename]);
                    python.stdout.on('data', function (data) {
                        rdata = data.toString();
                        console.log(rdata.replace(/\-/g, ''));
                        var sql8 = 'UPDATE find_animal SET kind =? WHERE postno = ?';
                        var params8 = [rdata.replace("\r\n", " ").trim(), postno];
                        let result8 = connection.execute(sql8, params8);
                        console.log(params8);
                        if (result1[0].affectedRows == 0) {
                            throw new Error("affectedRows is zero where menu posttable");
                            resultCode = 404;
                            message = 'get postno fail';
                            console.log(message);

                        }
                        else {
                            console.log(result8);
                        }

                    });
                }


            }

        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {

            connection.release();
        }
        res.json({
            'code': resultCode,
            'message': message,
            file: null, files: req.files
        });
    });
    return route;
};