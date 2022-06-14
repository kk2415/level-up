import React, {useState, useEffect} from 'react';
import {Container} from 'react-bootstrap'
import CommentService from "../../api/CommentService";
import Comment from './Comment'
import {TOKEN} from "../../api/token";

import $ from 'jquery'

const CommentFrame = ({articleId, identity}) => {
    const [comments, setComments] = useState(null)
    const [onComments, setOnComments] = useState(false)

    const loadComment = async (articleId, identity) => {
        let result = await CommentService.get(articleId, identity)
        console.log(result.data)

        setComments(result.data)
    }

    const createComment = () => {
        let comment = {
            articleId : articleId,
            content : $('#contentOfWritingComment').val(),
            identity : identity,
        }

        if (sessionStorage.getItem(TOKEN)) {
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
            <div id="commentFrame" className="row row-cols-1">
                {
                    comments &&
                    comments.map((info) => (
                        <Comment comment={info} articleId={articleId} identity={identity} />
                    ))
                }

                <Container className='mb-5'>
                    <textarea id="contentOfWritingComment" className="form-control" rows="3" placeholder="댓글을 입력해주세요" />
                    <br/>
                    <button onClick={createComment} className="btn btn-primary float-end" type="button">
                        등록
                    </button>
                </Container>
            </div>
        </>
    );
};

export default CommentFrame;
