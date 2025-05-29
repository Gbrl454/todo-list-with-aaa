/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect, useState, type ReactNode } from "react"
import { api } from "../lib/axios"
import { UserContext } from "./UserContext"

interface User {
    username: string,
    fullname: string,
    user_email: string,
}

interface LoginCredentials {
    username: string,
    password: string
}


interface UserProviderProps {
    children: ReactNode
}

export function UserProvider({ children }: UserProviderProps) {
    const [users, setUsers] = useState<User[]>([])
    const [currentUser, setCurrentUser] = useState<User | null>(null)

    async function fetchUser(query: string = "") {
        try {
            //vai ser descontinuado
            const response = await api.get("/auth/register", { params: { q: query } })
            setUsers(response.data)
        } catch (error) {
            console.log(error)
        }
    }

    async function createUser(user: User): Promise<void> {
        try {
            await api.post("/auth/register", user)
            fetchUser()
        } catch (error) {
            console.log(error)
        }
    }

    async function loginUser(credentials: LoginCredentials): Promise<boolean> {
        try {
            await api.post("/auth/login", credentials, {
                withCredentials: true,
            });

            // Após login, faz uma requisição para obter os dados do usuário autenticado
            const meResponse = await api.get("/auth/refresh", {
                withCredentials: true,
            });

            setCurrentUser(meResponse.data)
            return true
        } catch (error: any) {
            console.error("Erro no login:", error?.response?.data || error.message);
            return false
        }
    }

    async function logout() {
        try {
            await api.post("/auth/logout", null, { withCredentials: true });
            setCurrentUser(null);
        } catch (error) {
            console.error("Erro no logout:", error);
        } finally{
            setCurrentUser(null)
        }
    }

    useEffect(() => {
        async function loadUser() {
            try {
                const response = await api.get("/auth/refresh", { withCredentials: true })
                setCurrentUser(response.data)
            } catch {
                console.warn("Usuário não autenticado");
                setCurrentUser(null);
            }
        }
        loadUser()
    }, []);

    return (
        <UserContext.Provider value={{ users, fetchUser, createUser, loginUser, currentUser, logout }}>
            {children}
        </UserContext.Provider>
    )
}