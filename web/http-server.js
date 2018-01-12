var config = require('./http-config');
var http = require('http');
var url = require('url');
var fs = require('fs');
var path = require('path');
var baseDir = __dirname;

http.createServer(function(request, response) {
	try {
		var pathname = url.parse(request.url).pathname;
		// if the pathname startsWith X, redirect to the full path
		if(pathname === config.hotheadUri){
			response.writeHead(302, {'Location':config.hotheadPath});
			response.end(); // end response;
		}
		
		// if the index isn't on the path, add it
		if(pathname === config.hotheadPath){
			pathname = pathname + 'index.html';
		}

		var fsPath = baseDir + path.normalize(pathname);
//		console.log("pathname: " + pathname);
//		console.log(fsPath);
        // for now, block config or server code files since they have sensitive data
        if(pathRestricted(pathname)){
//		    console.log("restricted **************");
			response.writeHead(404);
			response.end();
		} else {
            if(fsPath.includes(".css")){
		        response.writeHead(200, {'Content-Type':'text/css'});
            } else {
		        response.writeHead(200, {'Content-Type':'text/html'});
		    }
        }

		if(pathname === config.hotheadRestPath){
            db.query(queryString);
//        console.log("pathname: " + pathname);
//        console.log(fsPath);
//            console.log("called ws**************");
            response.end("test");
        } else {
            // create stream and attempt to get contents
            var fileStream = fs.createReadStream(fsPath);
            fileStream.pipe(response);
//                console.log("piping **************");
            fileStream.on('error', function(e){
                response.writeHead(404);
                response.end();
            });
		}
	} catch (e) {
		response.writeHead(500);
		response.end();
		console.log(e.stack);
	}
}).listen(config.port, config.ipaddress);
console.log('Server running at http://' + config.ipaddress + ':' + config.port);

var pathRestricted = function(path){
    var restrictedPaths = config.restricted;
    var ret = false;

//        console.log("comparing paths**************");
    restrictedPaths.forEach(function(s){
//        console.log("string : " + s + " **************");
        if(path.includes(s)){
            ret = true;
//        console.log("restricted ************");
        }
    });
    return ret;
}