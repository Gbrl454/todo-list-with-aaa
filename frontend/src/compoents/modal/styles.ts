import { DialogOverlay } from "@radix-ui/react-dialog"
import styled from "styled-components"

export const Overlay = styled(DialogOverlay)`
    position: fixed;
    width: 100vw;
    height: 100vh;
    inset: 0;
    background: rgba(0, 0, 0, 0.60);
`

export const Content = styled(DialogOverlay)`
    min-width: 32rem;
    border-radius: 8px;
    padding: 2.5rem 3rem;
    background-color:  ${props => props.theme['gray-400']};
    color: white;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);

    input{
        gap: 0.5rem;
        padding: 0.5rem;
        color: ${props => props.theme['gray-300']};
        background-color: ${props => props.theme['gray-600']};
    }

    textarea{
        gap: 0.5rem;
        padding: 0.5rem;
        color: ${props => props.theme['gray-300']};
        background-color: ${props => props.theme['gray-600']};
    }

    select{
        gap: 0.5rem;
        padding: 0.5rem;
        color: ${props => props.theme['gray-300']};
        background-color: ${props => props.theme['gray-600']};
    }

    .closeButton{
        gap: 0.5rem;
        padding: 0.5rem;
        color: ${props => props.theme['gray-300']};
        background-color: ${props => props.theme['gray-600']};
    }
`
export const CreateButton = styled.button`
        padding-left: 40px;
        
        max-width: 260px;
        width: 100%;
        height: 46px;
        border-radius: 8px;
        padding: 14px;

        gap: 12px;
        color: ${props => props.theme["gray-200"]};
        background-color: ${props => props.theme["blue-dark"]};
        border-color: transparent;
        font-weight: bold;
        font-size: 14px;
        cursor: pointer;

        &:hover{
            transition: 0.5s;
            background-color: ${props => props.theme["blue"]};
        }
`