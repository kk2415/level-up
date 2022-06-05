import React from 'react';
import {send} from "../../../api/request";

const MemberRow = ({info, channelId}) => {
    console.log(info)

    const deniedMember = async () => {
        await send('/api/channel/' + channelId + '/member/' + info.email, 'DELETE')
            .then(() => {
                alert('회원을 퇴장시켰습니다.')
                window.location.href = '/channel/' + channelId + '/manager'
            })
            .catch((error) => {
                console.log(error)
            })
    }

    return (
        <li id="member" className="list-group-item">
            <span className="float-start">{info.email}</span>
            <button onClick={deniedMember} className="btn-sm btn-primary btn float-end">퇴장</button>
        </li>
    );
};

export default MemberRow;
