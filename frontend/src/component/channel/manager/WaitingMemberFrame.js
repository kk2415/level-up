import React, {useState, useEffect, useContext, useLayoutEffect} from 'react';
import {Container, Col, Row, Form, Button, Card} from 'react-bootstrap'

import ChannelService from "../../../api/ChannelService";
import {MemberService} from "../../../api/ApiService";

import WaitingMemberRow from "../../../component/channel/manager/WaitingMemberRow";
import MemberRow from "../../../component/channel/manager/MemberRow";
import PostRow from "../../../component/channel/manager/PostRow";
import Pager from "../../pager/Pager";

import { BACKEND_URL } from "../../../api/backEndHost.js"
import $ from 'jquery'

const PAGER_LENGTH = 5

const WaitingMemberFrame = ({channelId, waitingMemberCount}) => {

    const [waitingMembers, setWaitingMembers] = useState(null)
    const [curPage, setCurPage] = useState(1)

    const loadWaitingMembers = async (channelId, curPage, PAGER_LENGTH) => {
        let result = await MemberService.getWaitingMembers(channelId, curPage, PAGER_LENGTH)

        setWaitingMembers(result.data)
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
        loadWaitingMembers(channelId, curPage, PAGER_LENGTH)
    }, [curPage])

    return (
        <>
            {
                waitingMembers &&
                <div className="col">
                    <div className="card">
                        <h5 className="card-header">신청 회원</h5>
                        <div className="card-body">
                            <ul id="waitingMemberList" className="list-group list-group-flush">
                                {
                                    waitingMembers.map((waitingMember) => (
                                        <WaitingMemberRow info={waitingMember} channelId={channelId} />
                                    ))
                                }
                            </ul>
                        </div>
                        <div className="card-footer">
                            <Pager setCurPage={setCurPage} currentPage={curPage}
                                   pagerLength={PAGER_LENGTH} postsCount={waitingMemberCount}
                                   searchCondition={{}} onNext={onNext} onPrev={onPrev}
                             />
                        </div>
                    </div>
                </div>
            }
        </>
    );
};

export default WaitingMemberFrame;
