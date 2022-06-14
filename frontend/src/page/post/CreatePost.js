import React, {useState, useEffect} from 'react';
import {Link, useNavigate} from 'react-router-dom'

import $ from 'jquery'
import PostService from '../../api/PostService'
import {Container, Form} from 'react-bootstrap'
import RichTextEditor from "../../component/SummerNote";

const CreatePost = () => {
    const navigate = useNavigate();

    const getChannelId = () => {
        let search = decodeURI($(window.location).attr('search'))

        return search.substr(search.indexOf('=') + 1)
    }

    const [channelId, setChannelId] = useState(getChannelId())
    const [contents, setContents] = useState('내용')

    const handleCreatePost = async () => {
        let formData = new FormData(document.getElementById('form'));

        if (!validate(formData)) {
            return
        }

        let post = {
            channelId : channelId,
            title : formData.get('title'),
            content  : contents,
            category : formData.get('category'),
        }
        console.log(post)

        await PostService.create(post)
    }

    const validate = (formData) => {
        let valid = true;

        removeAlertMassageBox()
        if (formData.get('title') === null || formData.get('title') === "") {
            $('#alert').append('<h5>[제목] : 제목을 입력해주세요.</h5>')
            valid = false;
        }
        if (formData.get('category') === 'NONE') {
            $('#alert').append('<h5>[카테고리] : 카테고리를 선택해주세요.</h5>')
            valid = false;
        }
        if (contents === null || contents === "") {
            $('#alert').append('<h5>[내용] : 내용을 입력해주세요</h5>')
            valid = false;
        }

        if (!valid) {
            showAlertMassageBox()
        }
        return valid
    }

    const handleCancel = () => {
        navigate('/channel/' + channelId + '?page=1')
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
        <>
            <Container>
                <Form id='form'>
                    <Form.Group className="mb-3">
                        <div className="mb-3">
                            <label htmlFor="title" className="form-label">제목</label>
                            <input type="text" className="form-control" id="title" name="title" placeholder="제목을 작성해주세요" />
                        </div>
                    </Form.Group>

                    <Form.Group className='mb-4'>
                        <Form.Label>카테고리</Form.Label>
                        <Form.Select aria-label="Default select example" name="category" id="category">
                            <option selected value="NONE">카테고리를 선택해주세요</option>
                            <option value="INFO">정보</option>
                            <option value="SELF_IMPROVEMENT">자기계발</option>
                            <option value="TIP">팁</option>
                            <option value="LIFE">생활</option>
                            <option value="TALK">수다</option>
                            <option value="INTRODUCE">인사</option>
                        </Form.Select>
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>내용</Form.Label>
                        <RichTextEditor setContents={setContents} contents={contents} />
                    </Form.Group>

                    <div className="alert alert-danger mt-5" id="alert" role="alert">
                        <h4 className="alert-heading">입력한 정보에 문제가 있네요!</h4>
                        <hr/>
                    </div>

                    <div className="row">
                        <div className="col">
                            <button onClick={handleCreatePost} className="w-100 btn btn-primary btn-lg" type="button" id="postingButton">작성
                            </button>
                        </div>
                        <div className="col">
                            <button onClick={handleCancel} className="w-100 btn btn-secondary btn-lg" type="button" id="cancelButton">취소
                            </button>
                        </div>
                    </div>
                </Form>
            </Container>
        </>
    );
};

export default CreatePost;
