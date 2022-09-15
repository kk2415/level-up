import React, { useState, useEffect } from 'react';
import {useNavigate} from 'react-router-dom'

import $ from 'jquery'
import {Button, Form, Container, Row, Col, Image} from 'react-bootstrap';
import HorizonLine from "../../component/HorizonLine";
import { MemberService } from '../../api/service/MemberService'
import { S3_URL } from "../../api/backEndHost"

import '../../css/mypage.css'

const MyPage = () => {
    const navigate = useNavigate();

    const loadMember = async () => {
        let result = await MemberService.get(localStorage.getItem('id'))
        if (!result) {
            alert('권한이 없습니다')
            navigate('/')
            // window.location.href = '/'
        }

        setMember(result)
    }

    const handleChangeFile = (event) => {
        setMyPageFile(event.target.files[0])
    }

    const handleModify = () => {
        setOnModifyButton(true)

        $('#file').attr('disabled', false)
        $('#nickname').attr('disabled', false)
    }

    const handleAccess = async () => {
        if (onModifyButton) {
            let profileImage = member.uploadFile
            if (myPageFile) {
                profileImage = await MemberService.modifyProfile(member.id, myPageFile)
                if (profileImage === null) {
                    alert('이미지 저장을 실패하였습니다.')
                }
            }

            let updateMember = {
                nickname : $('#nickname').val(),
                profileImage : profileImage,
            }

            if ($('#nickname').val() === '') {
                updateMember.nickname = member.nickname
            }

            let result = await MemberService.modify(member.id, updateMember);
            if (result) {
                alert('수정되었습니다')
            }

            $('#file').attr('disabled', true)
            $('#nickname').attr('disabled', true)

            setOnModifyButton(false)
            window.location.reload()
        }
    }

    const handleConfirmEmail = () => {
        navigate('/confirm-email')
    }

    const handleDeleteMemberButton = () => {
        if (window.confirm('정말 탈퇴하시겠습니까?')) {
            let result = MemberService.delete(member.id);

            if (result) {
                alert('탈퇴되었습니다.')

                MemberService.signOut()
            }
        }
    }

    const [myPageFile, setMyPageFile] = useState(null)
    const [onModifyButton, setOnModifyButton] = useState(false)
    const [member, setMember] = useState(null)

    useEffect(() => {
        loadMember()
    }, [])

    return (
        <>
            {
                member &&
                <Container style={{width: "100%", fontFamily: "sans-serif"}}>
                    <Row className='d-flex justify-content-center align-items-center'>
                        <Form id='signUpForm' className='mt-5' style={{width: "80%"}}>
                            {
                                member.uploadFile &&
                                <Container className='w-100' style={{ textAlign: "center", width: "10xp", height: "10xp" }}>
                                    <Image thumbnail roundedCircle fluid
                                           src={S3_URL + member.uploadFile.storeFileName}
                                           className='mb-5 img-fluid'
                                           id='profileImage'
                                           style={{width: "30%", height: "30%", objectFit: "contain"}}
                                    />
                                </Container>

                            }

                            {
                                <button onClick={handleConfirmEmail} className='btn btn-info w-100 mb-5'>
                                    이메일 인증 하기
                                </button>
                            }

                            <Form.Group className="mb-3">
                                <Form.Label>이메일</Form.Label>
                                <Form.Control placeholder={member.email} id="email" name="email" disabled={true} />
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>이름</Form.Label>
                                <Form.Control placeholder={member.name} id="name" name="name" disabled={true} />
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>닉네임</Form.Label>
                                <Form.Control placeholder={member.nickname} id="nickname" name="nickname" disabled={true} />
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>전화번호</Form.Label>
                                <Form.Control placeholder={member.phone} id="phone" name="phone" disabled={true} />
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>생년월일</Form.Label>
                                <Form.Control placeholder={member.birthday} id="birthday" name="birthday" disabled={true} />
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <Form.Label>성별</Form.Label>
                                <Form.Control placeholder={member.gender} id="gender" name="gender" disabled={true} />
                            </Form.Group>

                            <Form.Group>
                                <Form.Label>프로필 사진 선택</Form.Label>
                                <Form.Control onChange={handleChangeFile} id='file' type='file' disabled={true} />
                            </Form.Group>

                            <HorizonLine text={""} />

                            <button onClick={handleDeleteMemberButton} className='btn btn-danger w-100 mb-5'>
                                탈퇴
                            </button>

                            <Row>
                                <Col className='col'>
                                    <Button onClick={handleModify} className='w-100 btn btn-primary btn-lg' type='button' id='updateButton'>수정</Button>
                                </Col>
                                <Col>
                                    <Button onClick={handleAccess} className='w-100 btn btn-primary btn-lg' type='button' id='modifyButton'>확인</Button>
                                </Col>
                            </Row>
                        </Form>
                    </Row>
                </Container>
            }
        </>
    );
};

export default MyPage;
