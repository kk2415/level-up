import React, {useState, useEffect, useLayoutEffect} from 'react';
import {Container} from 'react-bootstrap'

import ChannelService from '../../api/ChannelService'
import {ChannelNoticeTable} from "./ChannelNoticeTable";
import {AiFillCaretRight, AiFillCaretLeft} from "react-icons/ai";

const ChannelNotice = ({channelId}) => {

    const [curNoticePage, setCurNoticePage] = useState(1)
    const [notice, setNotice] = useState(null)
    const [noticeCount, setNoticeCount] = useState(0)

    const handleCreateNotice = () => {
        window.location.href = '/channel-notice/create?channel=' + channelId
    }

    const handleNextNoticeList = () => {
        let lastNum = Math.floor(noticeCount / 5) + 1

        if (curNoticePage + 1 > lastNum) {
            alert("다음 페이지가 없습니다.")
        }
        else {
            setCurNoticePage(curNoticePage + 1)
        }
    }

    const handlePrevNoticeList = () => {
        if (curNoticePage - 1 <= 0) {
            alert("이전 페이지가 없습니다.")
        }
        else {
            setCurNoticePage(curNoticePage - 1)
        }
    }

    const handleManageChannel = () => {
        window.location.href = '/channel/' + channelId + '/manager'
    }

    const loadNotice = async (channelId) => {
        let result = await ChannelService.getAllNotice(channelId, curNoticePage)

        if (result.data) {
            console.log(result.data[0].allNoticeCount)
            setNotice(result.data)
            setNoticeCount(result.data[0].allNoticeCount)
        }
    }

    useEffect(() => {
        loadNotice(channelId)
    }, [curNoticePage])

    useLayoutEffect(() => {
        setCurNoticePage(1)
    }, [])

    return (
        <Container>
            <Container className="row row-cols-1">
                <div className="col">
                    <ChannelNoticeTable notices={notice} channelId={channelId} page={curNoticePage} />
                </div>
                <div className="col">
                    {/*<button onClick={handleManageChannel} className="btn btn-primary btn-sm float-start" type="button" id="manager">*/}
                    {/*    채널 관리*/}
                    {/*</button>*/}
                    <div className="d-grid gap-2 d-md-block float-end">
                        {/*<button className="btn btn-secondary btn-sm" type="button" id="deleteNoticeAll">*/}
                        {/*    일괄삭제*/}
                        {/*</button>*/}
                        <button onClick={handleCreateNotice} className="btn btn-secondary btn-sm" type="button" id="createNotice">
                            글쓰기
                        </button>
                    </div>
                    <br/><br/><br/><br/>
                </div>
                <div className="col">
                    <div className="d-grid gap-2 d-md-block float-end">
                        <button onClick={handlePrevNoticeList} className="btn btn-secondary btn-sm" type="button" id="prevNoticeList">
                            <AiFillCaretLeft/>
                        </button>
                        <button onClick={handleNextNoticeList} className="btn btn-secondary btn-sm" type="button" id="nextNoticeList">
                            <AiFillCaretRight/>
                        </button>
                    </div>
                </div>
            </Container>
        </Container>
    );
};

export default ChannelNotice;
