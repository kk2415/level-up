import httpRequest from "/js/module/httpRequest.js";

const pagerLength = 5
const postNumOnScreen = 10

let request = new httpRequest()
let currentPage = getCurrentPage()
let postSearch = getPostSearch()
let allNoticeCount = getAllNoticeCount(postSearch)
let lastPagerNum = getLastPagerNum(allNoticeCount)
let noticeResponse = getNotices(currentPage, postSearch)

$(function () {
    console.log(currentPage)
    console.log(postSearch)
    console.log(allNoticeCount)
    console.log(lastPagerNum)
    console.log(noticeResponse)

    let backUpNotice = $('#notice');
    $('#currentPage').text(currentPage)
    $('#lastPage').text(lastPagerNum)

    setEventHandler()

    showNotices(noticeResponse)
    showPager(lastPagerNum, postSearch)
})

function getAllNoticeCount(postSearch) {
    let url = '/api/notices/count?field=' + postSearch.field + '&' + 'query=' + postSearch.querys

    if (postSearch.field === "") {
        url = '/api/notices/count'
    }

    let result = request.getRequest(url);

    return result == null ? null : result.data
}

function getNotices(page, postSearch) {
    let url = '/api/notices?page=' + page + '&' +
        'field=' + postSearch.field + '&' + 'query=' + postSearch.querys

    if (postSearch.field === "") {
        url = '/api/notices?page=' + page
    }

    return request.getRequest(url)
}

function showNotices(noticeResponse) {
    let notices = noticeResponse.data
    let count = noticeResponse.count
    let notice = $('#noticePost')

    for (let idx = 0; idx < count; idx++) {
        let cloneNotice = notice.clone()

        if (idx === 0) {
            cloneNotice = notice
        }

        cloneNotice.id = 'notice' + idx
        cloneNotice.children('td').eq(0).text(notices[idx].id)
        cloneNotice.children('td').eq(1).text(notices[idx].writer)
        cloneNotice.children('td').eq(2).children('a').text(notices[idx].title + ' [' + notices[idx].commentCount + ']')
        cloneNotice.children('td').eq(2).children('a').attr('href', '/notice/' + notices[idx].id)
        cloneNotice.children('td').eq(3).text(notices[idx].views)
        cloneNotice.children('td').eq(4).text(notices[idx].dateCreated)
        $('#noticeTableBody').append(cloneNotice)
    }
}


function showPager(lastPagerNum, postSearch) {
    let page = $('#page');
    let startNum = currentPage - (currentPage - 1) % pagerLength

    for (let idx = 0; idx < lastPagerNum; idx++) {
        let clonePage = page.clone()

        clonePage.id = 'page' + (Number(startNum) + 1)
        clonePage.children('a').text(idx + startNum)
        clonePage.children('a').click(function () {
            noticeResponse = getNotices(idx + startNum, postSearch)

            removePosts()
            $('#noticeTableBody').append(backUpNotice)

            showNotices(noticeResponse)
            $('#currentPage').text(idx + startNum)
        })

        page.before(clonePage)
    }

    page.css('display', 'none')
}

function removePosts() {
    $('#noticeTableBody').children('tr').remove()
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

function setEventHandler() {
    $('#postingButton').click(function () {
        let result = request.getRequest('/notice/create');

        if (result != null) {
            $(location).attr('href', '/notice/create')
        }
        else {
            alert('가입된 회원만 글을 작성할 수 있습니다.')
        }
    })

    $('#backButton').click(function () {
        $(location).attr('href', '/')
    })

    $('#next').click(function () {
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let lastNum = Math.floor(allNoticeCount / postNumOnScreen) + 1
        let nextPage = startNum + pagerLength


        let url = '/notice?page=' + nextPage + '&field='
            + postSearch.field + '&' + 'query=' + postSearch.querys

        if (postSearch.field == "" || postSearch.querys == "") {
            url = '/notice?page=' + nextPage
        }

        if (nextPage < lastNum) {
            $('#next').attr('href', url)
        }
        else {
            alert("다음 페이지가 없습니다")
        }
    })

    $('#previous').click(function () {
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let previousPage = startNum - pagerLength

        let url = '/notice?page=' + previousPage + '&field='
            + postSearch.field + '&' + 'query=' + postSearch.querys

        if (postSearch.field == "" || postSearch.querys == "") {
            url = '/notice?page=' + previousPage
        }


        if (previousPage > 0) {
            $('#previous').attr('href', url)
        }
        else {
            alert("이전 페이지가 없습니다")
        }

    })

    $('#search').keydown(function (event) {

        if (event.keyCode == 13) {
            event.preventDefault()

            postSearch.field = $('#navbarDropdown').val()
            postSearch.querys = $('#search').val()

            let url = '/notice?page=' + 1 + '&field='
                + postSearch.field + '&' + 'query=' + postSearch.querys

            if (postSearch.field == "" || postSearch.querys == "") {
                url = '/notice?page=' + 1
            }

            $(location).attr('href', url)
        }
    })

    $('#searchButton').click(function () {
        postSearch.field = $('#navbarDropdown').val()
        postSearch.querys = $('#search').val()

        let url = '/notice?page=' + 1 + '&field='
            + postSearch.field + '&' + 'query=' + postSearch.querys

        if (postSearch.field == "" || postSearch.querys == "") {
            url = '/notice?page=' + 1
        }

        $(location).attr('href', url)
    })
}

function getLastPagerNum(allNoticeCount) {
    return Math.floor(allNoticeCount / postNumOnScreen) + 1;
}