import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "./useAuth";

export function AuthGuard() {
  const { isAuthenticated, loading } = useAuth();

  if (loading) return <div>Verificando autenticação...</div>;

  if (!isAuthenticated) return <Navigate to="/auth/login" replace />;

  return <Outlet />;
}