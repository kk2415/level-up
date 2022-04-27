$(function () {
    let postId = getPostId()
    let memberEmail = getMemberEmail()
    let post = {}

    setPost()
    setPostContents()

    function setPostContents() {
        $('#title').val(post.title)
        $('#content').val(post.content)
        console.log(post)
    }

    function setPost() {
        $.ajax({
            url: '/api/post/' + postId,
            method: "GET",
            async: false,
        })
        .done(function (data) {
            post = data
        })
        .fail(function (error) {
            console.log(error)
        })
    }

    function getPostId() {
        let pathname = decodeURI($(location).attr('pathname'))

        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    function getMemberEmail() {
        let queryString = decodeURI($(location).attr('search'))

        return queryString.substr(queryString.indexOf('=') + 1)
    }
})