import React from 'react';
import {Modal, Button, Form, Container, Row, Col} from 'react-bootstrap';
import { GoogleLogin } from 'react-google-login'
import HorizonLine from "../../component/HorizonLine";
import {BiUserCircle} from "react-icons/bi";
import {Link} from "react-router-dom";
import {signIn} from "../../api/ApiService";
import '../../css/login.css'

const SignIn = ({} ) => {
    async function HandleSignInButton() {
        let formData = new FormData(document.getElementById('signInForm'));
        let member = {
            email : formData.get('email'),
            password : formData.get('password'),
        }

        console.log(member)
        let result = await signIn(member)
        if (result) {
            window.location.href = '/'
        }
    }

    return (
        <Container className='mt-5'>
            <Row className='d-flex justify-content-center align-items-center'>
                <BiUserCircle className='loginIcon' />
                <Form id='signInForm'>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Control name="email" type="email" placeholder="이메일을 입력해주세요" />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Control name="password" type="password" placeholder="비밀번호를 입력해주세요" />
                    </Form.Group>

                    <Container className="d-grid gap-2">
                        <Button onClick={HandleSignInButton} className='my-3' variant='info' type='button' >
                            로그인
                        </Button>
                        <HorizonLine text={"OR"} />
                        <GoogleLogin
                            render={renderProps=>{
                                return (
                                    <Button
                                        onClick={renderProps.onClick}
                                        disabled={renderProps.disabled}
                                        style={{
                                            backgroundColor: "#176BEF",
                                            borderColor: "#176BEF"
                                        }}
                                    >
                                        <i className='fab fa-google'/>&nbsp; Sign In with Google
                                    </Button>
                                )
                            }}
                        />
                    </Container>

                    <Container className='d-flex justify-content-center align-items-center'>
                        <div className='text-left mt-3'>
                            <Link to=""><small className='reset mx-2'>Password Reset</small></Link>
                            ||
                            <Link to=""><small className='reset mx-2'>Quick Recover</small></Link>
                        </div>
                    </Container>
                </Form>
            </Row>
        </Container>
    );
};

export default SignIn;
