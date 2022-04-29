$(function () {
    const pagerLength = 5
    const postNumOnScreen = 10

    let channelId = getChannelId()
    let currentPage = getCurrentPage()
    let channelName
    let post = $('#post');
    let channelPosts = {}
    let postSearch = {}

    setPostSearch()
    let allPostsCount = getPostsCount()

    setChannelName()
    $('#channelName').text(channelName)
    $('#currentPage').text(currentPage)

    setEventHandler()
    setChannelPosts(channelId, currentPage, postSearch)
    let postsCount = channelPosts.count

    showPosts()
    setPager()

    function getPostsCount() {
        let postsCount

        let url = '/api/' + channelId + '/search/posts-size?field=' + postSearch.field + '&' + 'query=' + postSearch.querys

        if (postSearch.field === "") {
            url = '/api/' + channelId + '/search/posts-size'
        }

        $.ajax({
            url: encodeURI(url),
            method: 'GET',
            async: false,
        })
        .done(function (data) {
            postsCount = data
        })
        .fail(function (error) {
            console.log(error)
        })

        return postsCount
    }

    function setChannelPosts(channelId, page, postSearch) {
        let url = '/api/' + channelId + '/posts/' + page + '?' +
            'field=' + postSearch.field + '&' + 'query=' + postSearch.querys

        if (postSearch.field === "") {
            url = '/api/' + channelId + '/posts/' + page
        }

        $.ajax({
            url: encodeURI(url),
            method: 'GET',
            async: false,
        })
        .done(function (data) {
            channelPosts = data
        })
    }

    function showPosts() {
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

    function setChannelName() {
        $.ajax({
            url: '/api/channel/' + channelId,
            method: 'GET',
            async: false,
        })
        .done(function (data) {
            channelName = data.name
        })
    }

    function setPager() {
        let page = $('#page');
        let endPageNum = Math.floor(allPostsCount / postNumOnScreen) + 1

        let startNum = currentPage - (currentPage - 1) % pagerLength
        let loopCount = pagerLength

        if ((endPageNum - currentPage) < pagerLength) {
            loopCount = endPageNum - currentPage
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
                setChannelPosts(channelId, idx + startNum, postSearch)

                removePosts()
                $('#postTableBody').append(post)

                showPosts()
            })

            page.before(clonePage)
        }

        page.css('display', 'none')
    }

    function removePosts() {
        $('tbody').children('tr').remove()
    }

    function getChannelId() {
        let pathname = $(location).attr('pathname')
        return pathname.charAt(pathname.lastIndexOf('/') + 1)
    }

    function getCurrentPage() {
        let queryString = decodeURI($(location).attr('search'))

        if (queryString.indexOf('&') != -1) {
            return queryString.substring(queryString.indexOf('=') + 1, queryString.indexOf('&'))
        }
        return queryString.substr(queryString.indexOf('=') + 1)
    }

    function setPostSearch() {
        let queryString = decodeURI($(location).attr('search'))

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
    }

    function setEventHandler() {
        $('#postingButton').click(function () {
            $(location).attr('href', '/post/create?channel=' + channelId)
        })

        $('#backButton').click(function () {
            $(location).attr('href', '/')
        })

        $('#next').click(function () {
            let startNum = currentPage - (currentPage - 1) % pagerLength
            let lastNum = Math.floor(allPostsCount / postNumOnScreen) + 1
            let nextPage = startNum + pagerLength


            let url = '/channel/detail/' + channelId + '?page=' + nextPage + '&field='
                + postSearch.field + '&' + 'query=' + postSearch.querys

            if (postSearch.field == "" || postSearch.querys == "") {
                url = '/channel/detail/' + channelId + '?page=' + nextPage
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

})