import React, {useState, useEffect, useLayoutEffect} from 'react';
import {useNavigate} from 'react-router-dom'

import $ from 'jquery'
import PostService from '../../api/service/PostService'
import ChannelPostService from '../../api/service/ChannelPostService'
import {Container, Form} from 'react-bootstrap'

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
            // content  : contents,
            content  : $('#summernote').val(),
            category : formData.get('category'),
        }
        console.log(post)

        await PostService.modify(post, postId, channelId)
    }

    const handleCancel = () => {
        window.location.href = '/post/' + postId + '?channel=' + channelId
    }

    const loadPost = async () => {
        let post = await ChannelPostService.get(postId)

        setPost(post)
    }

    const showPost = () => {
        if (post) {
            $('#title').val(post.title)
            $('#category').val(post.postCategory)
        }
    }

    function configSummernote() {
        $(document).ready(function() {
            $('#summernote').summernote({
                height: 400,
                minHeight: null,
                maxHeight: null,
                callbacks: {
                    onImageUpload : function(images) {
                        for (let i = 0; i < images.length; i++) {
                            let reader = new FileReader();
                            reader.readAsDataURL(images[i])

                            reader.onloadend = () => {
                                const base64 = reader.result
                                $('#summernote').summernote('insertImage', base64)
                            }
                        }
                    },
                    onPaste: function (e) {
                        let clipboardData = e.originalEvent.clipboardData;
                        if (clipboardData && clipboardData.items && clipboardData.items.length) {
                            let item = clipboardData.items[0];
                            if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                                e.preventDefault();
                            }
                        }
                    }
                }
            })
        })
    }

    useEffect(() => {
        showPost()

        if (post) {
            $('#summernote').val(post.content)
        }
    }, [post])

    useLayoutEffect(() => {
        setChannelId(getChannelId())
        setPostId(getPostId)
        loadPost()
        configSummernote()
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
                            <textarea id='summernote' />
                            {/*<RichTextEditor setContents={setContents} contents={post.content} />*/}
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
