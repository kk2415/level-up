import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()

    const pagerLength = 5
    const postNumOnScreen = 10
    const noticeNumOnScreen = 5

    let backUpNotice = $('#notice');

    let currentPage = getCurrentPage()
    let postSearch = getPostSearch()
    let allNoticeCount = getAllNoticeCount(postSearch)
    let lastPagerNum = getLastPagerNum(allNoticeCount)
    let noticeResponse = getNotices(currentPage, postSearch)

    let curNoticePage = 1

    $('#currentPage').text(currentPage)
    $('#lastPage').text(lastPagerNum)

    setEventHandler()

    showNotices(noticeResponse)
    showPager(lastPagerNum, postSearch)

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
        let notice = $('#notice');

        for (let idx = 0; idx < count; idx++) {
            let clonePost = notice.clone()

            if (idx === 0) {
                clonePost = notice
            }

            clonePost.id = idx
            clonePost.children('td').eq(0).text(notices[idx].writer)
            clonePost.children('td').eq(1).children('a').text(notices[idx].title + ' [' + notices[idx].commentCount + ']')
            clonePost.children('td').eq(1).children('a').attr('href', '/notice/' + notices[idx].id)
            clonePost.children('td').eq(2).text(notices[idx].views)
            clonePost.children('td').eq(3).text(notices[idx].voteCount)
            clonePost.children('td').eq(4).text(notices[idx].dateCreated)
            $('#noticeTableBody').append(clonePost)
        }
    }

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

})