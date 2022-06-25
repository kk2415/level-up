import React, {useState, useEffect, useLayoutEffect} from 'react';
import {useNavigate} from 'react-router-dom'

import $ from 'jquery'
import {Container, Form, Tabs, Tab, Row} from 'react-bootstrap'
import { ArticleTable } from '../../component/article/ArticleTable'
import {TOKEN} from "../../api/token";
import Pager from "../../component/pager/Pager";
import ArticleService from "../../api/service/ArticleService";

const Qna = () => {
    const navigate = useNavigate();

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
        console.log(queryString)
        console.log(queryString.indexOf('page'))

        if (queryString.indexOf('&') !== -1) {
            return queryString.substring(queryString.indexOf('=') + 1, queryString.indexOf('&'))
        }

        return Number(queryString.substr(queryString.indexOf('=') + 1))
    }

    const loadQnaPosts = async (searchCondition) => {
        const pageable = 'page=' + (curPage - 1) + '&size=10&sort=id,desc'
        let result = await ArticleService.getAll('QNA', pageable, searchCondition)

        console.log(result)

        setQnaPost(result.content)
        setQnaPostsCount(result.totalElements)
    }

    const handleWriting = () => {
        if (sessionStorage.getItem(TOKEN)) {
            navigate('/article/create?articleType=QNA')
        }
        else {
            alert('로그인해야됩니다.')
        }
    }

    const handleSearch = (event) => {
        event.preventDefault()

        let searchCondition = {
            field : $('#field').val(),
            querys : $('#search').val(),
        }

        let url = '/qna?page=1&field=' + searchCondition.field + '&query=' + searchCondition.querys

        if (searchCondition.field === "" || searchCondition.querys === "") {
            url = '/qna?page=1'
        }

        window.location.href = url
    }

    const onPagerNextButton = async (currentPage, lastPagerNum, pagerLength, searchCondition) => {
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let nextPage = startNum + pagerLength

        let url = '/qna?page=' + nextPage + '&field=' + searchCondition.field + '&query=' + searchCondition.querys

        if (searchCondition.field === "" || searchCondition.querys === "") {
            url = '/qna?page=' + nextPage
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

        let url = '/qna?page=' + previousPage + '&field=' + searchCondition.field + '&query=' + searchCondition.querys

        if (searchCondition.field === "" || searchCondition.querys === "") {
            url = '/qna?page=' + previousPage
        }

        if (previousPage > 0) {
            $('#previous').attr('href', url)
        }
        else {
            alert("이전 페이지가 없습니다")
        }
    }

    const searchKeyDown = (event) => {
        if (event.keyCode === 13) {
            handleSearch()
        }
    }

    const handleGoHome = () => {
        navigate('/')
    }

    const POST_NUM_ON_SCREEN = 10
    const NOTICE_NUM_ON_SCREEN = 5
    const PAGER_LENGTH = 5

    const [searchCondition, setSearchCondition] = useState(getSearchCondition())

    const [curPage, setCurPage] = useState(getCurrentPage())
    const [qnaPostsCount, setQnaPostsCount] = useState(0)
    const [qnaPost, setQnaPost] = useState(null)

    useEffect(() => {
        loadQnaPosts(searchCondition)

    }, [curPage])

    return (
        <>
            {
                qnaPost &&
                <Container>
                    <Container>
                        <Row className='d-flex justify-content-center align-items-center'>
                            <h2 className="page-section-heading text-center text-uppercase text-secondary mb-3" id="channelName">
                                QnA 게시판
                            </h2>
                        </Row>
                    </Container>

                    <ArticleTable articles={qnaPost} />

                    <div className="row">
                        <div className="col-lg-6 col-sm-12 text-lg-start text-center">
                            <button onClick={handleGoHome} type="button" className="btn btn-secondary btn-sm" id="backButton">홈으로</button>
                        </div>
                        <div className="col-lg-6 col-sm-12 text-lg-end text-center">
                            <button onClick={handleWriting} type="button" className="btn btn-secondary btn-sm" id="postingButton">글쓰기</button>
                        </div>
                    </div>

                    <Pager
                        currentPage={curPage}
                        postsCount={qnaPostsCount}
                        pagerLength={PAGER_LENGTH}
                        searchCondition={searchCondition}
                        setCurPage={setCurPage}
                        onNext={onPagerNextButton}
                        onPrev={onPagerPrevButton}
                    />

                </Container>
            }
        </>
    );
};

export default Qna;
