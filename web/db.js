var mysql = require('mysql');

module.exports = {
    connect: function(config){
        var con = mysql.createConnection(config);
        con.connect(function(e){
            if(e){
                console.log('Error connection to DB');
                return;
            }
            console.log('Connection started');
        });
        return con;
    },
    query: function(con, qry, callback){
console.log("running query ****:" + qry);
        con.query(qry, function(err, rows, fields) {
            if (err) {console.log("Encountered error: " + err); throw err;}
//console.log(rows);
console.log("calling callback ****:" + rows);
            callback(err, rows);
        });
//console.log("returning db****");
    },
    preparedQuery: function(con, qry, vars, callback){
console.log("running query ****:" + qry + " with vars: " + vars);
        con.query(qry, vars, function(err, rows, fields) {
            if (err) {console.log("Encountered error: " + err); throw err;}
//console.log(rows);
console.log("calling callback ****:" + rows);
            callback(err, rows);
        });
//console.log("returning db****");
    },

    end: function(con){
        con.end(function(e){
            //terminate connection gracefully
            console.log('Connection ended');
        });
    },
}