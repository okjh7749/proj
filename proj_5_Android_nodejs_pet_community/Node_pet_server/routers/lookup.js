module.exports = function (app) {
    var express = require('express');
    var route = express.Router();
    var bodyParser = require('body-parser');
    var moment = require('moment');

    require('moment-timezone');
    moment.tz.setDefault("Asia/Seoul");
    var date = moment().format('YYYY-MM-DD HH:mm:ss');
    var async = require("async");


    var mysql = require('mysql2/promise');
    const pool = mysql.createPool({
        host: "ec2-3-36-182-213.ap-northeast-2.compute.amazonaws.com",
        user: "jh",
        database: "server",
        password: "ckddlfwnd3349!",
        port: 3306
    });


    route.post('/lookuppost', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var resu;

        var postno = req.params.postno;
        var dn = req.body.data_n;
        var pn = req.body.page_n;
        pn = (pn - 1) * dn;
        try {

            //게시글 등록
            var sql = 'SELECT * FROM Post ORDER BY postno DESC LIMIT ?,?';
            var params = [pn,dn];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                resu = result[0];
            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            //connection.release();
        }
        res.json(resu);
    });
    route.get('/lookuppost/:postno', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var postno = req.params.postno;
        try {
            //게시글 등록
            var sql = 'SELECT * FROM Post WHERE postno = ?';
            var params = [postno];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                throw new Error("affectedRows is zero where post");
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                var title = result[0][0].title;
                var content = result[0][0].content;
                var writer = result[0][0].writer;
                var write_date = result[0][0].write_date;
            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            //connection.release();
        }
        res.json({
            'title': title,
            'content': content,
            'writer': writer,
            'write_date': write_date,
            file: null, files: req.files
        });
    });
    route.post('/lookupposti', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var resu;
        var dn = req.body.data_n;
        var pn = req.body.page_n;
        pn = (pn - 1) * dn;
        try {

            //게시글 등록
            var sql = 'SELECT * FROM iPost ORDER BY postno DESC LIMIT ?,?';
            var params = [pn, dn];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                resu = result[0];
            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            //connection.release();
        }
        res.json(resu);
    });
    route.get('/lookupposti/:postno', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var postno = req.params.postno;
        try {
            //게시글 등록
            var sql = 'SELECT * FROM iPost WHERE postno = ?';
            var params = [postno];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                throw new Error("affectedRows is zero where post");
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                console.log(result[0][0]);
                var title = result[0][0].title;
                var content = result[0][0].content;
                var writer = result[0][0].writer;
                var write_date = result[0][0].write_date;
                var img_path = result[0][0].img;
                
            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            //connection.release();
        }
        res.json({
            'title': title,
            'content': content,
            'writer': writer,
            'write_date': write_date,
            'img_path': img_path
        });
    });

    route.post('/lookuppost/sort', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var sort = req.body.sort;
        var dn = req.body.data_n;
        var pn = req.body.page_n;
        pn = (pn - 1) * dn;
        var resu;
        try {

            //게시글 등록
            var sql = 'SELECT * FROM iPost ORDER BY (?) DESC LIMIT ?,?';
            var params = [sort, pn, dn];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                resultCode = 404;
                message = 'write fail';
                console.log(message);

            }
            else {
                resu = result[0];
            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            //connection.release();
        }
        res.json(resu);
    });
    route.post('/lookuppost/tag', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var matches = [];
        var plist = [];
        const connection = await pool.getConnection(async conn => conn);
        var resu;
        var dn = req.body.data_n;
        var pn = req.body.page_n;
        pn = (pn - 1) * dn;
        var i = 0;
        var tag = req.body.tag;
        tag = tag.split(/(#[^#\s]+)/g).map(v => {
            if (v.match('#')) {
                matches[i++] = v;
            }
            return v;
        });
        for (var j in matches) {
            matches[j] = JSON.stringify(matches[j]);
        }
        var size = matches.length
        try {
            var sql2 = "SELECT post.* FROM Post as post INNER JOIN(Post_Tag AS pt INNER JOIN Tag AS tag ON tag.tagno = pt.tagno) ON post.postno = pt.postno AND tag.tag_name IN(" + matches.join() +")  GROUP BY postno HAVING COUNT(DISTINCT tag.tag_name) = ?  LIMIT ?,? ;";
            var params2 = [size,pn,dn];
            console.log(matches.join());
            console.log(size);
            let result2 = await connection.execute(sql2,params2);
            if (result2[0].affectedRows == 0) {
                throw new Error("affectedRows is zero where menu tag");
                resultCode = 404;
                message = 'check tag fail';
                console.log(message);
            }
            //만약 현재 진행중인 테그가테이블에 없다면
            else {
                console.log(result2[0]);
                resu = result2[0];
            }
            //현재 진행중인 테그가 테그 테이블에 존재했었다면
            
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': "등록 실패"
            });
            throw err;

        } finally {

        }
        res.json(resu);
    });
    route.post('/lookuppost/find', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var kind = req.body.kind;
        var what = req.body.what;
        var dn = req.body.data_n;
        var pn = req.body.page_n;
        pn = (pn - 1) * dn;
        var resu;
        await connection.beginTransaction();
        try {
            //게시글 등록
            var sql = 'SELECT * FROM Post WHERE ' +kind+' = ? LIMIT ?,?';
            var params = [what,pn, dn];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                resultCode = 404;
                message = 'write fail';
                console.log(message);

            }
            else {
                console.log(sql);
                resu = result[0];
            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            //connection.release();
        }
        res.json(resu);
    });
    route.post('/lookupposti/tag', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        var matches = [];
        var plist = [];
        const connection = await pool.getConnection(async conn => conn);
        var resu;
        var dn = req.body.data_n;
        var pn = req.body.page_n;
        pn = (pn - 1) * dn;
        var i = 0;
        await connection.beginTransaction();
        var tag = req.body.tag;
        tag = tag.split(/(#[^#\s]+)/g).map(v => {
            if (v.match('#')) {
                matches[i++] = v;
            }
            return v;
        });
        for (var j in matches) {
            matches[j] = JSON.stringify(matches[j]);
        }
        var size = matches.length
        try {
            var sql2 = "SELECT post.* FROM iPost as post INNER JOIN(iPost_iTag AS pt INNER JOIN iTag AS tag ON tag.tagno = pt.tagno) ON post.postno = pt.postno AND tag.tag_name IN(" + matches.join() + ")  GROUP BY postno HAVING COUNT(DISTINCT tag.tag_name) = ?  LIMIT ?,? ;";
            var params2 = [size, pn, dn];
            console.log(matches.join());
            console.log(size);
            let result2 = await connection.execute(sql2, params2);
            if (result2[0].affectedRows == 0) {
                throw new Error("affectedRows is zero where menu tag");
                resultCode = 404;
                message = 'check tag fail';
                console.log(message);
            }
            //만약 현재 진행중인 테그가테이블에 없다면
            else {
                console.log(result2[0]);
                resu = result2[0];
            }
            //현재 진행중인 테그가 테그 테이블에 존재했었다면

        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': "등록 실패"
            });
            throw err;

        } finally {

        }
        res.json(resu);
    });
    route.post('/lookupposti/find', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var sort = req.body.sort;
        var kind = req.body.kind;
        var what = req.body.what;
        var dn = req.body.data_n;
        var pn = req.body.page_n;
        pn = (pn - 1) * dn;
        var resu;
        await connection.beginTransaction();
        try {
            //게시글 등록
            var sql = 'SELECT * FROM iPost WHERE ' + kind + ' = ?  ORDER BY (?) DESC LIMIT ?,?';
            var params = [what,sort,pn, dn];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                resultCode = 404;
                message = 'write fail';
                console.log(message);

            }
            else {
                console.log(sql);
                resu = result[0];
            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            //connection.release();
        }
        res.json(resu);
    });

    route.post('/lookupfinda', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var kind = req.body.kind;
        var who = req.body.who;
        var dn = req.body.data_n;
        var pn = req.body.page_n;
        var latitue = req.body.latitue;
        var longitude = req.body.longitude;
        pn = (pn - 1) * dn;
        var resu;
        await connection.beginTransaction();
        try {

            if (who == "animal") {
                var sql = 'SELECT *,(6371 * acos(cos(radians(?)) * cos(radians(fa.latitue)) * cos(radians(fa.longitude) - radians(?)) + sin(radians(?)) * sin(radians(fa.latitue)))) AS distance FROM find_animal as fa WHERE fa.kind = "'+kind+'" HAVING distance <= 1.5 ORDER BY distance LIMIT ?, ?'
            }
            else if (who == "master") {
                var sql = 'SELECT *,(6371 * acos(cos(radians(?)) * cos(radians(fa.latitue)) * cos(radians(fa.longitude) - radians(?)) + sin(radians(?)) * sin(radians(fa.latitue)))) AS distance FROM find_master as fa WHERE fa.kind ="'+kind+'" HAVING distance <= 1.5 ORDER BY distance LIMIT ?, ?'
            }
            //게시글 등록
            var params = [latitue, longitude, latitue, pn, dn];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                console.log(result[0]);
                resu = result[0];
            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;
        } finally {
        }
        res.json(resu);
    });
    route.get('/lookupfinda/:postno', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var postno = req.params.postno;
        await connection.beginTransaction();
        try {
            //게시글 등록
            var sql = 'SELECT * FROM find_animal WHERE postno = ?';
            var params = [postno];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                throw new Error("affectedRows is zero where post");
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                console.log(result[0][0]);
                var title = result[0][0].title;
                var content = result[0][0].content;
                var writer = result[0][0].writer;
                var write_date = result[0][0].write_date;
                var kind = result[0][0].kind;
                var img_path = result[0][0].img;
                var latitue = result[0][0].latitue;
                var longitude = result[0][0].longitude;

            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            //connection.release();
        }
        res.json({
            'title': title,
            'content': content,
            'writer': writer,
            'write_date': write_date,
            'kind': kind,
            'img_path': img_path,
            'latitue': latitue,
            'longitude': longitude
        });
    });
    route.get('/lookupfindm/:postno', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var postno = req.params.postno;
        await connection.beginTransaction();
        try {
            //게시글 등록
            var sql = 'SELECT * FROM find_master WHERE postno = ?';
            var params = [postno];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                throw new Error("affectedRows is zero where post");
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                console.log(result[0][0]);
                var title = result[0][0].title;
                var content = result[0][0].content;
                var writer = result[0][0].writer;
                var write_date = result[0][0].write_date;
                var img_path = result[0][0].img;
                var latitue = result[0][0].latitue;
                var longitude = result[0][0].longitude;

            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            //connection.release();
        }
        res.json({
            'title': title,
            'content': content,
            'writer': writer,
            'write_date': write_date,
            'img_path': img_path,
            'latitue': latitue,
            'longitude': longitude
        });
    });

    route.post('/lookupclass', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var dn = req.body.data_n;
        var pn = req.body.page_n;
        var latitue = req.body.latitue;
        var longitude = req.body.longitude;
        pn = (pn - 1) * dn;
        var resu;
        await connection.beginTransaction();
        try {
            var sql = 'SELECT *,(6371 * acos(cos(radians(?)) * cos(radians(fa.latitue)) * cos(radians(fa.longitude) - radians(?)) + sin(radians(?)) * sin(radians(fa.latitue)))) AS distance FROM class_reservation as fa HAVING distance <= 2 ORDER BY distance LIMIT ?, ?';
            var params = [latitue, longitude, latitue, pn, dn];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                console.log(result[0]);
                resu = result[0];
            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;
        } finally {
        }
        res.json(resu);
    });
    route.get('/lookupclass/:postno', async (req, res) => {
        var resultCode = 200;
        var message = 'fail';
        const connection = await pool.getConnection(async conn => conn);
        var postno = req.params.postno;
        await connection.beginTransaction();
        try {
            //게시글 등록
            var sql = 'SELECT * FROM class_reservation WHERE postno = ?';
            var params = [postno];
            let result = await connection.execute(sql, params);
            if (result[0].affectedRows == 0) {
                throw new Error("affectedRows is zero where post");
                resultCode = 404;
                message = 'write fail';
                console.log(message);
            }
            else {
                console.log(result[0][0]);
                var postno = result[0][0].postno;
                var title = result[0][0].title;
                var content = result[0][0].content;
                var writer = result[0][0].writer;
                var reservation_date = result[0][0].reservation_date;
                var latitue = result[0][0].latitue;
                var longitude = result[0][0].longitude;

            }
        } catch (err) {
            await connection.rollback();
            res.json({
                'code': resultCode,
                'message': message
            });
            throw err;

        } finally {
            //connection.release();
        }
        res.json({
            'postno': postno,
            'title': title,
            'content': content,
            'writer': writer,
            'reservation_date': reservation_date,
            'latitue': latitue,
            'longitude': longitude
        });
    });

    return route;
};