import React, {useState, useEffect, useContext, useLayoutEffect} from 'react';
import {Container, Col, Row, Form, Button, Card} from 'react-bootstrap'

import ChannelService from "../../../api/ChannelService";
import {MemberService} from "../../../api/MemberService";

import MemberRow from "../../../component/channel/manager/MemberRow"
import Pager from "../../pager/Pager";

import { BACKEND_URL } from "../../../api/backEndHost.js"
import $ from 'jquery'
import {send} from "../../../api/request";

const PAGER_LENGTH = 5

const MemberFrame = ({channelId, memberCount}) => {

    const [members, setMembers] = useState(null)
    const [curPage, setCurPage] = useState(1)

    const loadMembers = async (channelId, curPage, PAGER_LENGTH) => {
        let url = '/api/channel/' + channelId + '/members/?page=' + curPage + '&count=' + PAGER_LENGTH

        await send(url, 'GET')
            .then((result) => {
                setMembers(result.data)
            })
            .catch((error) => {
                console.log(error)
            })
    }

    const onNext = (currentPage, lastPagerNum, pagerLength, searchCondition) => {
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let nextPage = startNum + pagerLength

        if (nextPage <= lastPagerNum) {
            setCurPage(nextPage)
        }
        else {
            alert("다음 페이지가 없습니다")
        }
    }

    const onPrev = (currentPage, lastPagerNum, pagerLength, searchCondition) => {
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let previousPage = startNum - pagerLength

        if (previousPage > 0) {
            setCurPage(previousPage)
        }
        else {
            alert("이전 페이지가 없습니다")
        }
    }

    useEffect(() => {
        loadMembers(channelId, curPage, PAGER_LENGTH)
    }, [curPage])

    return (
        <>
            {
                members &&
                <div className="col">
                    <div className="card">
                        <h5 className="card-header">신청 회원</h5>
                        <div className="card-body">
                            <ul id="waitingMemberList" className="list-group list-group-flush">
                                {
                                    members.map((member) => (
                                        <MemberRow info={member} channelId={channelId} />
                                    ))
                                }
                            </ul>
                        </div>
                        <div className="card-footer">
                            <Pager setCurPage={setCurPage} currentPage={curPage}
                                   pagerLength={PAGER_LENGTH} postsCount={memberCount}
                                   searchCondition={{}} onNext={onNext} onPrev={onPrev}
                             />
                        </div>
                    </div>
                </div>
            }
        </>
    );
};

export default MemberFrame;
