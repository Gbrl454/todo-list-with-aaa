import styled from "styled-components";


export const HeaderLoginContainer = styled.div`
    height: 200px;
    
    width: 100%;
    max-width: 1500px;
    background-color: ${props => props.theme["gray-700"]};
    
    
    display: flex;
    align-items: start;

    padding: 1rem;
    
    .Logo{
        width: 145px;
        height: 64px;
    }
`
