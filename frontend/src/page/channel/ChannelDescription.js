import React, {useState, useEffect, useContext} from 'react';
import $ from 'jquery'
import {Link, useNavigate} from 'react-router-dom'
import ChannelService from '../../api/ChannelService'
import {Container, Col, Row, Form, Button, Card} from 'react-bootstrap'
import {TOKEN} from "../../api/token";
import react from "react";

const getChannelId = () => {
    let pathname = decodeURI($(window.location).attr('pathname'))

    return Number(pathname.substr(pathname.lastIndexOf('/') + 1))
}

const getManagerId = async (channelId) => {
    let channel = await ChannelService.get(channelId);

    return channel.managerId
}

const ChannelDescription = () => {
    const navigate = useNavigate();

    const [showModify, setShowModify] = useState(false)
    const [showDelete, setShowDelete] = useState(false)
    const [description, setDescription] = useState({})
    const [channelId, setChannelId] = useState(getChannelId())

    let managerId = 0

    useEffect(  () => {
        const initDescription = async () => {
            managerId = await getManagerId(channelId)

            VerifyingPermissions(managerId)
            await loadDescription(channelId)
        }

        initDescription()
    }, [channelId])

    const VerifyingPermissions = async (managerId) => {
        console.log(managerId)

        if (managerId === Number(sessionStorage.getItem('id'))) {
            setShowModify(true)
            setShowDelete(true)
        }
    }

    const loadDescription = async (channelId) => {
        let result = await ChannelService.getDescription(channelId)

        setDescription(result)
    }

    const handleRegisterChannel = async () => {
        if (sessionStorage.getItem(TOKEN)) {
            await ChannelService.addWaitingMember(channelId)
            alert('신청되었습니다. 매니저가 수락할 때 까지 기다려주세요.')
        }
        else {
            alert('로그인해야합니다.')
        }
    }

    const handleEnterChannel = () => {
        $(window.location).attr('href', '/channel/' + channelId + '?page=1')
    }

    const handleBack = () => {
        $(window.location).attr('href', '/')
    }

    const handleModifyChannel = () => {
        $(window.location).attr('href', '/channel/modify/' + channelId)
    }

    const handleDeleteChannel = async () => {
        await ChannelService.delete(channelId)
    }

    return (
        <>
            {
                description &&
                <Container>
                    <div className="row row-cols-1">
                        <div className="col border-bottom">
                            <p id="manager" className="h6">{description.managerName}</p>
                            <h1 id="name" className="display-3">{description.name}</h1>
                            <br />

                            <p className="h6">
                        <span>
                            등록일&nbsp;
                            <span id="dateCreated">{description.dateCreated}</span>
                        </span>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                <span>
                            인원&nbsp;
                                    <span id="memberCount">{description.memberCount}</span>
                        </span>
                                &nbsp;&nbsp;&nbsp;

                                <span>
                            /&nbsp;
                                    <span id="limitedMemberNumber">{description.limitedMemberNumber}</span>
                        </span>
                            </p>
                        </div>
                        <div className="col">
                            <br /><br />
                            <h1 className="display-6">
                                <p id="description" dangerouslySetInnerHTML={{__html: description.description}}>

                                </p>
                            </h1>
                            <br /><br /><br /><br />
                        </div>
                        <div className="d-grid gap-2 d-md-block float-end">
                            {
                                showModify &&
                                <button onClick={handleModifyChannel} className="btn btn-primary" type="button" id="modifyButton">수정</button>
                            }
                            {
                                showDelete &&
                                <button onClick={handleDeleteChannel} className="btn btn-primary" type="button" id="deleteButton">삭제</button>
                            }
                        </div>
                    </div>
                    <br /><br />
                    <button onClick={handleRegisterChannel} className="btn btn-primary btn-lg form-control" type="button" id="registerStudyButton">신청하기</button>
                    <hr />

                    <br /><br />

                    <div>
                        <button onClick={handleBack} className="btn btn-primary float-start" type="button" id="toAllStudyChannelButton">목록으로
                        </button>
                        <button onClick={handleEnterChannel} className="btn btn-primary float-end" type="button" id="enterStudyButton">채널 접속</button>
                    </div>
                </Container>
            }
        </>
    );
};

export default react.memo(ChannelDescription);
