import styled from "styled-components";

export const ListPage = styled.div`
    max-width: 1440px;
    width: 100%;
    height: auto;
    
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    gap: 12px;

    .CreateTaskButton{
        max-width: 320px;
        width: 100%;
        height: 52px;
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
    }

`

export const ListContainer = styled.div`
    max-width: 750px;
    width: 100%;
    height: auto;

    
    gap: 1rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    
        .Cabeçalho{
        display: flex;
        max-width: 750px;
        width: 100%;
        justify-content: space-between;
        padding: 12px;
        
        h2{
            color: ${props => props.theme["blue"]};
            font-size: 14px;
            font-weight: bold;
        }

        span{ 
            color: ${props => props.theme["purple"]};
            font-size: 14px;
            font-weight: bold;
        }
    }
`

export const Item = styled.div`
    max-width: 736px;
    width: 100%;
    height: 65px;

    display: flex;
    justify-content: space-between;
    align-items: center;

    border-radius: 8px;
    padding: 1rem;
    gap: 1rem;
    
    background-color: ${props => props.theme["gray-500"]};

    .description{
        color: white;
        width: 100%;
        font-size: 1rem;
        text-align: justify;
        padding: 10px;
        border: none;
        background-color: transparent;
        line-height: 1.5;
    }

    p{  
        max-width: 650px;
        width: 100%;
        color:  ${props => props.theme["gray-100"]};

        .paragraph-checked {
        text-decoration: line-through;
        color: ${props => props.theme["gray-300"]};
        }
    }

    button{
        background-color: transparent;
        border-radius: 8px;
        border-color: transparent;
        cursor: pointer;

        &:hover{
            transition: 0.4s;
            background-color: ${props => props.theme["purple"]};
        }
    }

    .checkbox {
    padding: 0.5px;
    border-radius: 100%;
    height: 1.125rem;
    width: 1.125rem;
    transition: 0.2s all;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    }

    .checkbox-unchecked {
    border: 2px solid ${props => props.theme["blue"]};
    }

    .checkbox-unchecked:hover {
    background-color: rgba(30, 111, 159, 0.2);
    }

    .checkbox-checked {
    border: 2px solid ${props => props.theme["purple-dark"]};
    background-color: ${props => props.theme["purple-dark"]};
    }

    .checkbox-checked:hover {
    border: 2px solid ${props => props.theme["purple"]};
    background-color: ${props => props.theme["purple"]};
    }


`