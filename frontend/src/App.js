import { useState, createContext, useEffect, useContext } from 'react';
import Layout from './layouts/Layout';
import {BrowserRouter} from 'react-router-dom'
import {Container} from "react-bootstrap";
import AppRouter from "./route/AppRouter";
import EditorComponent from '../src/component/SummerNote'

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [member, setMember] = useState(null);

  useEffect(() => {
    const email = localStorage.getItem('email');
    const id = localStorage.getItem('id')
    setMember({ email, id });
  }, []);

  return (
      <AuthContext.Provider
          value={{ member, setMember }}
      >
        {children}
      </AuthContext.Provider>
  );
};

const App = () => {
  return (
      <>
          <AuthProvider>
              <BrowserRouter>
                  <Layout>
                      <Container style={ {minHeight: "75vh"} }>
                          <AppRouter />
                      </Container>
                  </Layout>
              </BrowserRouter>
          </AuthProvider>
      </>
  )
}

export default App;
