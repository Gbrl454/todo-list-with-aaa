import axios from "axios";
import Cookies from "js-cookie";

export interface UserTokenDTO {
    accessToken: string;
    expiresIn: number;
    refreshExpiresIn: number;
    refreshToken: string;
}

export interface LoginUserForm {
    username: string;
    password: string;
}

const API_URL = "http://localhost:8077/todo-list";

export async function refreshToken(): Promise<UserTokenDTO> {
    const cookie = Cookies.get("X-TOKEN-TODO");
    if (!cookie) throw new Error("Token não encontrado no cookie.");

    const oldToken: UserTokenDTO = JSON.parse(cookie);

    const url = `${API_URL}/auth/refresh`;

    const response = await axios.put<UserTokenDTO>(
        url,
        { refreshToken: oldToken.refreshToken }, // <-- envia o refreshToken no corpo JSON
        {
            headers: {
                Authorization: `Bearer ${oldToken.accessToken}`,
                'Content-Type': 'application/json',
            },
            withCredentials: true,
        }
    );

    const newToken = response.data;

    Cookies.set("X-TOKEN-TODO", JSON.stringify(newToken), {
        expires: newToken.expiresIn / (60 * 60 * 24),
        secure: true,
        sameSite: "Strict",
    });

    return newToken;
}