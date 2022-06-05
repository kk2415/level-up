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

    const getChannelNoticeId = () => {
        let pathname = decodeURI($(window.location).attr('pathname'))

        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    const [channelId, setChannelId] = useState(getChannelId())
    const [channelNoticeId, setChannelNoticeId] = useState(getChannelNoticeId())
    const [contents, setContents] = useState('내용')
    const [channelNotice, setChannelNotice] = useState(null)

    const handleModifyPost = async () => {
        let formData = new FormData(document.getElementById('form'));

        let channelNotice = {
            title : formData.get('title'),
            content  : contents,
        }

        console.log(channelNotice)
        await ChannelService.modifyNotice(channelNotice, channelId, channelNoticeId)
    }

    const handleCancel = () => {
        window.location.href = '/channel/' + channelId + '?page=1'
    }

    const loadChannelNotice = async (channelNoticeId) => {
        let channelNotice = await ChannelService.getNotice(channelNoticeId)

        console.log(channelNotice)
        setChannelNotice(channelNotice)
    }

    const showChannelNotice = () => {
        if (channelNotice) {
            $('#title').val(channelNotice.title)
        }
    }

    useEffect(() => {
        showChannelNotice()
    }, [channelNotice])

    useLayoutEffect(() => {
        setChannelId(getChannelId())
        setChannelNoticeId(getChannelNoticeId())
        loadChannelNotice(channelNoticeId)
    }, [])

    return (
        <>
            {
                channelNotice &&
                <Container>
                    <Form id='form'>
                        <Form.Group className="mb-3">
                            <div className="mb-3">
                                <label htmlFor="title" className="form-label">제목</label>
                                <input type="text" className="form-control" id="title" name="title" placeholder="제목을 작성해주세요" />
                            </div>
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>내용</Form.Label>
                            <RichTextEditor setContents={setContents} contents={channelNotice.content} />
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
