/* eslint-disable @typescript-eslint/no-explicit-any */
import { Register, RegisterContainer, RegisterPageContainer } from "./styles";
import Todo from "../../assets/Todo.svg";
import { useContext, useState } from "react";
import { UserContext } from "../../context/UserContext";


export function RegisterBody() {
    const context = useContext(UserContext)

    if (!context) {
        throw new Error("useUserContext must be used within a UserProvider");
    }

    const { createUser } = context;

    const [username, setUserName] = useState<string>('');
    const [fullname, setFullname] = useState<string>('');
    const [user_email, setUser_email] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [confirmpassword, setConfirmpassword] = useState<string>('');


    const handledSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            console.log({ username, fullname, user_email, password })

            if (confirmpassword != password) return (window.alert('senha não confere'), setPassword(''), setConfirmpassword(''), null)

            createUser({ username, fullname, user_email, password })

            setUserName('')
            setFullname('')
            setUser_email('')
            setPassword('')
        } catch (error: any) {
            if (error.response) {
                console.error('Erro de validação:', error.response.data);
                alert(JSON.stringify(error.response.data, null, 2)); // mostra os problemas
            } else {
                console.error(error);
                alert('Erro inesperado ao cadastrar médico.');
            }
        }
    }


    return (
        <div>
            <RegisterPageContainer>
                <RegisterContainer>
                    <div className="Logo">
                        <img src={Todo} />
                    </div>
                    <Register>
                        <form onSubmit={handledSubmit}>
                            <label>
                                <input value={username}
                                    onChange={(e) => setUserName(e.target.value)}
                                    placeholder="UserName" />
                            </label>
                            <label>
                                <input value={fullname}
                                    onChange={(e) => setFullname(e.target.value)}
                                    placeholder="Name" />
                            </label>
                            <label>
                                <input value={user_email}
                                    onChange={(e) => setUser_email(e.target.value)}
                                    placeholder="E-mail" />
                            </label>
                            <label>
                                <input type="password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    placeholder="Password" />
                            </label>
                            <label>
                                <input type="password"
                                    value={confirmpassword}
                                    onChange={(e) => setConfirmpassword(e.target.value)}
                                    placeholder="Confirm Password" />
                            </label>
                            <button type="submit">Register</button>
                        </form>
                    </Register>
                </RegisterContainer>
            </RegisterPageContainer>
        </div>
    )
}