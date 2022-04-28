$(function () {
    let postId = getPostId()
    let channelId = getChannelId()
    let memberEmail = getMemberEmail()
    let post = {}
    let requestPost = {}
    console.log(channelId)

    setPost()
    setPostContents()

    setEventHandler()
    hideAlertMessageBox()


    function setEventHandler() {
        $('#updateButton').click(function () {
            requestPost.memberEmail = memberEmail
            requestPost.writer = post.writer
            requestPost.title = $('#title').val()
            requestPost.content = $('#content').val()
            requestPost.category = $('#category').val();

            updatePost(requestPost)


        })

        $('#cancelButton').click(function () {
            $(location).attr('href', '/channel/detail/' + channelId)
        })
    }

    function hideAlertMessageBox() {
        $('#alert').css('display', 'none')
    }

    function setPost() {
        $.ajax({
            url: '/api/post/' + postId,
            method: "GET",
            async: false,
        })
        .done(function (data) {
            post.title = data.title
            post.writer = data.writer
            post.content = data.content
            post.category = data.category
        })
        .fail(function (error) {
            console.log(error)
        })
    }

    function setPostContents() {
        $('#title').val(post.title)
        $('#content').val(post.content)
        $("#category").val(post.category).attr("selected", "selected");
    }

    function getPostId() {
        let pathname = decodeURI($(location).attr('pathname'))

        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    function getChannelId() {
        let queryString = decodeURI($(location).attr('search'))

        return queryString.substr(queryString.lastIndexOf('=') + 1)
    }

    function getMemberEmail() {
        let queryString = decodeURI($(location).attr('search'))

        return queryString.substring(queryString.indexOf('=') + 1, queryString.indexOf('&'))
    }

    function updatePost(request) {
        $.ajax({
            url: '/api/post/' + postId,
            method: 'PATCH',
            data: JSON.stringify(request),
            contentType: 'application/json',
            dataType: 'json',
            async: false,
        })
        .done(function () {
            console.log(channelId)
            $(location).attr('href', '/post/detail/' + postId + '?channel=' + channelId)
        })
        .fail(function (error) {
            console.log(error)
        })
    }
})