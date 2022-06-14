import React, {useState, useEffect, useLayoutEffect} from 'react';
import {Container} from 'react-bootstrap'
import $ from 'jquery'

import ChannelService from '../../api/ChannelService'
import CommentFrame from '../../component/comment/CommentFrame'
import {useNavigate} from "react-router-dom";

const DetailChannelNotice = () => {
    const navigate = useNavigate();

    const getChannelNoticeId = () => {
        let pathname = decodeURI($(window.location).attr('pathname'))
        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    const getChannelId = () => {
        let search = decodeURI($(window.location).attr('search'))
        return search.substr(search.indexOf('=') + 1)
    }

    const [channelNotice, setChannelNotice] = useState(null)
    const [channelNoticeId, setChannelNoticeId] = useState(getChannelNoticeId())
    const [channelId, setChannelId] = useState(getChannelId())
    const [authentication, setAuthentication] = useState(false)
    const [voteCount, setVoteCount] = useState(0)

    const handleGoChannelButton = () => {
        navigate('/channel/' + channelId + '?page=1')
    }

    const handlePrevPostButton = async () => {
        let prev = await ChannelService.getPrevNotice(channelNoticeId)

        if (prev != null) {
            window.location.href = '/channel-notice/detail/' + prev.id + '?channel=' + channelId
        }
        else {
            alert("이전 페이지가 없습니다.")
        }
    }

    const handleNextPostButton = async () => {
        let next = await ChannelService.getNextNotice(channelNoticeId)

        if (next != null) {
            window.location.href = '/channel-notice/detail/' + next.id + '?channel=' + channelId
        }
        else {
            alert("다음 페이지가 없습니다.")
        }
    }

    const handleModifyButton = () => {
        navigate('/channel-notice/modify/' + channelNoticeId + '?channel=' + channelId)
    }

    const handleDeleteButton = async () => {
        await ChannelService.deleteNotice(channelId, channelNoticeId)
    }

    const loadChannelNotice = async (channelNoticeId) => {
        let notice = await ChannelService.getNotice(channelNoticeId)

        console.log(notice)
        setChannelNotice(notice)
        // setVoteCount(post.voteCount)
    }

    const authorize = (channelNotice) => {
        let memberId = sessionStorage.getItem('id')

        if (channelNotice && Number(memberId) === channelNotice.memberId) {
            setAuthentication(true)
        }
    }

    const createVote = async () => {
        // let voteRequest = {
        //     'articleId' : post.id,
        //     'identity' : 'POST',
        // }
        //
        // let result = await VoteService.create(voteRequest)
        // if (result != null) {
        //     setVoteCount(voteCount + 1)
        // }
    }

    useEffect(() => {
        authorize(channelNotice)
    }, [channelNotice])

    useLayoutEffect(() => {
        setChannelNoticeId(getChannelNoticeId())
        loadChannelNotice(channelNoticeId)
    }, [])

    return (
        <>
            {
                channelNotice &&
                <Container>
                    <div className="row">
                        <div className="col border-bottom">
                            <p id="writer" className="h6">{channelNotice.writer}</p>
                            <h1 id="title" className="display-3">{channelNotice.title}</h1>
                            <br/>
                            <p className="h6">
                                <span>작성일
                                    &nbsp;
                                    <span id="dateCreated">{channelNotice.dateCreated}</span>
                                </span>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>조회&nbsp;
                                    <span id="views">{channelNotice.views}</span>
                                </span>
                                &nbsp;&nbsp;&nbsp;
                                <span>추천
                                    &nbsp;
                                    <span id="voteCount">0</span>
                                </span>
                                &nbsp;&nbsp;&nbsp;
                                <span>
                                    댓글&nbsp;
                                    <span id="commentCount">{channelNotice.commentCount}</span>
                                </span>

                                <span className="d-grid gap-2 d-md-block float-end">
                                    <span className="fs-3" id="voteCount2">0</span>
                                    &nbsp;&nbsp;&nbsp;
                                    <button onClick={createVote} id="vote" className="btn-sm btn btn-primary">추천</button>
                                </span>
                            </p>
                        </div>

                        <div className="w-100"/>

                        <div className="col-lg-11">
                            <br/><br/>
                            <h1 className="display-6">
                                <p dangerouslySetInnerHTML={{ __html: channelNotice.content }}/>
                            </h1>
                            <br/><br/><br/><br/>
                        </div>

                        <div className="w-100"/>

                        <div className="d-grid gap-2 d-md-block float-end">
                            {
                                authentication &&
                                <button onClick={handleModifyButton} className="btn btn-primary btn-sm" type="button" id="modifyButton">
                                    수정
                                </button>
                            }
                            {
                                authentication &&
                                <button onClick={handleDeleteButton} className="btn btn-primary btn-sm" type="button" id="deleteButton">
                                    삭제
                                </button>
                            }
                        </div>
                    </div>

                    <hr/>

                    <div className="col">
                        <br />
                        <div className="fs-3">
                            댓글<span className="fs-3" id="commentCount2"/>
                        </div>
                        <br />
                    </div>

                    <CommentFrame articleId={channelNoticeId} identity={'CHANNEL_NOTICE'} />

                    <div>
                        <button onClick={handleGoChannelButton} className="btn btn-primary btn-lg float-start" type="button" id="allPostButton">목록으로
                        </button>
                        <div className="d-grid gap-2 d-md-block float-end">
                            <button onClick={handlePrevPostButton} className="btn btn-primary btn-lg" type="button" id="prevPostButton">이전글
                            </button>
                            <button onClick={handleNextPostButton} className="btn btn-primary btn-lg" type="button" id="nextPostButton">다음글
                            </button>
                        </div>
                    </div>
                </Container>
            }
        </>
    );
};

export default DetailChannelNotice;
