import React, { useState, useEffect } from 'react';
import $ from 'jquery'
import {Button, Form, Container, Row, Col, Image} from 'react-bootstrap';
import HorizonLine from "../../component/HorizonLine";
import { getMember } from '../../api/ApiService'
import { uploadFile } from '../../api/FileService'
import { BACKEND_URL } from "../../api/backEndHost"

const MyPage = () => {

    const loadMember = async () => {
        let result = await getMember()
        if (!result) {
            alert('권한이 없습니다')
            window.location.href = '/'
        }

        console.log(result)
        setMember(result)
    }

    const handleChangeFile = (event) => {
        setMyPageFile(event.target.files[0])
    }

    const handleModify = () => {
        $('#file').attr('disabled', false)
    }

    const handleAccess = () => {
        if (myPageFile) {
            console.log(myPageFile)
            uploadFile('/api/member/' + member.email + '/image', 'PATCH', myPageFile)
        }

        $('#file').attr('disabled', true)
    }

    const [myPageFile, setMyPageFile] = useState(null)
    const [member, setMember] = useState({uploadFile : {storeFileName : ''}})

    useEffect(() => {
        loadMember()
    }, [])

    console.log(member)

    return (
        <Container>
            <Form id='signUpForm' className='mt-5'>
                <div className='w-100' style={{ textAlign: "center" }} >
                    <Image thumbnail roundedCircle
                           src={BACKEND_URL + member.uploadFile.storeFileName}
                           className='mb-5'
                            style={{width: "50vh"}}
                    />
                </div>

                <Form.Group className="mb-3">
                    <Form.Label>이메일</Form.Label>
                    <Form.Control placeholder={member.email} id="email" name="email" disabled={true} />
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
                <br/>
                <Row>
                    <Col className='col'>
                        <Button onClick={handleModify} className='w-100 btn btn-primary btn-lg' type='button' id='updateButton'>수정</Button>
                    </Col>
                    <Col>
                        <Button onClick={handleAccess} className='w-100 btn btn-primary btn-lg' type='button' id='modifyButton'>확인</Button>
                    </Col>
                </Row>
            </Form>
        </Container>
    );
};

export default MyPage;
