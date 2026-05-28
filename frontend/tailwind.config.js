/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{ts,tsx}'],
  theme: {
    extend: {
      colors: {
        brand: {
          50: '#f4f1ff',
          100: '#e7defe',
          200: '#d2c0fd',
          300: '#b497fb',
          400: '#9466f7',
          500: '#7a3ff0',
          600: '#6c2ee4',
          700: '#5a24c7',
          800: '#4b22a1',
          900: '#3f2082',
        },
      },
      boxShadow: {
        soft: '0 20px 60px -20px rgba(15, 23, 42, 0.35)',
      },
    },
  },
  plugins: [],
}
