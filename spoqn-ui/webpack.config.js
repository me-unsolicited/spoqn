var path = require('path'),
    HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: './app/app.jsx',
    output: {
        path: __dirname,
        filename: './dist/bundle.js'
    },
    resolve: {
        alias: {
            Components: path.resolve(__dirname, './app/components'),
            Common: path.resolve(__dirname, './app/common'),
            VendorJS: path.resolve(__dirname, './app/vendor/js'),
            VendorCSS: path.resolve(__dirname, './app/vendor/css'),
            Styles: path.resolve(__dirname, './app/styles')
        },
        extensions: ['.js', '.jsx']
    },
    module: {
        loaders: [
            {
                loader: 'babel-loader',
                query: {
                    presets: ['react', 'es2015', 'stage-0']
                },
                test: /\.jsx?$|\.js?$/,
                exclude: /(node_modules|bower_components)/
            },
            {
                test: /\.css$/,
                loader: "style-loader!css-loader"
            },
            {
                loader: "url-loader?name=./dist/[hash].[ext]?limit=10000&mimetype=application/font-woff",
                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/
            },
            {
                loader: "file-loader?name=./dist/assets/fonts/[hash].[ext]",
                test: /\.(ttf|eot)(\?v=[0-9]\.[0-9]\.[0-9])?$/
            },
            {
                loader: "file-loader?name=./dist/assets/images/[hash].[ext]",
                test: /\.(svg|jpeg|jpg|png)(\?v=[0-9]\.[0-9]\.[0-9])?$/
            }
        ]

    },
    plugins: [
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, './app/index.hbs'),
            filename: path.resolve(__dirname, './dist/index.html'),
            inject: false
        })
    ]
};