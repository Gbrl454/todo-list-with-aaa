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

export async function login(form: LoginUserForm): Promise<UserTokenDTO> {
  const response = await axios.post<UserTokenDTO>(
    `${API_URL}/auth/login`,
    form,
    { withCredentials: true } // se necessário para cookies HttpOnly
  );

  Cookies.set("X-TOKEN-TODO", JSON.stringify(response.data), {
    expires: response.data.expiresIn / (60 * 60 * 24), // corrige a unidade de tempo
    secure: true,
    sameSite: "Strict",
  });

  return response.data;
}

export async function refreshToken(): Promise<UserTokenDTO> {
  const cookie = Cookies.get("X-TOKEN-TODO");
  if (!cookie) throw new Error("Token não encontrado no cookie.");

  const oldToken: UserTokenDTO = JSON.parse(cookie);

  const response = await axios.put<UserTokenDTO>(
    `${API_URL}/auth/refresh`,
    { refresh_token: oldToken.refreshToken },
    { withCredentials: true }
  );

  const newToken = response.data;

  Cookies.set("X-TOKEN-TODO", JSON.stringify(newToken), {
    expires: newToken.expiresIn / (60 * 60 * 24),
    secure: true,
    sameSite: "Strict",
  });

  return newToken;
}
