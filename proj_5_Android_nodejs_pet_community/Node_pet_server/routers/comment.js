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

	route.post('/addcomment/post', async (req, res) => {
		const connection = await pool.getConnection(async conn => conn);
		console.log(req.body);
		var writer = req.body.writer;
		var content = req.body.content;
		var write_date = date;
		var postno = req.body.postno;
		resultCode = 404;
		message = 'fail';
		try {
			//게시글 등록
			var sql = 'INSERT INTO Post_Comment (postno,writer,content,write_date) VALUES (?, ?, ?, ?)';
			var params = [postno, writer, content, write_date];
			let result = await connection.execute(sql, params);
			if (result[0].affectedRows == 0) {
				throw new Error("affectedRows is zero where post");
				resultCode = 404;
				message = 'write fail';
				console.log(message);
			}
			else {
				resultCode = 200;
				message = 'comment write suc';
			}
		} catch (err) {
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
	route.post('/addcomment/ipost', async (req, res) => {
		const connection = await pool.getConnection(async conn => conn);
		console.log(req.body);
		var writer = req.body.writer;
		var content = req.body.content;
		var write_date = date;
		var postno = req.body.postno;
		resultCode = 404;
		message = 'fail';
		try {
			//게시글 등록
			var sql = 'INSERT INTO iPost_Comment (postno,writer,content,write_date) VALUES (?, ?, ?, ?)';
			var params = [postno, writer, content, write_date];
			let result = await connection.execute(sql, params);
			if (result[0].affectedRows == 0) {
				throw new Error("affectedRows is zero where post");
				resultCode = 404;
				message = 'write fail';
				console.log(message);
			}
			else {
				resultCode = 200;
				message = 'comment write suc';
			}
		} catch (err) {
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
	route.post('/addcomment/class', async (req, res) => {
		const connection = await pool.getConnection(async conn => conn);
		console.log(req.body);
		var writer = req.body.writer;
		var content = req.body.content;
		var write_date = date;
		var postno = req.body.postno;
		resultCode = 404;
		message = 'fail';
		try {
			//게시글 등록
			var sql = 'INSERT INTO Class_Comment (postno,writer,content,write_date) VALUES (?, ?, ?, ?)';
			var params = [postno, writer, content, write_date];
			let result = await connection.execute(sql, params);
			if (result[0].affectedRows == 0) {
				throw new Error("affectedRows is zero where post");
				resultCode = 404;
				message = 'write fail';
				console.log(message);
			}
			else {
				resultCode = 200;
				message = 'comment write suc';
			}
		} catch (err) {
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
	route.post('/addcomment/finda', async (req, res) => {
		const connection = await pool.getConnection(async conn => conn);
		console.log(req.body);
		var writer = req.body.writer;
		var content = req.body.content;
		var write_date = date;
		var postno = req.body.postno;
		resultCode = 404;
		message = 'fail';
		try {
			//게시글 등록
			var sql = 'INSERT INTO Finda_Comment (postno,writer,content,write_date) VALUES (?, ?, ?, ?)';
			var params = [postno, writer, content, write_date];
			let result = await connection.execute(sql, params);
			if (result[0].affectedRows == 0) {
				throw new Error("affectedRows is zero where post");
				resultCode = 404;
				message = 'write fail';
				console.log(message);
			}
			else {
				resultCode = 200;
				message = 'comment write suc';
			}
		} catch (err) {
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
	route.post('/addcomment/findm', async (req, res) => {
		const connection = await pool.getConnection(async conn => conn);
		console.log(req.body);
		var writer = req.body.writer;
		var content = req.body.content;
		var write_date = date;
		var postno = req.body.postno;
		resultCode = 404;
		message = 'fail';
		try {
			//게시글 등록
			var sql = 'INSERT INTO Findm_Comment (postno,writer,content,write_date) VALUES (?, ?, ?, ?)';
			var params = [postno, writer, content, write_date];
			let result = await connection.execute(sql, params);
			if (result[0].affectedRows == 0) {
				throw new Error("affectedRows is zero where post");
				resultCode = 404;
				message = 'write fail';
				console.log(message);
			}
			else {
				resultCode = 200;
				message = 'comment write suc';
			}
		} catch (err) {
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


	route.post('/showcomment/post', async (req, res) => {
		const connection = await pool.getConnection(async conn => conn);
		console.log(req.body);
		var postno = req.body.postno;
		var resu;
		resultCode = 404;
		message = 'fail';
		try {
			//게시글 등록
			var sql = 'SELECT * FROM Post_Comment WHERE postno = ?'
			var params = [postno];
			let result = await connection.execute(sql, params);
			if (result[0].affectedRows == 0) {
				throw new Error("affectedRows is zero where post");
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
				'message': "등록 실패"
			});
			throw err;

		} finally {

		}
		res.json(resu);
	});
	route.post('/showcomment/ipost', async (req, res) => {
		const connection = await pool.getConnection(async conn => conn);
		console.log(req.body);
		var postno = req.body.postno;
		var resu;
		resultCode = 404;
		message = 'fail';
		try {
			//게시글 등록
			var sql = 'SELECT * FROM iPost_Comment WHERE postno = ?'
			var params = [postno];
			let result = await connection.execute(sql, params);
			if (result[0].affectedRows == 0) {
				throw new Error("affectedRows is zero where post");
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
				'message': "등록 실패"
			});
			throw err;

		} finally {

		}
		res.json(resu);
	});
	route.post('/showcomment/class', async (req, res) => {
		const connection = await pool.getConnection(async conn => conn);
		console.log(req.body);
		var postno = req.body.postno;
		var resu;
		resultCode = 404;
		message = 'fail';
		try {
			//게시글 등록
			var sql = 'SELECT * FROM Class_Comment WHERE postno = ?';
			var params = [postno];
			let result = await connection.execute(sql, params);
			if (result[0].affectedRows == 0) {
				throw new Error("affectedRows is zero where post");
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
				'message': "등록 실패"
			});
			throw err;

		} finally {

		}
		res.json(resu);
	});
	route.post('/showcomment/finda', async (req, res) => {
		const connection = await pool.getConnection(async conn => conn);
		console.log(req.body);
		var postno = req.body.postno;
		var resu;
		resultCode = 404;
		message = 'fail';
		try {
			//게시글 등록
			var sql = 'SELECT * FROM Finda_Comment WHERE postno = ?'
			var params = [postno];
			let result = await connection.execute(sql, params);
			if (result[0].affectedRows == 0) {
				throw new Error("affectedRows is zero where post");
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
				'message': "등록 실패"
			});
			throw err;

		} finally {

		}
		res.json(resu);
	});
	route.post('/showcomment/findm', async (req, res) => {
		const connection = await pool.getConnection(async conn => conn);
		console.log(req.body);
		var postno = req.body.postno;
		var resu;
		resultCode = 404;
		message = 'fail';
		try {
			//게시글 등록
			var sql = 'SELECT * FROM Findm_Comment WHERE postno = ?'
			var params = [postno];
			let result = await connection.execute(sql, params);
			if (result[0].affectedRows == 0) {
				throw new Error("affectedRows is zero where post");
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
				'message': "등록 실패"
			});
			throw err;

		} finally {

		}
		res.json(resu);
	});
	return route;
};