import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { AppProviders } from './app/AppProviders';
import { ProtectedRoute } from './app/ProtectedRoute';
import { useAuth } from './features/auth/useAuth';
import { AuthPage } from './pages/AuthPage';
import { DashboardPage } from './pages/DashboardPage';
import { ProjectPage } from './pages/ProjectPage';

function AppRoutes() {
  const { isAuthenticated } = useAuth();

  return (
    <Routes>
      <Route
        path="/login"
        element={isAuthenticated ? <Navigate replace to="/" /> : <AuthPage />}
      />
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <DashboardPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/projects/:projectId"
        element={
          <ProtectedRoute>
            <ProjectPage />
          </ProtectedRoute>
        }
      />
      <Route path="*" element={<Navigate replace to={isAuthenticated ? '/' : '/login'} />} />
    </Routes>
  );
}

export default function App() {
  return (
    <AppProviders>
      <BrowserRouter>
        <AppRoutes />
      </BrowserRouter>
    </AppProviders>
  );
}
