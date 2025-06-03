import { useContext } from "react";
import { UserContext } from "../context/UserContext";


export function useAuth() {
  const { currentUser, loading } = useContext(UserContext);
  const isAuthenticated = !!currentUser;
  return { isAuthenticated, loading };
}