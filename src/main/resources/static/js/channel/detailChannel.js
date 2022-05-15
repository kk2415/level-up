import httpRequest from "/js/module/httpRequest.js";
import ChannelNotice from "/js/module/channelNotice.js";

let request = new httpRequest()
let channelNotice = new ChannelNotice()

const pagerLength = 5
const postNumOnScreen = 10
const noticeNumOnScreen = 5

let backUpPost = $('#post');
let backUpChannelNotice = $('#channelNotice');

let channelId = getChannelId()
let channelName = getChannelName(channelId)
let currentPage = getCurrentPage()
let postSearch = getPostSearch()
let allPostsCount = getPostsCount(postSearch)

let lastPagerNum = getLastPagerNum()
let channelPosts = getChannelPosts(channelId, currentPage, postSearch)

let allNoticeCount = 0
let curNoticePage = 1

$(function () {
    console.log(channelPosts)
    console.log(allPostsCount)
    console.log(lastPagerNum)

    $('#channelName').text(channelName)
    $('#currentPage').text(currentPage)
    $('#lastPage').text(lastPagerNum)

    setManagerEventHandler()
    setPostEventHandler()
    setPagerEventHandler()
    setNoticeEventHandler()

    showChannelNotice(1)
    showPosts(channelPosts)
    showPager(lastPagerNum, postSearch)
})


/**
 * getter
 * */
function getPostsCount(postSearch) {
    let postsCount = 0
    let url = '/api/' + channelId + '/search/count?field=' + postSearch.field + '&' + 'query=' + postSearch.querys

    if (postSearch.field === "") {
        url = '/api/' + channelId + '/search/count'
    }

    return request.getRequest(url)
}

function getChannelPosts(channelId, page, postSearch) {
    let url = '/api/' + channelId + '/posts/' + page + '?' +
        'field=' + postSearch.field + '&' + 'query=' + postSearch.querys

    if (postSearch === undefined || postSearch.field === "") {
        url = '/api/' + channelId + '/posts/' + page
    }

    return request.getRequest(url)
}

function getChannelName(channelId) {
    let result = request.getRequest('/api/channel/' + channelId);

    return result.name
}

function getChannelId() {
    let pathname = $(location).attr('pathname')
    return pathname.substr(pathname.lastIndexOf('/') + 1)
}

function getCurrentPage() {
    let queryString = decodeURI($(location).attr('search'))

    if (queryString.indexOf('&') != -1) {
        return queryString.substring(queryString.indexOf('=') + 1, queryString.indexOf('&'))
    }
    return queryString.substr(queryString.indexOf('=') + 1)
}

function getPostSearch() {
    let queryString = decodeURI($(location).attr('search'))
    let postSearch = {}

    if (!queryString.includes("field", 0) || !queryString.includes("query", 0)) {
        postSearch.field = ""
        postSearch.querys = ""
    }

    if (queryString.includes("field", 0)) {
        let queryStringOfPostSearch = queryString.substr(queryString.search("field"));
        let firstIndex = queryStringOfPostSearch.indexOf("=") + 1;
        let endIndex = queryStringOfPostSearch.indexOf("&");

        postSearch.field = queryStringOfPostSearch.substring(firstIndex, endIndex)
    }

    if (queryString.includes("query", 0)) {
        let queryStringOfPostSearch = queryString.substr(queryString.search("query"));
        let firstIndex = queryStringOfPostSearch.indexOf("=") + 1
        postSearch.querys = queryStringOfPostSearch.substr(firstIndex)
    }

    return postSearch
}

function getLastPagerNum() {
    return Math.floor(allPostsCount / postNumOnScreen) + 1;
}



/**
 * post
 * */
function showPosts(channelPosts) {
    let posts = channelPosts.data
    let count = channelPosts.count
    let post = $('#post');

    for (let idx = 0; idx < count; idx++) {
        let clonePost = post.clone()

        if (idx === 0) {
            clonePost = post
        }

        clonePost.id = idx
        clonePost.children('td').eq(0).text(posts[idx].writer)
        clonePost.children('td').eq(1).children('a').text(posts[idx].title + ' [' + posts[idx].commentCount + ']')
        clonePost.children('td').eq(1).children('a').attr('href', '/post/detail/' + posts[idx].id + '?channel=' + channelId)
        clonePost.children('td').eq(2).text(posts[idx].views)
        clonePost.children('td').eq(3).text(posts[idx].voteCount)
        clonePost.children('td').eq(4).text(posts[idx].dateCreated)
        $('#postTableBody').append(clonePost)
    }
}

function removePosts() {
    $('#postTableBody').children('tr').remove()
}



/**
 * channel notice
 * */
function showChannelNotice(page) {
    let result = channelNotice.loadChannelNoticeList(channelId, page);
    let channelNoticeList = result.data
    let count = result.count

    if (count > 0) {
        allNoticeCount = channelNoticeList[0].allNoticeCount
    }

    let noticeTableRow = $('#channelNotice');
    for (let idx = 0; idx < count; idx++) {
        let cloneTableRow = noticeTableRow.clone()

        if (idx === 0) {
            cloneTableRow = noticeTableRow
        }

        cloneTableRow.id = idx
        cloneTableRow.children('td').eq(0).text(channelNoticeList[idx].id)
        cloneTableRow.children('td').eq(1).children('a').text(channelNoticeList[idx].title + ' [' + channelNoticeList[idx].commentCount + ']')
        cloneTableRow.children('td').eq(1).children('a').attr('href', '/channel-notice/detail/' + channelNoticeList[idx].id + '?channel=' + channelId)
        cloneTableRow.children('td').eq(2).text(channelNoticeList[idx].writer)
        cloneTableRow.children('td').eq(3).text(channelNoticeList[idx].views)
        cloneTableRow.children('td').eq(4).text(channelNoticeList[idx].dateCreated)
        $('#channelNoticeTable').append(cloneTableRow)
    }
}

function removeChannelNotice() {
    $('#channelNoticeTableBody').children('tr').remove()
}



/**
 * pager
 * */
function showPager(lastPagerNum, postSearch) {
    let page = $('#page');
    let startNum = currentPage - (currentPage - 1) % pagerLength
    let loopCount = pagerLength

    if (lastPagerNum - startNum < pagerLength) {
        loopCount = lastPagerNum - startNum + 1
    }

    for (let idx = 0; idx < loopCount; idx++) {
        let clonePage = page.clone()
        let url = '/channel/detail/' + channelId + '?page=' + (idx + startNum) + '&field='
            + postSearch.field + '&' + 'query=' + postSearch.querys

        if (postSearch.field == "" || postSearch.querys == "") {
            url = '/channel/detail/' + channelId + '?page=' + (idx + startNum)
        }

        clonePage.id = 'page' + (Number(startNum) + 1)
        clonePage.children('a').text(idx + startNum)
        clonePage.children('a').click(function () {
            channelPosts = getChannelPosts(channelId, idx + startNum, postSearch)

            removePosts()
            $('#postTableBody').append(backUpPost)

            showPosts(channelPosts)
            $('#currentPage').text(idx + startNum)
        })

        page.before(clonePage)
    }

    page.css('display', 'none')
}



/**
 * post event handler
 * */
function setPostEventHandler() {
    $('#postingButton').click(function () {
        let result = request.getRequest('/post/create?channel=' + channelId);

        if (result != null) {
            $(location).attr('href', '/post/create?channel=' + channelId)
        }
        else {
            alert('가입된 회원만 글을 작성할 수 있습니다.')
        }
    })

    $('#backButton').click(function () {
        $(location).attr('href', '/')
    })

    $('#search').keydown(function (event) {

        if (event.keyCode == 13) {
            event.preventDefault()

            postSearch.field = $('#navbarDropdown').val()
            postSearch.querys = $('#search').val()

            let url = '/channel/detail/' + channelId + '?page=' + 1 + '&' +
                'field=' + postSearch.field + '&' + 'query=' + postSearch.querys

            if (postSearch.field === "" || postSearch.querys === "") {
                url = '/channel/detail/' + channelId + '?page=' + 1
            }

            $(location).attr('href', url)
        }
    })

    $('#searchButton').click(function () {
        postSearch.field = $('#navbarDropdown').val()
        postSearch.querys = $('#search').val()

        let url = '/channel/detail/' + channelId + '?page=' + 1 + '&' +
            'field=' + postSearch.field + '&' + 'query=' + postSearch.querys

        if (postSearch.field === "" || postSearch.querys === "") {
            url = '/channel/detail/' + channelId + '?page=' + 1
        }

        $(location).attr('href', url)
    })
}



/**
 * notice event handler
 * */
function setNoticeEventHandler() {
    $('#createNotice').click(function () {
        $(location).attr('href', '/channel-notice/create?channel=' + channelId)
    })

    $('#nextNoticeList').click(function () {
        if (curNoticePage - 1 <= 0) {
            alert("이전 페이지가 없습니다.")
        }
        else {
            removeChannelNotice()
            $('#channelNoticeTable').append(backUpChannelNotice)

            curNoticePage -= 1
            console.log(curNoticePage)
            showChannelNotice(curNoticePage)
        }
    })

    $('#prevNoticeList').click(function () {
        let lastNum = Math.floor(allNoticeCount / noticeNumOnScreen) + 1
        console.log(allNoticeCount)

        if (curNoticePage + 1 > lastNum) {
            alert("다음 페이지가 없습니다.")
        }
        else {
            removeChannelNotice()
            $('#channelNoticeTable').append(backUpChannelNotice)

            curNoticePage += 1
            console.log(curNoticePage)
            showChannelNotice(curNoticePage)
        }
    })
}



/**
 * pager event handler
 * */
function setPagerEventHandler() {
    $('#next').click(function () {
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let lastNum = Math.floor(allPostsCount / postNumOnScreen) + 1
        let nextPage = startNum + pagerLength


        let url = '/channel/detail/' + channelId + '?page=' + nextPage + '&field='
            + postSearch.field + '&' + 'query=' + postSearch.querys

        if (postSearch.field == "" || postSearch.querys == "") {
            url = '/channel/detail/' + channelId + '?page=' + nextPage
        }

        if (nextPage <= lastNum) {
            $('#next').attr('href', url)
        }
        else {
            alert("다음 페이지가 없습니다")
        }
    })

    $('#previous').click(function () {
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let previousPage = startNum - pagerLength

        let url = '/channel/detail/' + channelId + '?page=' + previousPage + '&field='
            + postSearch.field + '&' + 'query=' + postSearch.querys

        if (postSearch.field == "" || postSearch.querys == "") {
            url = '/channel/detail/' + channelId + '?page=' + previousPage
        }

        if (previousPage > 0) {
            $('#previous').attr('href', url)
        }
        else {
            alert("이전 페이지가 없습니다")
        }

    })
}



/**
 * Manager Event Handler
 * */
function setManagerEventHandler() {
    $('#manager').click(function () {
        $(location).attr('href', '/channel/' + channelId + '/manager')
    })
}