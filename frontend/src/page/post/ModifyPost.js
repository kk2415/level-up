import React, {useState, useEffect, useContext, useLayoutEffect} from 'react';
import {Link, useNavigate} from 'react-router-dom'

import $ from 'jquery'
import ChannelService from '../../api/ChannelService'
import PostService from '../../api/PostService'
import {Container, Col, Row, Form, Button, Card} from 'react-bootstrap'
import { ChannelTable } from '../../component/channel/ChannelTable'
import {TOKEN} from "../../api/token";
import Board from '../../component/Board'
import Pager from "../../component/pager/Pager";
import RichTextEditor from "../../component/SummerNote";

const ModifyChannel = () => {
    const navigate = useNavigate();

    const getChannelId = () => {
        let search = decodeURI($(window.location).attr('search'))

        return search.substr(search.indexOf('=') + 1)
    }

    const getPostId = () => {
        let pathname = decodeURI($(window.location).attr('pathname'))

        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    const [channelId, setChannelId] = useState(getChannelId())
    const [postId, setPostId] = useState(getPostId())
    const [contents, setContents] = useState('내용')
    const [post, setPost] = useState(null)

    const handleModifyPost = async () => {
        let formData = new FormData(document.getElementById('form'));

        let post = {
            title : formData.get('title'),
            content  : contents,
            category : formData.get('category'),
        }
        console.log(post)

        await PostService.modify(post, postId, channelId)
    }

    const handleCancel = () => {
        window.location.href = '/post/' + postId + '?' + channelId
    }

    const laodPost = async () => {
        let post = await PostService.get(postId)

        console.log(post)
        setPost(post)
    }

    const showPost = () => {
        if (post) {
            $('#title').val(post.title)
            $('#category').val(post.category)
        }
    }

    useEffect(() => {
        showPost()
    }, [post])


    useLayoutEffect(() => {
        setChannelId(getChannelId())
        setPostId(getPostId)
        laodPost()
    }, [])

    return (
        <>
            {
                post &&
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
                            <RichTextEditor setContents={setContents} contents={post.content} />
                        </Form.Group>

                        <div className="row">
                            <div className="col">
                                <button onClick={handleModifyPost} className="w-100 btn btn-primary btn-lg" type="button" id="postingButton">수정
                                </button>
                            </div>
                            <div className="col">
                                <button onClick={handleCancel} className="w-100 btn btn-secondary btn-lg" type="button" id="cancelButton">취소
                                </button>
                            </div>
                        </div>
                    </Form>
                </Container>
            }
        </>
    );
};

export default ModifyChannel;
