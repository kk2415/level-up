import React, {useState, useEffect, useContext, useLayoutEffect} from 'react';
import {Table, Container, Col, Row, Form, Button, Card} from 'react-bootstrap'

import CommentService from "../../api/CommentService";
import VoteService from "../../api/VoteService";
import ReplyComment from "./ReplyComment";
import WriteReplyComment from './WriteReplyComment'

const Comment = ({comment, articleId, identity}) => {
    const [onHideReplyComment, setOnHideShowReplyComment] = useState(false)
    const [replyComment, setReplyComment] = useState(null)
    const [writingReplyComment, setWritingReplyComment] = useState(false)
    const [voteCount, setVoteCount] = useState(comment.voteCount)
    const [replyCount, setReplyCount] = useState(comment.replyCount)

    const createVote = async () => {
        let voteRequest = {
            'articleId' : comment.id,
            'identity' : 'COMMENT',
        }

        let result = await VoteService.create(voteRequest)
        if (result != null) {
            setVoteCount(voteCount + 1)
        }
    }

    const loadReplyComment = async () => {
        let reply = await CommentService.getReply(comment.id)

        setReplyComment(reply.data)
    }

    const showReplyCommentList = async () => {
        setOnHideShowReplyComment(!onHideReplyComment)
    }

    useEffect(() => {
        loadReplyComment(comment.id)
    }, [onHideReplyComment, writingReplyComment])

    return (
        <>
            {
                comment &&
                <Container id="comment" className="comment col">
                    <Container className='border-bottom border-top'>
                        <span id="commentWriter">{comment.writer}</span>
                        &nbsp;&nbsp;&nbsp;
                        <span id="commentDate">{comment.dateCreated}</span>
                        <p id="commentContent">{comment.content}</p>
                        <span id="commentVote" className="float-end">
                            <button onClick={createVote} className="btn-sm btn-primary" type="button">{'추천 ' + voteCount}</button>
                        </span>
                        {
                            comment.replyCount > 0 &&
                            <button onClick={showReplyCommentList} className="btn-sm btn-primary" id="replyButton">답글 {replyCount}</button>
                        }
                        {
                            comment.replyCount <= 0 &&
                            <button onClick={showReplyCommentList} className="btn-sm btn-primary" id="replyButton">답글쓰기</button>
                        }
                    </Container>
                    {
                        onHideReplyComment &&
                        <Container className="container" id="replyList">
                            {
                                replyComment &&
                                replyComment.map((reply) => (
                                    <ReplyComment reply={reply} />
                                ))
                            }
                            <WriteReplyComment setWritingReplyComment={setWritingReplyComment}
                                               setReplyCount={setReplyCount}
                                               replyCount={replyCount}
                                               parentCommentId={comment.id}
                                               articleId={articleId}
                                               identity={identity} />
                        </Container>
                    }
                </Container>
            }
        </>
    );
};

export default Comment;
