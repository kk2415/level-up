import React from 'react';
import ChannelMemberService from "../../../api/service/ChannelMemberService";

const MemberRowNoImage = ({info, channelId}) => {
    const deniedMember = async () => {
        let result = await ChannelMemberService.delete(info.channelMemberId, channelId);

        if (result) {
            alert('회원을 강제퇴장시켰습니다.')
            window.location.href = '/channel/' + channelId + '/manager'
        }
    }

    return (
        <>
            {
                !info.manager &&
                <li id="member" className="list-group-item">
                    <span className="float-start">{info.email}</span>
                    <button onClick={deniedMember} className="btn-sm btn-primary btn float-end">퇴장</button>
                </li>
            }
        </>
    );
};

export default MemberRowNoImage;
