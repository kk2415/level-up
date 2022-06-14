import React, {useState, useEffect, useLayoutEffect} from 'react';
import {Container} from 'react-bootstrap'
import $ from 'jquery'

import PostService from '../../api/PostService'
import CommentFrame from '../../component/comment/CommentFrame'
import {useNavigate} from "react-router-dom";
import VoteService from "../../api/VoteService";

const Post = () => {
    const navigate = useNavigate();

    const getPostId = () => {
        let pathname = decodeURI($(window.location).attr('pathname'))
        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    const getChannelId = () => {
        let search = decodeURI($(window.location).attr('search'))
        return search.substr(search.indexOf('=') + 1)
    }

    const [post, setPost] = useState(null)
    const [postId, setPostId] = useState(getPostId())
    const [channelId, setChannelId] = useState(getChannelId())
    const [authentication, setAuthentication] = useState(false)
    const [voteCount, setVoteCount] = useState(0)

    const handleGoChannelButton = () => {
        navigate('/channel/' + channelId + '?page=1')
    }

    const handlePrevPostButton = async () => {
        let prev = await PostService.getPrev(postId)

        if (prev != null) {
            window.location.href = '/post/' + prev.id + '?channel=' + channelId
        }
        else {
            alert("이전 페이지가 없습니다.")
        }
    }

    const handleNextPostButton = async () => {
        let next = await PostService.getNext(postId)

        if (next != null) {
            window.location.href = '/post/' + next.id + '?channel=' + channelId
        }
        else {
            alert("이전 페이지가 없습니다.")
        }
    }

    const handleModifyButton = () => {
        navigate('/post/modify/' + postId + '?channel=' + channelId)
    }

    const handleDeleteButton = async () => {
        await PostService.delete(postId, channelId)
    }

    const loadPost = async (postId) => {
        let post = await PostService.get(postId)

        console.log(post)
        setPost(post)
        setVoteCount(post.voteCount)
    }

    const authorize = (post) => {
        let memberId = sessionStorage.getItem('id')

        if (post && Number(memberId) === post.memberId) {
            setAuthentication(true)
        }
    }

    const createVote = async () => {
        let voteRequest = {
            'articleId' : post.id,
            'identity' : 'POST',
        }

        let result = await VoteService.create(voteRequest)
        if (result != null) {
            setVoteCount(voteCount + 1)
        }
    }

    useEffect(() => {
        authorize(post)
    }, [post])

    useLayoutEffect(() => {
        setPostId(getPostId())
        loadPost(postId)
    }, [])

    return (
        <>
            {
                post &&
                <Container>
                    <div className="row">
                        <div className="col border-bottom">
                            <p id="writer" className="h6">{post.writer}</p>
                            <h1 id="title" className="display-3">{post.title}</h1>
                            <br/>
                            <p className="h6">
                                <span>작성일
                                    &nbsp;
                                    <span id="dateCreated">{post.dateCreated}</span>
                                </span>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <span>조회&nbsp;
                                    <span id="views">{post.views}</span>
                                </span>
                                &nbsp;&nbsp;&nbsp;
                                <span>추천
                                    &nbsp;
                                    <span id="voteCount">{voteCount}</span>
                                </span>
                                &nbsp;&nbsp;&nbsp;
                                <span>
                                    댓글&nbsp;
                                    <span id="commentCount">{post.commentCount}</span>
                                </span>

                                <span className="d-grid gap-2 d-md-block float-end">
                                    <span className="fs-3" id="voteCount2">{voteCount}</span>
                                    &nbsp;&nbsp;&nbsp;
                                    <button onClick={createVote} id="vote" className="btn-sm btn btn-primary">추천</button>
                                </span>
                            </p>
                        </div>

                        <div className="w-100"></div>

                        <div className="col-lg-11">
                            <br/><br/>
                            <h1 className="display-6">
                                <p dangerouslySetInnerHTML={{ __html: post.content }}></p>
                            </h1>
                            <br/><br/><br/><br/>
                        </div>

                        <div className="w-100"></div>

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

                    <CommentFrame articleId={postId} identity={'POST'} />

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

export default Post;
