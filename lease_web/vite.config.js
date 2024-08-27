import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server:{
    // 配置vite冷启动项目自动使用浏览器访问首页
    open:true,
    proxy: {  
      '/api': {
        target: 'http://localhost',
        // changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  }
})
