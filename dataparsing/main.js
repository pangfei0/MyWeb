var http = require('http');
var dp = require("./dataparsing");
var dataParsing = dp.DataParsing;
http.createServer(function (req, res) {
    console.log("[200] " + req.method + " to " + req.url);
    var fullBody = '';

    req.on('data', function (chunk) {
        // append the current chunk of data to the fullBody variable
        fullBody += chunk.toString();
    });

    req.on('end', function () {
        try {
            res.writeHead(200, "OK", {'Content-Type': 'application/json; charset=utf-8'});
            var parsedData = dataParsing.addData(JSON.parse(fullBody))[0];
            res.write(JSON.stringify(parsedData));
        }
        catch (e) {
            console.log(e)
        }
        finally {
            res.end();
        }
    });

}).listen(1337, "127.0.0.1");

console.log('Server running at http://127.0.0.1:1337/');