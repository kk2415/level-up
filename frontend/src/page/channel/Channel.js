import React, {useState, useEffect, useContext, useLayoutEffect} from 'react';
import {Link, useNavigate} from 'react-router-dom'

import $ from 'jquery'
import ChannelService from '../../api/ChannelService'
import PostService from '../../api/PostService'
import {Container, Col, Row, Form, Button, Card, FormControl} from 'react-bootstrap'
import { ChannelTable } from '../../component/channel/ChannelTable'
import {TOKEN} from "../../api/token";
import Pager from "../../component/pager/Pager";
import ChannelNotice from '../../component/channelNotice/ChannelNotice'

const Channel = () => {
    const navigate = useNavigate();

    const getChannelId = () => {
        let pathname = $(window.location).attr('pathname')

        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    const getSearchCondition = () => {
        let queryString = decodeURI($(window.location).attr('search'))
        let searchCondition = {}

        if (!queryString.includes("field", 0) || !queryString.includes("query", 0)) {
            searchCondition.field = ""
            searchCondition.querys = ""
        }

        if (queryString.includes("field", 0)) {
            let queryStringOfPostSearch = queryString.substr(queryString.search("field"));
            let firstIndex = queryStringOfPostSearch.indexOf("=") + 1;
            let endIndex = queryStringOfPostSearch.indexOf("&");

            searchCondition.field = queryStringOfPostSearch.substring(firstIndex, endIndex)
        }

        if (queryString.includes("query", 0)) {
            let queryStringOfPostSearch = queryString.substr(queryString.search("query"));
            let firstIndex = queryStringOfPostSearch.indexOf("=") + 1
            searchCondition.querys = queryStringOfPostSearch.substr(firstIndex)
        }

        return searchCondition
    }

    const getCurrentPage = () => {
        let queryString = decodeURI($(window.location).attr('search'))

        if (queryString.indexOf('&') !== -1) {
            return queryString.substring(queryString.indexOf('=') + 1, queryString.indexOf('&'))
        }

        return Number(queryString.substr(queryString.indexOf('=') + 1))
    }

    const POST_NUM_ON_SCREEN = 10
    const NOTICE_NUM_ON_SCREEN = 5
    const PAGER_LENGTH = 5

    const [channelId, setChannelId] = useState(getChannelId())
    const [searchCondition, setSearchCondition] = useState(getSearchCondition())
    const [curPage, setCurPage] = useState(getCurrentPage())

    const [channelName, setChannelName] = useState(null)
    const [posts, setPosts] = useState({})
    const [postsCount, setPostsCount] = useState(0)

    const loadChannelName = async (channelId) => {
        let result = await ChannelService.get(channelId)
        setChannelName(result.name)
    }

    const loadPostCount = async (channelId, searchCondition) => {
        let count = await PostService.count(channelId, searchCondition)

        setPostsCount(Number(count))
    }

    const handleWriting = () => {
        if (sessionStorage.getItem(TOKEN)) {
            navigate('/post/create?channel=' + channelId)
        }
        else {
            alert('로그인해야됩니다.')
        }
    }

    const handleSearch = () => {
        let searchCondition = {
            field : $('#field').val(),
            querys : $('#search').val(),
        }

        let url = '/channel/' + channelId + '?page=' + 1 + '&' +
            'field=' + searchCondition.field + '&' + 'query=' + searchCondition.querys

        if (searchCondition.field === "" || searchCondition.querys === "") {
            url = '/channel/' + channelId + '?page=' + 1
        }

        window.location.href = url
    }

    const searchKeyDown = (event) => {
        if (event.keyCode === 13) {
            event.preventDefault()
            handleSearch()
        }
    }

    const handleGoHome = () => {
        navigate('/')
    }

    const onPagerNextButton = async (currentPage, lastPagerNum, pagerLength, searchCondition) => {
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let nextPage = startNum + pagerLength

        let url = '/channel/' + channelId + '?page=' + nextPage + '&field='
            + searchCondition.field + '&' + 'query=' + searchCondition.querys

        if (searchCondition.field === "" || searchCondition.querys === "") {
            url = '/channel/' + channelId + '?page=' + nextPage
        }

        if (nextPage <= lastPagerNum) {
            $('#next').attr('href', url)
        }
        else {
            alert("다음 페이지가 없습니다")
        }
    }

    const onPagerPrevButton = async (currentPage, lastPagerNum, pagerLength, searchCondition) => {
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let previousPage = startNum - pagerLength

        let url = '/channel/' + channelId + '?page=' + previousPage

        if (searchCondition !== undefined && searchCondition.field !== "") {
            url = '/channel/' + channelId + '?page=' + previousPage + '&field='
                + searchCondition.field + '&' + 'query=' + searchCondition.querys
        }

        if (previousPage > 0) {
            $('#previous').attr('href', url)
        }
        else {
            alert("이전 페이지가 없습니다")
        }
    }

    useEffect(() => {
        loadChannelName(channelId)
        loadPostCount(channelId, searchCondition)

    }, [channelName, curPage])

    return (
        <>
            {
                channelName &&
                <Container>
                    <h2 className="page-section-heading text-center text-uppercase text-secondary mb-0" id="channelName">
                        {channelName}
                    </h2>

                    <ChannelNotice channelId={channelId} />
                    <hr />

                    <Container>
                        <div className="row">
                            <div className="col-lg-6 col-sm-12 text-lg-start text-center">
                            </div>
                            <div className="col-lg-6 col-sm-12 text-lg-end text-center">
                                <nav className="navbar navbar-expand-lg navbar-light float-end">
                                    <div className="container-fluid">
                                        <div className="collapse navbar-collapse" id="navbarSupportedContent">
                                            <ul className="navbar-nav d-flex">
                                                <li className="nav-item dropdown">
                                                    <Form.Select id="field" name="field" size="sm">
                                                        <option value="title">제목</option>
                                                        <option value="writer">작성자</option>
                                                    </Form.Select>
                                                </li>
                                            </ul>
                                            <Form className="d-flex">
                                                <FormControl
                                                    onKeyDown={searchKeyDown}
                                                    onKeyPress={searchKeyDown}
                                                    id="search"
                                                    name="search"
                                                    type="search"
                                                    placeholder="Search"
                                                    className="me-2"
                                                    aria-label="Search"
                                                />
                                                <Button onClick={handleSearch} id="searchButton" variant="outline-success">Search</Button>
                                            </Form>
                                        </div>
                                    </div>
                                </nav>
                            </div>
                        </div>

                        <ChannelTable channelId={channelId} currentPage={curPage} searchCondition={searchCondition} />

                        <div className="row">
                            <div className="col-lg-6 col-sm-12 text-lg-start text-center">
                                <button onClick={handleGoHome} type="button" className="btn btn-primary btn-sm" id="backButton">홈으로</button>
                            </div>
                            <div className="col-lg-6 col-sm-12 text-lg-end text-center">
                                <button onClick={handleWriting} type="button" className="btn btn-primary btn-sm" id="postingButton">글쓰기</button>
                            </div>
                        </div>

                        <br/><br/>

                        <Pager
                            currentPage={curPage}
                            postsCount={postsCount}
                            pagerLength={PAGER_LENGTH}
                            searchCondition={searchCondition}
                            setCurPage={setCurPage}
                            onNext={onPagerNextButton}
                            onPrev={onPagerPrevButton}
                        />
                    </Container>

                    <div>
                        <span className="text-orange text-strong" id="currentPage">1</span>
                        /
                        <span className="text-orange text-strong" id="lastPage">1</span>
                        pages
                    </div>
                </Container>
            }
        </>
    );
};

export default Channel;
