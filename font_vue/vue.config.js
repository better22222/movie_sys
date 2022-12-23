const port = 81
module.exports = {
  devServer: {
    port: port,
    proxy: {
      '/api': {
        target: 'http://localhost:10002',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '',
        }
      }
    }
  },

}




