var express = require('express'),
    proxy = require('express-http-proxy'),
    app = express();

app.use(express.static('dist'));

app.listen(3000, function () {
    console.log('Server is running on http://localhost:3000')
});

app.use('/*', proxy('http://localhost:8080/', {
    forwardPath: function(req) {
        return require('url').parse(req.url).path + 'spoqn-server' + req.originalUrl;
    }
}));