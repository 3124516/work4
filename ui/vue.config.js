// vue.config.js
const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  // 禁用 ESLint 对未使用变量的检查
  lintOnSave: false,
  
  // 配置 Webpack 别名
  configureWebpack: {
    resolve: {
      alias: {
        '@': require('path').resolve(__dirname, 'src')
      }
    }
  }
})