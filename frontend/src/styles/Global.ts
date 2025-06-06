/* eslint-disable @typescript-eslint/no-empty-object-type */
// src/styles/styled.d.ts

import 'styled-components'
import type { defaultTheme } from './themes/default'
import { createGlobalStyle } from 'styled-components';

type ThemeType = typeof defaultTheme

declare module 'styled-components' {
  export interface DefaultTheme extends ThemeType {}
}
export const GlobalStyle = createGlobalStyle`
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    :focus {
        border: 2px solid ${(props) => props.theme['gray-600']};
        box-shadow: 0 0 4px ${(props) => props.theme['gray-600']};
    }

    body {
        background-color: ${(props) => props.theme['gray-600']};
        
        -webkit-font-smoothing: antialiased;
        font-size: 16px;
        line-height: 1.5;    
        height: 100%;
        margin: 0;
    }

    body, input, textarea, button {
        font: 400 1rem Roboto, sans-serif;
    }
`;
