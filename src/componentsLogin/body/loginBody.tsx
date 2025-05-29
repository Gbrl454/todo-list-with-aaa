import { Login, LoginContainer, LoginPageContainer } from "./styles";
import Todo from "../../assets/Todo.svg";
import { useContext, useState } from "react";
import { UserContext } from "../../context/UserContext";
import { Link, useNavigate } from "react-router-dom";

export function LoginBody() {
    const navigate = useNavigate();
    const context = useContext(UserContext)

    if (!context) {
        throw new Error("useUserContext must be used within a UserProvider");
    }

    const { loginUser } = context;

    const [username, setUserName] = useState<string>('')
    const [password, setPassword] = useState<string>('')

    const handledSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!username.trim() || !password.trim()) {
            alert("Preencha todos os campos.");
            return;
        }

        const success = await loginUser({ username, password })

        if (success) {
            navigate("/task")
        } else {
            alert('Erro de validação:');
            setUserName('')
            setPassword('')
        }
    }

    return (
        <LoginPageContainer>
            <LoginContainer>
                <div className="Logo">
                    <img src={Todo} />
                </div>
                <form onSubmit={handledSubmit} id="formulario">
                    <Login>
                        <label>
                            <input type="text" value={username} onChange={(e) => setUserName(e.target.value)} placeholder="Nickname" />
                        </label>
                        <label>
                            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
                        </label>
                        <Link to="/register">create account</Link>
                        <button type="submit">login</button>
                    </Login>
                </form>
            </LoginContainer>
        </LoginPageContainer>
    )
}