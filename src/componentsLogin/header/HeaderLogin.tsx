import Todo from "../../assets/Todo.svg";
import { HeaderLoginContainer } from "./styles";


export function HeaderLogin(){
    return(
            <HeaderLoginContainer>
                <div className="Logo">
                    <img src={Todo} />
                </div>
            </HeaderLoginContainer>
    )   
}