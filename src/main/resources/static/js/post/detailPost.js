import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()
    let backupComment = $('#comment');

    let postId = getPostId()
    let channelId = getChannelId()
    let memberEmail = getMemberEmail()

    let post = getPost()
    let comments = getComments()
    let comment = {}

    setEventHandler()
    showModifyAndDeleteButton()

    showPost(post)
    showComments(comments)


    function getMemberEmail() {
        let member = request.getRequest('/api/member');

        return member == null ? null : member.email
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

    function getComments() {
        return request.getRequest('/api/comment/' + postId + '?identity=POST')
    }

    function showComments(comments) {
        let count = comments.count
        let comment = comments.data

        for (let idx = count - 1; idx >= 0; idx--) {

            let cloneComment = $('#comment').clone();

            cloneComment.id = 'comment' + idx
            cloneComment.css('display', 'block')
            cloneComment.children('#commentWriter').text(comment[idx].writer)
            cloneComment.children('#commentDate').text(comment[idx].dateCreated)
            cloneComment.children('#commentContent').text(comment[idx].content)
            cloneComment.children('#commentVote').children('#commentVoteCount').text(comment[idx].voteCount)

            cloneComment.children('#commentVote').children('button').click(function () {
                let voteRequest = {
                    'articleId' : comment[idx].id,
                    'identity' : 'COMMENT',
                }

                request.postRequest('/api/vote', voteRequest, function () {
                    cloneComment.children('#commentVote').children('#commentVoteCount').text(comment[idx].voteCount + 1)
                })
            })

            $('#comment').after(cloneComment)
        }

        $('#comment').css('display', 'none')
    }

    function createComment() {
        comment.memberEmail = getMemberEmail()
        comment.articleId = postId
        comment.content = $('#contentOfWritingComment').val()
        comment.identity = 'POST'

        console.log(comment)
        if (comment.memberEmail !== null) {
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

    function getPost() {
        return request.getRequest('/api/post/' + postId + '?view=true')
    }

    function showPost(post) {
        $('#writer').text(post.writer)
        $('#title').text(post.title)
        $('#dateCreated').text(post.dateCreated)
        $('#views').text(post.views)
        $('#voteCount').text(post.voteCount)
        $('#voteCount2').text(post.voteCount)
        $('#commentCount').text(post.commentCount)
        $('#commentCount2').text(post.commentCount)
        $('#content').html(post.content)
    }

    function showModifyAndDeleteButton() {
        if (isLoginMember() && isMyPost()) {
            $('#modifyButton').css('display', 'inline-block')
            $('#deleteButton').css('display', 'inline-block')
        }
    }

    function removeComments() {
        $('.comment').remove()
        $('#contentOfWritingComment').val('')
    }

    function setEventHandler() {
        $('#commentingButton').click(function () {
            createComment()
            comments = getComments()

            removeComments()
            $('#commentFrame').append(backupComment)
            showComments(comments)
        })

        $('#allPostButton').click(function () {
           $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
        })

        $('#prevPostButton').click(function () {

            let result = request.getRequest('/api/post/' + postId + '/prevPost');

            if (result != null) {
                let prevPostId = result.id
                $(location).attr('href', '/post/detail/' + prevPostId + '?channel=' + channelId)
            }
            else {
                alert("이전 페이지가 없습니다.")
            }
        })

        $('#nextPostButton').click(function () {
            let result = request.getRequest('/api/post/' + postId + '/nextPost');

            if (result != null) {
                let nextPostId = result.id
                $(location).attr('href', '/post/detail/' + nextPostId + '?channel=' + channelId)
            }
            else {
                alert("다음 페이지가 없습니다.")
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

        $('#vote').click(function () {
            let voteRequest = {
                'articleId' : postId,
                'identity' : 'POST',
            }

            request.postRequest('/api/vote', voteRequest, function () {
                $('#voteCount2').text(post.voteCount + 1)
            })
        })

    }
})