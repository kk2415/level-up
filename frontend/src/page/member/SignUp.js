import React, {useEffect, useState} from 'react';
import { Button, Form, Container } from 'react-bootstrap';
import { GoogleLogin } from 'react-google-login'

import $ from 'jquery'

import HorizonLine from "../../component/HorizonLine";
import { MemberService } from '../../api/service/MemberService'
import {createMemberValidation as validation} from '../../api/validation'
import {EmailService} from "../../api/service/EmailService";

const SignUp = () => {
    const [file, setFile] = useState(null)
    const [onShowAlertMsg, setOnShowAlertMsg] = useState(false)

    const handleChangeFile = (event) => {
        setFile(event.target.files[0])
    }

    async function HandleSignUpButton() {
        let formData = new FormData(document.getElementById('signUpForm'));
        let profileImage = await MemberService.uploadProfile(file)

        let member = {
            name : formData.get('name'),
            nickname : formData.get('nickname'),
            email : formData.get('email'),
            password : formData.get('password'),
            confirmPassword : formData.get('confirmPassword'),
            phone: formData.get('tel'),
            birthday : formData.get('birthday'),
            gender : formData.get('gender'),
            uploadFile : profileImage,
            authority : 'NORMAL',
        }

        if (validate(member)) {
            let newMember = await MemberService.signUp(member);

            if (newMember) {
                let logInMember = {
                    email : formData.get('email'),
                    password : formData.get('password'),
                }

                await MemberService.signIn(logInMember);
                window.location.href = '/confirm-email'

                alert(newMember.name + '님 가입되었습니다')
                alert('인증번호를 발송 중입니다. 잠시만 기다려주세요.')

                await EmailService.sendSecurityCode(newMember.id);
            }
        }
    }

    const validate = (member) => {
        let valid = true;

        removeAlertMassageBox()
        if (!validation.name.test(member.name) || member.name === null) {
            $('#alert').append('<h5>[이름] : 이름은 2자리 이상 15이하, 한글만 입력하세요</h5>')
            valid = false;
        }
        if (!validation.nickname.test(member.nickname) || member.nickname === null) {
            $('#alert').append('<h5>[닉네임] : 닉네임은 2자리 이상 15이하, 영문 대소문자, 숫자, 한글만 입력하세요</h5>')
            valid = false;
        }
        if (!validation.email.test(member.email) || member.email === null) {
            $('#alert').append('<h5>[이매일] : 유효한 이메일 형식이 아닙니다.</h5>')
            valid = false;
        }
        if (!validation.password.test(member.password) || member.password === null) {
            $('#alert').append('<h5>[비밀번호] : 비밀번호는 8자리이상 24이하, 영문자/숫자만 및 특수문자만 입력하세요</h5>')
            valid = false;
        }
        if (member.password !== member.confirmPassword) {
            $('#alert').append('<h5>[비밀번호] : 비밀번호와 비밀번호 확인이 일치하지 않습니다.</h5>')
            valid = false;
        }
        if (!validation.phone.test(member.phone) || member.phone === null) {
            $('#alert').append('<h5>[휴대폰번호] : 유효한 형식이 아닙니다.</h5>')
            valid = false;
        }
        if (member.birthday === null) {
            $('#alert').append('<h5>[생년월일] : 생년월일을 입력해주세요</h5>')
            valid = false;
        }

        if (!valid) {
            showAlertMassageBox()
            setOnShowAlertMsg(true)
        }
        return valid
    }

    const removeAlertMassageBox = () => {
        $('#alert').children('h5').remove();
    }

    const showAlertMassageBox = () => {
        $('#alert').css('display', 'block')
    }

    const hideAlertMassageBox = () => {
        $('#alert').css('display', 'none')
    }

    useEffect(() => {
        hideAlertMassageBox()
    }, [])

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
                <Form.Label>닉네임</Form.Label>
                <Form.Control id="nickname" name="nickname" placeholder="닉네임을 입력해주세요" />
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>전화번호</Form.Label>
                <Form.Control id="tel" name="tel" type="tel" placeholder="010-0000-0000" />
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>생년월일</Form.Label>
                <Form.Control type="date" id="birthday" name="birthday" placeholder="생년월일 6자리를 입력해주세요" />
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

            <div className="alert alert-danger mt-5" id="alert" role="alert">
                <h4 className="alert-heading">입력한 정보에 문제가 있네요!</h4>
                <hr/>
            </div>

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
