$(function () {
    let postId = getPostId()
    let channelId = getChannelId()
    let backupComment = $('#comment');

    let post = {}
    let comment = {}
    let comments = {}

    setEventHandler()

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
            comments = data
        })
        .fail(function (error) {
            console.log(error)
        })
    }

    function showComments() {
        let count = comments.count
        let comment = comments.data

        for (let idx = count - 1; idx >= 0; idx--) {

            let cloneComment = $('#comment').clone();

            cloneComment.id = 'comment' + idx
            cloneComment.css('display', 'block')
            cloneComment.children('#commentWriter').text(comment[idx].writer)
            cloneComment.children('#commentDate').text(comment[idx].dateCreated)
            cloneComment.children('#commentContent').text(comment[idx].content)
            $('#comment').after(cloneComment)
        }

        $('#comment').css('display', 'none')
    }

    function writeComment() {
        comment.memberEmail = getMemberEmail()
        comment.postId = postId
        comment.content = $('#contentOfWritingComment').val()

        console.log(comment)
        if (comment.memberEmail !== undefined) {
            $.ajax({
                url: '/api/comment',
                method: "POST",
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(comment),
                async: false,
            })
            .done(function (data) {
            })
            .fail(function (error) {
                console.log(error)
            })
        }
        else {
            alert("댓글을 작성하려면 로그인을 해야합니다.")
        }
    }

    function getMemberEmail() {
        let memberEmail

        $.ajax({
            url: '/api/member',
            method: 'GET',
            async: false,
        })
        .done(function (data) {
            memberEmail = data.email
        })
        .fail(function (error) {
            console.log(error)
        })

        return memberEmail
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

    function removeComments() {
        $('.comment').remove()
    }

    function setEventHandler() {
        $('#commentingButton').click(function () {
            writeComment()
            setComments()
            removeComments()
            $('#commentFrame').append(backupComment)
            showComments()
        })
    }
})