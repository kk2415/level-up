import React, { useState } from 'react';
import { Modal, Button, Form, Container } from 'react-bootstrap';
import { GoogleLogin } from 'react-google-login'
import HorizonLine from "../../component/HorizonLine";
import { signUp } from '../../api/ApiService'
import { uploadFile } from '../../api/FileService'

const SignUp = () => {
    const [file, setFile] = useState(null)

    const handleChangeFile = (event) => {
        setFile(event.target.files[0])
    }

    async function HandleSignUpButton() {
        let formData = new FormData(document.getElementById('signUpForm'));
        let profileImage = await uploadFile('/api/member/image', 'POST', file)
        let member = {
            name : formData.get('name'),
            email : formData.get('email'),
            password : formData.get('password'),
            confirmPassword : formData.get('confirmPassword'),
            phone: formData.get('tel'),
            birthday : formData.get('birthday'),
            gender : formData.get('gender'),
            uploadFile : profileImage,
            authority : 'NORMAL',
        }
        console.log(member)

        let result = await signUp(member)
        if (result) {
            window.location.href = '/'
        }
    }

    return (
        <Form id='signUpForm'>
            <Form.Group className="mb-3">
                <Form.Label>이름</Form.Label>
                <Form.Control id="name" name="name" placeholder="이름을 입력해주세요" />
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>이메일</Form.Label>
                <Form.Control id="email" name="email" type="email" placeholder="이메일을 입력해주세요" />
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>비밀번호</Form.Label>
                <Form.Control id="password" name="password" type="password" placeholder="비밀번호를 입력해주세요" />
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>비밀번호 확인</Form.Label>
                <Form.Control id="confirmPassword" name="confirmPassword" type="password" placeholder="비밀번호를 재입력해주세요" />
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>전화번호</Form.Label>
                <Form.Control id="tel" name="tel" type="tel" placeholder="010-0000-0000" />
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>생년월일</Form.Label>
                <Form.Control id="birthday" name="birthday" placeholder="생년월일 6자리를 입력해주세요" />
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>성별</Form.Label>
                <Form.Select name="gender">
                    <option value="MALE">남성</option>
                    <option value="FEMALE">여성</option>
                </Form.Select>
            </Form.Group>

            <Form.Group>
                <Form.Label>프로필 사진 선택</Form.Label>
                <Form.Control onChange={handleChangeFile} id='file' type='file' />
            </Form.Group>

            <Container className="d-grid gap-2">
                <Button className='my-3' variant='info' type='button' onClick={() => {HandleSignUpButton()}} >
                    회원가입
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
                                <i className='fab fa-google'/>&nbsp; Sign Up with Google
                            </Button>
                        )
                    }}
                />
            </Container>
        </Form>
    );
};

export default SignUp;
