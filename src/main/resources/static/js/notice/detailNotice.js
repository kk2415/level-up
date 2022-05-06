import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()
    let backupComment = $('#comment');

    let noticeId = getNoticeId()
    let noticeResponse = getNotice()
    let comments = getComments()
    let comment = {}

    console.log(noticeId)
    console.log(noticeResponse)
    console.log(comments)

    setEventHandler()

    showPost(noticeResponse)
    showComments(comments)



    function getComments() {
        return request.getRequest('/api/comment/' + noticeId + '?identity=NOTICE')
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
        comment.memberEmail = ''
        comment.articleId = noticeId
        comment.content = $('#contentOfWritingComment').val()
        comment.identity = 'NOTICE'
        console.log(comment)

        let result = request.postRequest('/api/comment', comment);
        if (result == null) {
            alert("댓글을 작성하려면 로그인을 해야합니다.")
        }
    }

    function getNoticeId() {
        let pathname = decodeURI($(location).attr('pathname'))
        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    function getNotice() {
        return request.getRequest('/api/notice/' + noticeId + '?view=true')
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
           $(location).attr('href', '/notice?page=1')
        })

        $('#prevPostButton').click(function () {

            let result = request.getRequest('/api/notice/' + noticeId + '/prev');

            if (result != null) {
                let prevPostId = result.id
                $(location).attr('href', '/notice/' + prevPostId)
            }
            else {
                alert("이전 페이지가 없습니다.")
            }
        })

        $('#nextPostButton').click(function () {
            let result = request.getRequest('/api/notice/' + noticeId + '/next');

            if (result != null) {
                let nextPostId = result.id
                $(location).attr('href', '/notice/' + nextPostId)
            }
            else {
                alert("다음 페이지가 없습니다.")
            }
        })

        $('#modifyButton').click(function () {
            $(location).attr('href', '/notice/edit/' + noticeId)
        })

        $('#deleteButton').click(function () {
            request.deleteRequest('/api/notice/' + noticeId, () => {
                alert('삭제되었습니다.')
                $(location).attr('href', '/notice?page=1')
            })
        })

    }
})