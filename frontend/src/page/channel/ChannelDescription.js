import React, {useState, useEffect, useContext} from 'react';
import $ from 'jquery'
import {useNavigate} from 'react-router-dom'
import ChannelService from '../../api/ChannelService'
import {Container} from 'react-bootstrap'
import {TOKEN} from "../../api/token";
import react from "react";
import {AiOutlineImport} from "react-icons/ai";
import '../../css/channel.css'

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

    const [isManager, setIsManager] = useState(false)
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
            setIsManager(true)
        }
    }

    const loadDescription = async (channelId) => {
        let result = await ChannelService.getDescription(channelId)

        setDescription(result)
    }

    const handleRegisterChannel = async () => {
        if (sessionStorage.getItem(TOKEN)) {
            await ChannelService.addWaitingMember(channelId);
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
                            <h1 className="display-6">
                                <p id="description" dangerouslySetInnerHTML={{__html: description.description}}>
                                </p>
                            </h1>
                            <br /><br /><br /><br />
                        </div>
                        <div className="float-end">
                            {
                                showModify &&
                                <button onClick={handleModifyChannel} className="btn btn-sm btn-secondary" type="button" id="modifyButton">
                                    채널 수정
                                </button>
                            }
                            {
                                showDelete &&
                                <button onClick={handleDeleteChannel} className="btn btn-sm btn-danger" type="button" id="deleteButton">
                                    채널 삭제
                                </button>
                            }
                        </div>
                    </div>
                    <br /><br />

                    {
                        !isManager &&
                        <button onClick={handleRegisterChannel} className="btn btn-lg btn-success" type="button" id="registerStudyButton">
                            가입 신청
                        </button>
                    }

                    <hr />

                    <br /><br />

                    <div>
                        <button onClick={handleBack} className="btn btn-secondary float-start" type="button" id="toAllStudyChannelButton">목록으로
                        </button>
                        <button onClick={handleEnterChannel} className="btn btn-info float-end" type="button" id="enterStudyButton">
                            <AiOutlineImport className='enterChannel' />
                            <span className='fs-3 fw-bold mx-2 '>접속</span>
                        </button>
                    </div>
                </Container>
            }
        </>
    );
};

export default react.memo(ChannelDescription);
