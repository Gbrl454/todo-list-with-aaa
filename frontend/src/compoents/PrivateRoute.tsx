import { useContext, type JSX } from "react";
import { Navigate } from "react-router-dom";
import { UserContext } from "../context/UserContext";

interface PrivateRouteProps{
    children: JSX.Element
}

export function PrivateRoute({children}: PrivateRouteProps){
    const {currentUser} = useContext(UserContext)

    if(!currentUser){
        return <Navigate to="/auth/login" replace/>
    }

    return children
}