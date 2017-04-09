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
            Components: path.resolve(__dirname, './app/components')
        },
        extensions: ['.js', '.jsx']
    },
    module: {
        loaders: [
            {
                loader: 'babel-loader',
                query: {
                    presets: ['react', 'es2015']
                },
                test: /\.jsx?$/,
                exclude: /(node_modules|bower_components)/
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, './app/index.hbs'),
            filename:  path.resolve(__dirname, './dist/index.html'),
            inject: false
        })
    ]
};