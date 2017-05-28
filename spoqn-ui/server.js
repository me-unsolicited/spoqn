var express = require('express'),
    proxy = require('express-http-proxy'),
    app = express(),
    URL = require('url');

app.use(express.static('dist'));

app.listen(3000, function () {
    console.log('Server is running on http://localhost:3000')
});

app.use('/api/*', proxy('http://localhost:8080/', {
    forwardPath: function (request) {
        return URL.parse(request.url).path + 'spoqn-server' + request.originalUrl;
    }
}));

app.use('/*', function (request, response) {
    if (/api/g.test(request.originalUrl))
        return;

    response.redirect('/');
});