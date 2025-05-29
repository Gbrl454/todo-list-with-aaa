import { HeaderLogin } from "../componentsLogin/header/HeaderLogin";
import { RegisterBody } from "./body/register";


export function RegisterPage(){
    return(
        <div>
            <HeaderLogin/>
            <RegisterBody/>
        </div>
    )
}