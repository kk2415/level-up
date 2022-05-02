import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()
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
        let member = request.getRequest('/api/member');

        return member.email
    }

    function isMyPost() {
        let bool = false

        console.log(memberEmail)

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
        comments = request.getRequest('/api/comment/' + postId)
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

            request.postRequest('/api/comment', comment)
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
        post = request.getRequest('/api/post/' + postId + '?view=true')
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

            let result = request.getRequest('/api/post/' + postId + '/prevPost');

            if ('status' in result && result.status !== 200) {
                alert("이전 페이지가 없습니다.")
            }
            else {
                let prevPostId = result.id
                $(location).attr('href', '/post/detail/' + prevPostId + '?channel=' + channelId)
            }
        })

        $('#nextPostButton').click(function () {
            let result = request.getRequest('/api/post/' + postId + '/nextPost');

            if ('status' in result && result.status !== 200) {
                alert("다음 페이지가 없습니다.")
            }
            else {
                let nextPostId = result.id
                $(location).attr('href', '/post/detail/' + nextPostId + '?channel=' + channelId)
            }
        })

        $('#modifyButton').click(function () {
            $(location).attr('href', '/post/edit/' + postId + '?email=' + memberEmail + '&channel=' + channelId)
        })

        $('#deleteButton').click(function () {
            request.deleteRequest('/api/post/' + postId, () => {
                alert('삭제되었습니다.')
                $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
            })
        })

    }
})