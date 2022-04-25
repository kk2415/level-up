$(function () {
    let postId = getPostId()
    let channelId = getChannelId()
    let post = {}
    let comments = {}

    setPost()
    setComments()

    showPost()
    showComments()

    function setComments() {
        $.ajax({
            url: '/api/comment/' + postId,
            method: "GET",
            async: false,
        })
        .done(function (data) {
            comments = data.data
        })
        .fail(function (error) {
            console.log(error)
        })
    }

    function showComments() {

    }

    function writeComment() {

    }

    function getPostId() {
        let pathname = decodeURI($(location).attr('pathname'))
        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    function getChannelId() {
        let pathname = decodeURI($(location).attr('pathname'))
        let firstIdx = pathname.indexOf('/', 2) + 1
        let lastIdx = pathname.indexOf('/', firstIdx)

        return pathname.substring(firstIdx, lastIdx)
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

    function showPost() {
        $('#writer').text(post.writer)
        $('#title').text(post.title)
        $('#dateCreated').text(post.dateCreated)
        $('#views').text(post.views)
        $('#voteCount').text(post.voteCount)
        $('#commentCount').text(post.commentCount)
        $('#content').text(post.content)
    }
})