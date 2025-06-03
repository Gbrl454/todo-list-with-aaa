/* eslint-disable @typescript-eslint/no-explicit-any */
import { useEffect, useState, type ReactNode } from "react";
import { api } from "../lib/axios";
import { UserContext } from "./UserContext";
import { toast } from "react-toastify";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import Cookies from "js-cookie";


interface User {
    username: string;
    fullname: string;
    email: string;
    password: string;
    passwordConfirmation: string;
}

interface JwtPayload {
    preferred_username: string;
    given_name: string;
    family_name: string;
    email: string;
}

interface LoginCredentials {
    username: string;
    password: string;
}

interface UserProviderProps {
    children: ReactNode;
}

export function UserProvider({ children }: UserProviderProps) {
    const [users, setUsers] = useState<User[]>([]);
    const [currentUser, setCurrentUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true); // novo estado loading

    async function fetchUser(query = "") {
        try {
            const response = await api.get("/users", { params: { q: query } });
            setUsers(response.data);
        } catch (error) {
            console.error("Erro ao buscar usuários:", error);
        }
    }

    async function createUser(user: User): Promise<void> {
        try {
            await api.post("/auth/register", user);
            toast.success("Usuário registrado com sucesso!");
        } catch (error) {
            const msg = axios.isAxiosError(error)
                ? error.response?.data?.message || "Erro ao registrar usuário."
                : "Erro desconhecido.";
            toast.error(msg);
            console.error(error);
        }
    }

    async function loginUser(
        credentials: LoginCredentials
    ): Promise<{ success: boolean; message?: string }> {
        try {
            const response = await api.post("/auth/login", credentials, {
                withCredentials: true,
            });

            const { accessToken, refreshToken, expiresIn } = response.data;

            if (!accessToken) {
                return { success: false, message: "Token não recebido." };
            }

            // ✅ Salva o token no cookie
            Cookies.set("X-TOKEN-TODO", JSON.stringify({ accessToken, refreshToken }), {
                expires: expiresIn / 60 / 24, // converter segundos para dias
                secure: true,
                sameSite: "Strict",
            });

            const decoded = jwtDecode<JwtPayload>(accessToken);

            const user: User = {
                username: decoded.preferred_username,
                fullname: `${decoded.given_name} ${decoded.family_name}`,
                email: decoded.email,
                password: "",
                passwordConfirmation: "",
            };

            setCurrentUser(user);
            return { success: true };
        } catch (error: any) {
            const msg = axios.isAxiosError(error)
                ? error.response?.data?.message || "Erro ao fazer login."
                : "Erro desconhecido ao fazer login.";
            console.error(msg);
            return { success: false, message: msg };
        }
    }
    useEffect(() => {
        async function loadUser() {
            try {
                setLoading(true);
                const response = await api.put("/auth/refresh", {}, { withCredentials: true });

                const { accessToken, refreshToken, expiresIn } = response.data;

                if (!accessToken) {
                    setCurrentUser(null);
                    Cookies.remove("X-TOKEN-TODO");
                    return;
                }

                Cookies.set(
                    "X-TOKEN-TODO",
                    JSON.stringify({ accessToken, refreshToken }),
                    {
                        expires: expiresIn / 60 / 24,
                        secure: true,
                        sameSite: "Strict",
                    }
                );

                const decoded = jwtDecode<JwtPayload>(accessToken);

                const user: User = {
                    username: decoded.preferred_username,
                    fullname: `${decoded.given_name} ${decoded.family_name}`,
                    email: decoded.email,
                    password: "",
                    passwordConfirmation: "",
                };

                setCurrentUser(user);
            } catch (error) {
                setCurrentUser(null);
                Cookies.remove("X-TOKEN-TODO");
            } finally {
                setLoading(false);
            }
        }

        loadUser();
    }, []);

    async function logout() {
        try {
            await api.post("/auth/logout", null, { withCredentials: true });
        } catch (error) {
            console.error("Erro no logout:", error);
        } finally {
            Cookies.remove("X-TOKEN-TODO"); // <-- remove o token do cookie
            setCurrentUser(null);
        }
    }

    return (
        <UserContext.Provider
            value={{ users, fetchUser, createUser, loginUser, currentUser, logout, loading }}
        >
            {children}
        </UserContext.Provider>
    );
}