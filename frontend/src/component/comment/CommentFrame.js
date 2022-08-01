import React, {useState, useEffect} from 'react';
import {Container} from 'react-bootstrap'
import CommentService from "../../api/service/CommentService";
import Comment from './Comment'
import {TOKEN} from "../../api/token";

import $ from 'jquery'

const CommentFrame = ({articleId, identity}) => {
    const [comments, setComments] = useState(null)
    const [onComments, setOnComments] = useState(false)

    const loadComment = async (articleId, identity) => {
        let result = await CommentService.get(articleId, identity)
        console.log(result.data)
        console.log(localStorage.getItem('id'))
        setComments(result.data)
    }

    const createComment = () => {
        let comment = {
            articleId : articleId,
            content : $('#contentOfWritingComment').val(),
            identity : identity,
        }

        if (localStorage.getItem(TOKEN)) {
            console.log(comment)

            CommentService.create(comment)
            $('#contentOfWritingComment').val('')
            setOnComments(!onComments)
        }
        else {
            alert("댓글을 작성하려면 로그인을 해야합니다.")
        }
    }

    useEffect(() => {
        loadComment(articleId, identity)
    }, [onComments])

    return (
        <>
            <div className="fs-3 mb-1">
                댓글
            </div>
            <div id="commentFrame" className="row row-cols-1">
                <Container className="mb-3">
                    {
                        comments &&
                        comments.map((info) => (
                            <Comment comment={info} articleId={articleId} identity={identity} />
                        ))
                    }
                </Container>
                <Container>
                    <textarea id="contentOfWritingComment" className="form-control" rows="3" placeholder="댓글을 입력해주세요." />
                    <br/>
                    <button onClick={createComment} className="btn btn btn-success float-end mb-5" type="button">
                        등록
                    </button>
                </Container>
            </div>
        </>
    );
};

export default CommentFrame;
