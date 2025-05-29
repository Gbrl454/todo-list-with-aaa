import { LoginBody } from "./body/loginBody";
import { HeaderLogin } from "./header/HeaderLogin";

export function Login(){
    return(
        <div>
            <HeaderLogin/>
            <LoginBody/>
        </div>
    )
}