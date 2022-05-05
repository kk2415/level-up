import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()

    let channelNoticeId = getChannelNoticeId()
    let channelId = getChannelId()

    let channelNoticeResponse = getChannelNoticeResponse()
    let comments = getComments()

    let backupComment = $('#comment');
    let comment = {}

    setEventHandler()

    showChannelNotice(channelNoticeResponse)
    showComments(comments)





    function getMemberEmail() {
        let member = request.getRequest('/api/member');

        return member == null ? null : member.email
    }

    function getComments() {
        return request.getRequest('/api/comment/' + channelNoticeId + '?identity=CHANNEL_NOTICE')
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
            $('#comment').after(cloneComment)
        }

        $('#comment').css('display', 'none')
    }

    function createComment() {
        comment.memberEmail = getMemberEmail()
        comment.articleId = channelNoticeId
        comment.content = $('#contentOfWritingComment').val()
        comment.identity = 'CHANNEL_NOTICE'

        console.log(comment)
        if (comment.memberEmail !== null) {

            request.postRequest('/api/comment', comment)
        }
        else {
            alert("댓글을 작성하려면 로그인을 해야합니다.")
        }
    }

    function getChannelNoticeId() {
        let pathname = decodeURI($(location).attr('pathname'))
        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    function getChannelId() {
        let search = decodeURI($(location).attr('search'))
        return search.substr(search.indexOf('=') + 1)
    }

    function getChannelNoticeResponse() {
        return request.getRequest('/api/channel-notice/' + channelNoticeId + '?view=true')
    }

    function showChannelNotice(channelNoticeResponse) {
        $('#writer').text(channelNoticeResponse.writer)
        $('#title').text(channelNoticeResponse.title)
        $('#dateCreated').text(channelNoticeResponse.dateCreated)
        $('#views').text(channelNoticeResponse.views)
        $('#voteCount').text(channelNoticeResponse.voteCount)
        $('#commentCount').text(channelNoticeResponse.commentCount)
        $('#allCommentCount').text(channelNoticeResponse.commentCount)
        $('#content').html(channelNoticeResponse.content)
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

        $('#nextPostButton').click(function () {
            let result = request.getRequest('/api/channel-notice/' + channelNoticeId + '/nextPost');

            if (result != null) {
                let nextPostId = result.id
                $(location).attr('href', '/channel-notice/detail/' + nextPostId + '?channel=' + channelId)
            }
            else {
                alert("다음 페이지가 없습니다.")
            }
        })

        $('#prevPostButton').click(function () {

            let result = request.getRequest('/api/channel-notice/' + channelNoticeId + '/prevPost');

            if (result != null) {
                let prevPostId = result.id
                $(location).attr('href', '/channel-notice/detail/' + prevPostId + '?channel=' + channelId)
            }
            else {
                alert("이전 페이지가 없습니다.")
            }
        })

        $('#modifyButton').click(function () {
            $(location).attr('href', '/channel-notice/edit/' + channelNoticeId + '?channel=' + channelId)
        })

        $('#deleteButton').click(function () {
            let requestBody = {
                ids : [],
            }
            requestBody.ids.push(channelNoticeId)

            request.deleteRequest('/api/channel-notice?channel=' + channelId, () => {
                alert('삭제되었습니다.')
                $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
            }, requestBody)
        })

    }
})