
const NodePolyfillPlugin = require('node-polyfill-webpack-plugin')
const port = 80
module.exports = {
  devServer: {
    port: port,
    proxy: {
      '/api': {
        target: 'http://localhost:10001',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '',
        }
      }
    }
  },
  configureWebpack: {
    plugins: [new NodePolyfillPlugin()]
  }
}




