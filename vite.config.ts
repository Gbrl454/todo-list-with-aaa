import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
        proxy: {
      '/api': {
        target: 'https://mock-4e615c3798a542dda5b515164256ac6c.mock.insomnia.rest',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
        secure: false
      }
    }}
  }
)
