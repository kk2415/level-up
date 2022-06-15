import React from 'react';
import { send } from "../../../api/request"
import {Container} from "react-bootstrap";

const WaitingMemberRow = ({info, channelId}) => {
    const deniedMember = async () => {
        await send('/api/channel/' + channelId + '/waiting-member/' + info.email, 'DELETE')
            .then(() => {
                alert('채널 가입을 거절하였습니다')
                window.location.href = '/channel/' + channelId + '/manager'
            })
            .catch((error) => {
                console.log(error)
            })
    }

    const accessMember = async () => {
        await send('/api/channel/' + channelId + '/member/' + info.email, 'POST', {})
            .then(() => {
                alert('채널 가입을 승인하였습니다.')
                window.location.href = '/channel/' + channelId + '/manager'
            })
            .catch((error) => {
                console.log(error)
            })
    }

    return (
        <li id="waitingMember" className="list-group-item">
            <span className="float-start">{info.email}</span>
            <button onClick={deniedMember} className="btn-sm btn-primary btn float-end">거절 버튼</button>
            <button onClick={accessMember} className="btn-sm btn-primary btn float-end">수락 버튼</button>
        </li>
    );
};

export default WaitingMemberRow;
