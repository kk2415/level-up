$(function () {
    let postId = getPostId()
    let channelId = getChannelId()
    let memberEmail = getMemberEmail()
    let backupComment = $('#comment');

    let post = {}
    let comment = {}
    let comments = {}

    setEventHandler()

    setPost()
    setComments()

    showPost()
    showModifyAndDeleteButton()

    showComments()





    function getMemberEmail() {
        let email

        $.ajax({
            url: '/api/member',
            method: "GET",
            async: false,
        })
        .done(function (data) {
            email = data.email
        })
        .fail(function (error) {
            email = null
        })

        return email
    }

    function isMyPost() {
        let bool = false

        $.ajax({
            url: '/api/post/' + postId + '/check-member?email=' + memberEmail,
            method: "GET",
            async: false,
        })
        .done(function () {
            bool = true
        })
        .fail(function (error) {
            console.log(error)
        })
        return bool;
    }

    function isLoginMember() {
        if (memberEmail == null) {
            return false
        }
        return true
    }

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

    function getPostId() {
        let pathname = decodeURI($(location).attr('pathname'))
        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    function getChannelId() {
        let search = decodeURI($(location).attr('search'))
        return search.substr(search.indexOf('=') + 1)
    }

    function setPost() {
        $.ajax({
            url: '/api/post/' + postId + '?view=true',
            method: "GET",
            async: false,
        })
        .done(function (data) {
            console.log(data)
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

    function showModifyAndDeleteButton() {
        if (isLoginMember() && isMyPost()) {
            $('#modifyButton').css('display', 'inline-block')
            $('#deleteButton').css('display', 'inline-block')
        }
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

        $('#allPostButton').click(function () {
           $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
        })

        $('#prevPostButton').click(function () {
            $.ajax({
                url: '/api/post/' + postId + '/prevPost',
                method: "GET",
                async: false,
            })
            .done(function (data) {
                let prevPostId = data.id

                $(location).attr('href', '/post/detail/' + prevPostId + '?channel=' + channelId)
            })
            .fail(function (error) {
                console.log(error)
                alert("이전 페이지가 없습니다.")
            })
        })

        $('#nextPostButton').click(function () {
            $.ajax({
                url: '/api/post/' + postId + '/nextPost',
                method: "GET",
                async: false,
            })
            .done(function (data) {
                let nextPostId = data.id

                $(location).attr('href', '/post/detail/' + nextPostId + '?channel=' + channelId)
            })
            .fail(function (error) {
                console.log(error)
                alert("다음 페이지가 없습니다.")
            })
        })

        $('#modifyButton').click(function () {
            $(location).attr('href', '/post/edit/' + postId + '?email=' + memberEmail + '&channel=' + channelId)
        })

    }
})