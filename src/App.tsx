import { ThemeProvider } from "styled-components";
import { HomePage } from "./compoents/HomePage";
import { defaultTheme } from "./styles/themes/default";
import { GlobalStyle } from "./styles/Global";
import { TasksProvider } from "./context/TasksProvider";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { Login } from "./componentsLogin/LoginPage";
import { RegisterPage } from "./componentsRegister/RegisterPage";
import { UserProvider } from "./context/UserProvider";
import { PrivateRoute } from "./compoents/PrivateRoute";
import { ViewPage } from "./componentsView/viewPage";
import { AuthGuard } from "./guard/AuthGuard";



export function App() {
  return (
    <>
      <ThemeProvider theme={defaultTheme}>
        <GlobalStyle />
        <TasksProvider>
          <UserProvider>
            <BrowserRouter>
              <Routes>
                <Route path="/auth/login" element={<Login />} />
                <Route path="/register" element={<RegisterPage />} />


                <Route element={<AuthGuard />}>
                  <Route path="/task" element={<HomePage />} />
                  <Route path="/task/:id" element={<ViewPage />} />
                </Route>

                <Route path="*" element={<Navigate to="/auth/login" replace />} />
              </Routes>
            </BrowserRouter>
          </UserProvider>
        </TasksProvider>
      </ThemeProvider>
    </>
  )
}
