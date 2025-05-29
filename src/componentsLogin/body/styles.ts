import styled from "styled-components";

export const LoginPageContainer = styled.div`
    width: 100%;
    height: 100vh;

    display: flex;
    justify-content: center;
    align-items: start;

    padding: 80px;
`

export const LoginContainer = styled.div`
    max-width: 490px;
    width: 100%;

    max-height: 440px;
    height: 100%;

    border-radius: 8px;
    display: flex;
    flex-direction: column;
    justify-content: start;
    align-items: center;

    background-color: ${props => props.theme['gray-400']};
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);

    padding-top: 80px;
`

export const Login = styled.div`
    max-width: 488px;
    width: 100%;
   
    display: flex;
    flex-direction: column;
    justify-content: start;
    align-items: center;

    padding-top: 23px;
    gap: 12px;

    input{
        width:275px;
        height: 54px;

        border-radius: 8px;
        
        padding: 10px;
        color: ${props => props.theme['gray-200']};
        background-color: ${props => props.theme['gray-500']};
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    }

    a{
        color: ${props => props.theme['purple']};
    }

    button{
        width: 200px;
        height: 52px;
        border-radius: 8px;
        
        color: ${props => props.theme["gray-200"]};
        background-color: ${props => props.theme["blue-dark"]};
        border-color: transparent;
        font-weight: bold;
        font-size: 14px;
        cursor: pointer;
    }
`