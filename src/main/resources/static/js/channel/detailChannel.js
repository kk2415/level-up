$(function () {
    let channelId = getChannelId()
    let currentPage = getCurrentPage()
    let channelName

    const pagerLength = 5
    const postNumOnScreen = 10

    let post = $('#post');
    let channelPosts = {}
    let postSearch = {}

    setPostSearch()
    setChannelName()
    $('#channelName').text(channelName)
    $('#currentPage').text(currentPage)

    setEventHandler()
    setChannelPosts(channelId, currentPage, postSearch)
    let postsCount = channelPosts.count

    showPosts()
    console.log(channelPosts)

    setPager()

    function setChannelPosts(channelId, page, postSearch) {
        console.log((postSearch))

        let url = '/api/' + channelId + '/posts/' + page + '?' +
            'field=' + postSearch.field + '&' + 'query=' + postSearch.querys

        if (postSearch.field === "") {
            url = '/api/' + channelId + '/posts/' + page
        }

        console.log('loadPost url : ' + url)

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
        let data = channelPosts.data
        console.log(data)
        let count = channelPosts.count
        let post = $('#post');

        for (let idx = 0; idx < count; idx++) {
            let clonePost = post.clone()

            if (idx === 0) {
                clonePost = post
            }

            clonePost.id = idx
            clonePost.children('td').eq(0).text(data[idx].writer)
            clonePost.children('td').eq(1).children('a').text(data[idx].title + ' [' + data[idx].commentCount + ']')
            clonePost.children('td').eq(1).children('a').attr('href', '/channel/' + channelId + '/post/' + data[idx].id)
            clonePost.children('td').eq(2).text(data[idx].views)
            clonePost.children('td').eq(3).text(data[idx].voteCount)
            clonePost.children('td').eq(4).text(data[idx].dateCreated)
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

    // http://localhost:8080/channel/detail/4?page=1&field=writer&query=%ED%85%8C%EC%8A%A4%ED%8A%B8
    // http://localhost:8080/channel/detail/4?page=2field=writer&query=%ED%85%8C%EC%8A%A4%ED%8A%B8

    function setPager() {
        let page = $('#page');
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let lastNum = Math.floor(postsCount / postNumOnScreen) + 1

        for (let idx = 0; idx < lastNum; idx++) {
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

            // clonePage.children('a').attr('href', url)
            // let url = '/channel/detail/' + channelId + '?page=' + currentPage + '&' +
            //     'field=' + postSearch.field + '&' + 'query=' + postSearch.querys

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
        console.log(queryString)

        if (!queryString.includes("field", 0) || !queryString.includes("query", 0)) {
            postSearch.field = ""
            postSearch.querys = ""
        }

        if (queryString.includes("field", 0)) {
            let queryStringOfPostSearch = queryString.substr(queryString.search("field"));
            let firstIndex = queryStringOfPostSearch.indexOf("=") + 1;
            let endIndex = queryStringOfPostSearch.indexOf("&");

            // console.log(queryStringOfPostSearch)
            // console.log(firstIndex)
            // console.log(endIndex)
            // console.log(queryStringOfPostSearch.substring(firstIndex, endIndex))

            postSearch.field = queryStringOfPostSearch.substring(firstIndex, endIndex)
        }

        if (queryString.includes("query", 0)) {
            let queryStringOfPostSearch = queryString.substr(queryString.search("query"));
            let firstIndex = queryStringOfPostSearch.indexOf("=") + 1

            // console.log(queryStringOfPostSearch)
            // console.log(firstIndex)
            // console.log(queryStringOfPostSearch.substr(firstIndex))

            postSearch.querys = queryStringOfPostSearch.substr(firstIndex)
        }
    }

    function setEventHandler() {
        $('#postingButton').click(function () {
            $(location).attr('href', '/channel/' + channelId + '/posting')
        })

        $('#backButton').click(function () {
            $(location).attr('href', '/')
        })

        $('#next').click(function () {
            let startNum = currentPage - (currentPage - 1) % pagerLength
            let lastNum = Math.floor(channelPosts.count / postNumOnScreen) + 1
            let nextPage = startNum + 5


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
            let previousPage = startNum - 5

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

                console.log("hello")
                postSearch.field = $('#navbarDropdown').val()
                postSearch.querys = $('#search').val()

                let url = '/channel/detail/' + channelId + '?page=' + 1 + '&' +
                    'field=' + postSearch.field + '&' + 'query=' + postSearch.querys

                if (postSearch.field === "" || postSearch.querys === "") {
                    url = '/channel/detail/' + channelId + '?page=' + 1
                }

                console.log(url)
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