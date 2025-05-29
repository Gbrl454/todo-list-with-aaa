import styled from "styled-components";


export const HeaderContainer = styled.div`
    height: 200px;
    
    width: 100%;
    max-width: 1600px;
    background-color: ${props => props.theme["gray-700"]};
    
    
    display: flex;
    align-items: start;
    justify-content: space-between;

    padding: 1rem;
    
    .Logo{
        width: 145px;
        height: 64px;
    }

    .LoginContainer{
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 0.5rem;

        h2{ 
            width: 144px;
            height: 54px;
            
            display: flex;
            align-items: center;
            border-radius: 16px;
            color: ${props => props.theme["gray-200"]};
            background-color: ${props => props.theme["gray-500"]};
            font-size: 18px;

            padding: 0.5rem;
        }

        button{ 
            width: 89px;
            height: 52px;
            border-radius: 8px;
            
            color: ${props => props.theme["gray-200"]};
            background-color: ${props => props.theme["blue-dark"]};
            border-color: transparent;
            font-weight: bold;
            font-size: 14px;
            cursor: pointer;
        }
    }
`
