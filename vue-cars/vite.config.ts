import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// @ts-ignore
import { resolve } from 'path'
// https://vitejs.dev/config/

export default defineConfig({
  plugins: [vue()],
  server: {
        host: '0.0.0.0',
        port: 8085,
        hmr: true,
        open: true
     },
    resolve: {
        alias: [
            {
                find: '@',
                // @ts-ignore
                replacement: resolve(__dirname, 'src')
            }
        ]
    },
    define:{
        'process.env': {
            'BASE_API':"http://localhost:8080"
        }
    }
})
