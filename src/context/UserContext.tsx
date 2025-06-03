import { createContext } from "react";

interface User{
    username: string,
    fullname: string,
    email: string;
    password: string;
    passwordConfirmation: string;
}

interface LoginCredentials{
    username: string,
    password: string
}

interface ContextUserType {
  users: User[];
  fetchUser: (query?: string) => void;
  createUser: (user: User) => void;
  loginUser: (credential: LoginCredentials) => Promise<{ success: boolean; message?: string }>;
  currentUser: User | null;
  logout: () => void;
}

export const UserContext = createContext<ContextUserType | undefined>(undefined)