import React, {useState, useEffect, useLayoutEffect} from 'react';
import {useNavigate} from 'react-router-dom'

import $ from 'jquery'
import {Container, Form, Tabs, Tab, Row} from 'react-bootstrap'
import { ArticleTable } from '../../component/article/ArticleTable'
import {TOKEN} from "../../api/token";
import Pager from "../../component/pager/Pager";
import ArticleService from "../../api/service/ArticleService";

const ArticleList = () => {
    const navigate = useNavigate();

    const getArticleType = () => {
        let queryString = decodeURI($(window.location).attr('search'))

        return queryString.substring(queryString.indexOf('=') + 1, queryString.indexOf('&'))
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

        let pageStartQueryString = queryString.substr(queryString.indexOf('page'));

        if (pageStartQueryString.indexOf('&') !== -1) {
            return pageStartQueryString.substring(pageStartQueryString.indexOf('=') + 1, pageStartQueryString.indexOf('&'))
        }

        return Number(pageStartQueryString.substr(pageStartQueryString.indexOf('=') + 1))
    }

    const handleWriting = () => {
        if (sessionStorage.getItem(TOKEN)) {
            navigate('/article/create?articleType=' + articleType)
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

        let url = '/article/list?articleType=' + articleType + '&page=1&field=' + searchCondition.field + '&query=' + searchCondition.querys

        if (searchCondition.field === "" || searchCondition.querys === "") {
            url = '/article/list?articleType=' + articleType + '&page=1'
        }

        window.location.href = url
    }

    const onPagerNextButton = async (currentPage, lastPagerNum, pagerLength, searchCondition) => {
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let nextPage = startNum + pagerLength

        let url = '/article/list?articleType=' + articleType + '&page=' + nextPage + '&field=' + searchCondition.field +
            '&query=' + searchCondition.querys

        if (searchCondition.field === "" || searchCondition.querys === "") {
            url = '/article/list?articleType=' + articleType + '&page=' + nextPage
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

        let url = '/article/list?articleType=' + articleType + '&page=' + previousPage + '&field=' + searchCondition.field +
            '&query=' + searchCondition.querys

        if (searchCondition.field === "" || searchCondition.querys === "") {
            url = '/article/list?articleType=' + articleType + 'page=' + previousPage
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

    const loadArticles = async (articleType, searchCondition) => {
        const pageable = 'page=' + (curPage - 1) + '&size=10&sort=id,desc'
        let result = await ArticleService.getAll(articleType, pageable, searchCondition)

        setArticle(result.content)
        setArticleCount(result.totalElements)
    }

    const POST_NUM_ON_SCREEN = 10
    const NOTICE_NUM_ON_SCREEN = 5
    const PAGER_LENGTH = 5

    const [articleType, setArticleType] = useState(getArticleType())
    const [searchCondition, setSearchCondition] = useState(getSearchCondition())

    const [curPage, setCurPage] = useState(getCurrentPage())
    const [articleCount, setArticleCount] = useState(0)
    const [article, setArticle] = useState(null)

    useEffect(() => {
        loadArticles(articleType, searchCondition)
    }, [curPage])

    return (
        <>
            {
                article &&
                <Container>
                    <Container>
                        <Row className='d-flex justify-content-center align-items-center'>
                            <h2 className="page-section-heading text-center text-uppercase text-secondary mb-3" id="channelName">
                                {articleType} 게시판
                            </h2>
                        </Row>
                    </Container>

                    <div className="row">
                        <div className="col-lg-6 col-sm-12 text-lg-start text-center">
                        </div>
                        <div className="col-lg-6 col-sm-12 text-lg-end text-center">
                            <nav className="navbar navbar-expand-lg navbar-light float-end">
                                <div className="container-fluid">
                                    <div>
                                        <select className="form-control form-control-sm" id="field" name="field">
                                            <option value="title">제목</option>
                                            <option value="writer">작성자</option>
                                        </select>
                                    </div>
                                    <Form className="navbar-form navbar-right">
                                        <div className="input-group">
                                            <input type="Search"
                                                   onKeyDown={searchKeyDown}
                                                   onKeyPress={searchKeyDown}
                                                   id="search"
                                                   name="search"
                                                   placeholder="Search..."
                                                   className="form-control"/>
                                            <div className="input-group-btn">
                                                <button onClick={handleSearch} id="searchButton" className="btn btn-info">
                                                    <span className="glyphicon glyphicon-search" />
                                                </button>
                                            </div>
                                        </div>
                                    </Form>
                                </div>
                            </nav>
                        </div>
                    </div>

                    <ArticleTable articles={article} />

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
                        postsCount={articleCount}
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

export default ArticleList;
