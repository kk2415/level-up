import React, {useState, useEffect, useContext, useLayoutEffect} from 'react';

import ChannelService from "../../api/ChannelService";

import WaitingMemberFrame from "../../component/channel/manager/WaitingMemberFrame";
import PostFrame from "../../component/channel/manager/PostFrame";
import MemberFrame from "../../component/channel/manager/MemberFrame";

import {Container} from 'react-bootstrap'

import { S3_URL } from "../../api/backEndHost.js"
import $ from 'jquery'

const NUM_ON_SCREEN = 5

const ChannelManager = () => {
    const getChannelId = () => {
        let pathname = decodeURI($(window.location).attr('pathname'))

        return pathname.substring(pathname.indexOf('/', 2) + 1, pathname.lastIndexOf('/'))
    }

    const getLastPostPagerNum = (count) => {
        return Math.floor(count / NUM_ON_SCREEN) + 1;
    }

    const getLastMemberPagerNum = (count) => {
        return Math.floor(count / NUM_ON_SCREEN) + 1;
    }

    const getLastWaitingMemberPagerNum = (count) => {
        return Math.floor(count / NUM_ON_SCREEN) + 1;
    }

    const loadManager = async (channelId) => {
        let result = await ChannelService.getManager(channelId)

        setManager(result)
    }

    const handleChannelModify = () => {
        window.location.href = '/channel/modify/' + channelId
    }

    const [channelId, setChannelId] = useState(getChannelId())
    const [manager, setManager] = useState(null)

    const [lastPostPagerNum, setLastPostPagerNum] = useState(0)
    const [lastMemberPagerNum, setLastMemberPagerNum] = useState(0)
    const [lastWaitingMemberPagerNum, setLastWaitingMemberPagerNum] = useState(0)

    useEffect(() => {
        if (manager) {
            console.log(manager)

            setLastPostPagerNum(getLastPostPagerNum(manager.channelInfo.postCount))
            setLastMemberPagerNum(getLastMemberPagerNum(manager.channelInfo.memberCount))
            setLastWaitingMemberPagerNum(getLastWaitingMemberPagerNum(manager.channelInfo.waitingMemberCount))
        }
    })

    useLayoutEffect(() => {
        setChannelId(getChannelId())
        loadManager(channelId)
    },[])

    return (
        <>
            {
                manager &&
                <div className="h-100 row align-items-center">
                    <div className="col">
                        <div className="card text-center">
                            <h5 className="card-header">채널정보</h5>
                            <div className="card-body">
                                <div className="justify-content-center">
                                    <img src={S3_URL + manager.channelInfo.thumbnail} id="thumbnail" className="img-thumbnail img-fluid rounded-circle"
                                         width="200px" height="200px"/>
                                    <div className="input-group mb-3">
                                        <span className="input-group-text" id="inputGroup-sizing-default1">채널명</span>
                                        <input type="text" className="form-control" id="channelName"
                                               aria-label="Sizing example input" value={manager.channelInfo.channelName}
                                               aria-describedby="inputGroup-sizing-default" disabled/>
                                    </div>
                                    <div className="input-group mb-3">
                                        <span className="input-group-text" id="inputGroup-sizing-default2">매니저</span>
                                        <input type="text" className="form-control" id="manager"
                                               aria-label="Sizing example input" value={manager.channelInfo.manager}
                                               aria-describedby="inputGroup-sizing-default" disabled/>
                                    </div>
                                    <div className="input-group mb-3">
                                        <span className="input-group-text" id="inputGroup-sizing-default3">채널 등록일</span>
                                        <input type="text" className="form-control" id="date"
                                               aria-label="Sizing example input" value={manager.channelInfo.date}
                                               aria-describedby="inputGroup-sizing-default" disabled/>
                                    </div>
                                    <div className="input-group mb-3">
                                        <span className="input-group-text" id="inputGroup-sizing-default4">회원수</span>
                                        <input type="text" className="form-control" id="memberCount"
                                               aria-label="Sizing example input" value={manager.channelInfo.memberCount}
                                               aria-describedby="inputGroup-sizing-default" disabled/>
                                    </div>
                                    <div className="input-group mb-3">
                                        <span className="input-group-text" id="inputGroup-sizing-default5">게시글 개수</span>
                                        <input type="text" className="form-control" id="postCount"
                                               aria-label="Sizing example input" value={manager.channelInfo.postCount}
                                               aria-describedby="inputGroup-sizing-default" disabled/>
                                    </div>
                                    <br/>
                                    <div className="row">
                                        <div className="col">
                                            <button onClick={handleChannelModify} className="w-100 btn btn-primary btn-lg"
                                                    type="button" id="updateButton">수정
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            }

            <br/>
            <br/>

            {
                manager &&
                // <div className="row row-cols-1 row-cols-md-3 g-4">
                //     <WaitingMemberFrame channelId={channelId} waitingMemberCount={manager.channelInfo.waitingMemberCount} />
                //     <MemberFrame channelId={channelId} memberCount={manager.channelInfo.memberCount} />
                //     <PostFrame channelId={channelId} postCount={manager.channelInfo.postCount} />
                // </div>
                <Container className="row row-cols-1 row-cols-md-3 g-4">
                    <WaitingMemberFrame channelId={channelId} waitingMemberCount={manager.channelInfo.waitingMemberCount} />
                    <MemberFrame channelId={channelId} memberCount={manager.channelInfo.memberCount} />
                    <PostFrame channelId={channelId} postCount={manager.channelInfo.postCount} />
                </Container>
            }

        </>
    );
};

export default ChannelManager;
