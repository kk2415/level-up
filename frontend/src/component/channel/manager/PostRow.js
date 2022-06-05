import React from 'react';
import {send} from "../../../api/request";

const PostRow = ({info, channelId}) => {

    const deletePost = async () => {
        await send('/api/post/' + info.id, 'DELETE')
            .then(() => {
                alert('게시물이 삭제되었습니다.')
                window.location.href = '/channel/' + channelId + '/manager'
            })
            .catch((error) => {
                console.log(error)
            })
    }

    return (
        <li id="post" className="list-group-item">
            <span className="float-start">{info.writer}</span>&nbsp;&nbsp;&nbsp;&nbsp;
            <span><a href="">{info.title}</a></span>
            <span onClick={deletePost} className="btn-sm btn-primary btn float-end">삭제 버튼</span>
        </li>
    );
};

export default PostRow;
