import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  
  /*
  
  questo si usa per docker composeserver: {
    host: "0.0.0.0",
    port: 5173,
    proxy: {
      '/auth': {
        
        
       target: 'http://backend:9090',
        changeOrigin: true,
        secure: false
      }
    }
  }*/

//questo si usa per docker separati front end backend
  server: {
    host: '0.0.0.0',
    port: 5173,

    proxy: {
      '/auth': {
        target: 'http://host.docker.internal:8080',
        changeOrigin: true,
        secure: false
      }
    }
  }
})
