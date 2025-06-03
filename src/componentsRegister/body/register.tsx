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

        const trimmedUsername = username.trim();
        const trimmedFullname = fullname.trim();
        const trimmedEmail = user_email.trim();
        const trimmedPassword = password.trim();
        const trimmedConfirmPassword = confirmpassword.trim();

        if (trimmedPassword !== trimmedConfirmPassword) {
            window.alert('Senha não confere');
            setPassword('');
            setConfirmpassword('');
            return;
        }

        try {
            await createUser({
                username: trimmedUsername,
                fullname: trimmedFullname,
                email: trimmedEmail,
                password: trimmedPassword,
                passwordConfirmation: trimmedConfirmPassword
            });

            // Limpa os campos após o cadastro
            setUserName('');
            setFullname('');
            setUser_email('');
            setPassword('');
            setConfirmpassword('');
        } catch (error: any) {
            if (error.response) {
                console.error('Erro de validação:', error.response.data);
                alert(JSON.stringify(error.response.data, null, 2));
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