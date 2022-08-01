import React from 'react';
import {send} from "../../../api/request";
import {Button} from "@mui/material";
import {Container} from "react-bootstrap";
import ChannelPostService from "../../../api/service/ChannelPostService";

const PostRow = ({info, channelId}) => {

    const deletePost = async () => {
        if (window.confirm('삭제하시겠습니까?')) {
            let result = await ChannelPostService.delete(info.id);
            if (result) {
                alert('게시물이 삭제되었습니다.')
                window.location.href = '/channel/' + channelId + '/manager'
            }
        }
    }

    return (
            <li className="list-group-item">
                <span className="float-start">{info.writer}</span>
                <span className='mx-5'><a href="">{info.title}</a></span>
                <button onClick={deletePost} className="btn-sm btn-primary btn float-end">삭제</button>
            </li>
    );
};

export default PostRow;
