import { useContext } from "react";
import Todo from "../../assets/Todo.svg";
import { HeaderContainer } from "./styles";
import { UserContext } from "../../context/UserContext";
import { useNavigate } from "react-router-dom";


export function Header(){
    const context = useContext(UserContext);
    const navigato = useNavigate()

    if (!context) {
        throw new Error("Header must be used within a UserProvider");
    }

    const handledLogout = async(e: React.FormEvent) =>{
        e.preventDefault()
        logout()

        navigato("/auth/login")
    }

    const {currentUser, logout} = context;
    return(
            <HeaderContainer>
                <div className="Logo">
                    <img src={Todo} />
                </div>
                <div className="LoginContainer">
                    <h2>{currentUser?.fullName}</h2>
                    <button onClick={handledLogout} type="submit">Sing out</button>
                </div>
            </HeaderContainer>
    )
}