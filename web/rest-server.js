var restify = require('restify');
var config = require('./rest-config');
var db = require('./db');
var dbconfig = require('./db-config');
var fs = require('fs');
var serveStatic = require('serve-static-restify');
var nodemailer = require('nodemailer');

var queryBase = './pages/hothead/rest/sql/';

var restServer = restify.createServer({
//  certificate: fs.readFileSync('path/to/server/certificate'),
//  key: fs.readFileSync('path/to/server/key'),
  name: config.name,
});
restServer.listen(config.port, '0.0.0.0');
restServer.use(restify.bodyParser({"mapParams":true}));

// ***************************** serve hothead web pages
restServer.get(/\/hothead\/?((?!rest).)*/, restify.serveStatic({
    directory: './pages',
    default: 'index.html'
   // match: /^((?!rest).)*$/   // we should deny access to the rest
}));

// ***************************** define rest endpoints
/*
 * Get results for the recommended widget
 */
restServer.get('/rest/recommended', function(req, res, next) {
//console.log("getting db****");
    fs.readFile(queryBase + "recommended.sql", "utf-8", function(err, data){
        var hhcon = db.connect(dbconfig);
//console.log("getting queryString ****" + data);
        db.query(hhcon, data, function(err, rows){
//console.log("returning json****" + rows);
           if(err){return next(err);}
            var result = rows;
            res.send(200, JSON.stringify(result));
        });
    });
});

/*
 * Get results for the popular widget
 */
restServer.get('/rest/popular', function(req, res, next) {
console.log("Called /rest/popular ****");
    fs.readFile(queryBase + "popular.sql", "utf-8", function(err, data){
//console.log("getting queryString ****" + data);
        var hhcon = db.connect(dbconfig);
        db.query(hhcon, data, function(err, rows){
//console.log("returning json****" + rows);
           if(err){return next(err);}
            res.send(200, JSON.stringify(rows));
        });
    });
});

/*
 * Get search results
 */
restServer.get('/rest/search/:name', function(req, res, next) {
//console.log("received call with param: " + req.params.name);
    var vars = ["%"+req.params.name+"%","%"+req.params.name+"%","%"+req.params.name+"%","%"+req.params.name+"%"];
    fs.readFile(queryBase + "search.sql", "utf-8", function(err, data){
//console.log("getting queryString ****" + data);
        var hhcon = db.connect(dbconfig);
        db.preparedQuery(hhcon, data, vars, function(err, rows){
//console.log("returning json****" + rows);
           if(err){return next(err);}
            res.send(200, JSON.stringify(rows));
        });
    });
});

/*
 * Get a random sauce
 */
restServer.get('/rest/random/:hotness', function(req, res, next) {
    console.log("received call with param: " + req.params.hotness);
    var vars = [req.params.hotness, req.params.hotness, req.params.hotness == 'insane' ? 'xxxhot' : req.params.hotness];
    fs.readFile(queryBase + "detailRandomByHeat.sql", "utf-8", function(err, data){
        console.log("getting queryString ****" + data);
        var hhcon = db.connect(dbconfig);
        db.preparedQuery(hhcon, data, vars, function(err, rows){
            console.log("returning json****" + JSON.stringify(rows));
            if(err){return next(err);}
            res.send(200, JSON.stringify(rows));
        });
    });
});

/*
 * Get details
 */
restServer.get('/rest/detail/:key', function(req, res, next) {
    console.log("received call with param: " + req.params.key);
    var vars = [req.params.key];
    fs.readFile(queryBase + "detailByKey.sql", "utf-8", function(err, data){
        console.log("getting queryString ****" + data);
        var hhcon = db.connect(dbconfig);
        db.preparedQuery(hhcon, data, vars, function(err, rows){
            console.log("returning json****" + JSON.stringify(rows));
            if(err){return next(err);}
            res.send(200, JSON.stringify(rows));
        });
    });
});

/*
 * Get reviews
 */
restServer.get('/rest/reviews/:key', function(req, res, next) {
    console.log("received call with param: " + req.params.key);
    var vars = [req.params.key];
    fs.readFile(queryBase + "reviewsByKey.sql", "utf-8", function(err, data){
        console.log("getting queryString ****" + data);
        var hhcon = db.connect(dbconfig);
        db.preparedQuery(hhcon, data, vars, function(err, rows){
            console.log("returning json****" + JSON.stringify(rows));
            if(err){return next(err);}
            res.send(200, JSON.stringify(rows));
        });
    });
});

/*
 * Get bookmarks
 */
restServer.get('/rest/bookmarks/:user', function(req, res, next) {
    console.log("received call with param: " + req.params.user);
    var vars = [req.params.user];
    fs.readFile(queryBase + "bookmarksByUser.sql", "utf-8", function(err, data){
        console.log("getting queryString ****" + data);
        var hhcon = db.connect(dbconfig);
        db.preparedQuery(hhcon, data, vars, function(err, rows){
            console.log("returning json****" + JSON.stringify(rows));
            if(err){return next(err);}
            res.send(200, JSON.stringify(rows));
        });
    });
    return next();
});

/*
 * Create user
 */
restServer.post('/rest/createprofile', function(req, res, next) {
    console.log("received call with body: " + req.body);
    console.log("received call with param: " + req.body.email + " " + req.body.screenName);
    // see if the user exists already
    var screenName = [req.body.screenName];
    fs.readFile(queryBase + "doesUserExist.sql", "utf-8", function(err, data){
        console.log("getting queryString ****" + data);
        var hhcon = db.connect(dbconfig);
        db.preparedQuery(hhcon, data, screenName, function(err, rows){
            console.log("returning json****" + JSON.stringify(rows));
            if(err){return next(err);}
            if(rows[0].userExists == 1){
                console.log("Error: user already exists");
                res.send(200, JSON.stringify({"status": "error", "message":"User already exists"}));
            } else {
                // add user
                var max = 999999;
                var min = 100000;
                var code = Math.random() * (max - min) + min;
                vars = [req.body.screenName, '', '', req.body.email, req.body.hash, code];
                fs.readFile(queryBase + "storeNewProfile.sql", "utf-8", function(err, data){
                    console.log("getting queryString ****" + data);
                    var hhcon = db.connect(dbconfig);
                    db.preparedQuery(hhcon, data, vars, function(err, rows){
                        console.log("returning json****" + JSON.stringify(rows));
                        if(err){return next(err);}
                        res.send(200, JSON.stringify(rows));


                // Use Smtp Protocol to send Email
    //console.log("creating transport");
    //            var smtpTransport = nodemailer.createTransport({
    //               service: "Gmail",
    //               auth: {
    //                   type:"OAuth2",
    //                   user: "hotheadapp@gmail.com",
    //                   accessToken: ""
    //               }
    //            });
    //
    //console.log("verifying transport");
    //            // verify connection configuration
    //            smtpTransport.verify(function(error, success) {
    //               if (error) {
    //                    console.log(error);
    //               } else {
    //                    console.log('Server is ready to take our messages');
    //               }
    //            });
    //
    //console.log("creating mail object");
    //            var mail = {
    //                from: "hotheadapp@gmail.com",
    //                to: "alex.t.finch@gmail.com",
    //                subject: "Verify your email",
    //                text: "Node.js New world for me",
    //                html: "<b>Node.js New world for me</b>"
    //            }
    //
    //console.log("sending mail");
    //            smtpTransport.sendMail(mail, function(error, response){
    //                if(error){
    //console.log(error);
    //                } else {
    //console.log("Message sent: " + response.message);
    //                }
    //
    //                smtpTransport.close();
    //            });
                    });
                });
            }
        });
    });

    return next();
});

/*
 * Get user from hash
 */
restServer.get('/rest/getprofilefromhash/:hash', function(req, res, next) {
    console.log("received call with param: " + req.params.hash);
    var vars = [req.params.hash];
    fs.readFile(queryBase + "getProfileFromHash.sql", "utf-8", function(err, data){
        console.log("getting queryString ****" + data);
        var hhcon = db.connect(dbconfig);
        db.preparedQuery(hhcon, data, vars, function(err, rows){
            console.log("returning json****" + JSON.stringify(rows));
            if(err){return next(err);}
            rows[0].success = "success";
            res.send(200, JSON.stringify(rows));
        });
    });

    return next();
});
