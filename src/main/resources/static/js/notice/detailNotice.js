import httpRequest from "/js/module/httpRequest.js";

let request = new httpRequest()
let backupComment = $('#comment');

let noticeId = getNoticeId()
let noticeResponse = getNotice()
let comments = getComments()
let comment = {}

$(function () {
    hideCreateReplyForm()
    hideReplyList()

    setNoticeEventHandler()
    setCommentEventHandler()

    console.log(noticeResponse)

    showNotice(noticeResponse)
    showComments(comments)
})


/**
 * getter
 * */
function getNoticeId() {
    let pathname = decodeURI($(location).attr('pathname'))
    return pathname.substr(pathname.lastIndexOf('/') + 1)
}

function getNotice() {
    return request.getRequest('/api/notice/' + noticeId + '?view=true')
}

function getComments() {
    return request.getRequest('/api/comment/' + noticeId + '?identity=NOTICE')
}


/**
 * notice function
 * */
function showNotice(notice) {
    $('#writer').text(notice.writer)
    $('#title').text(notice.title)
    $('#dateCreated').text(notice.dateCreated)
    $('#views').text(notice.views)
    $('#commentCount').text(notice.commentCount)
    $('#commentCount2').text(notice.commentCount)
    $('#content').html(notice.content)
}


/**
 * comment function
 * */
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

        if (comment[idx].replyCount > 0) {
            cloneComment.children('#replyButton').text(`답글 ${comment[idx].replyCount}`)
        }

        cloneComment.children('#replyButton').click(function () {
            showReplyList(cloneComment, comment[idx]);
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

function removeComments() {
    $('.comment').remove()
    $('#contentOfWritingComment').val('')
}


/**
 * reply function
 * */
function showReplyList(commentNode, commentResponse) {
    let replyList = commentNode.children('#replyList');

    if (replyList.css('display') === 'block') {
        replyList.css('display', 'none')
        replyList.children().remove()
        commentNode.children('#createReplyForm').remove()
        return
    }

    replyList.css('display', 'block')

    let replyResponse = request.getRequest('/api/comment/' + commentResponse.id + '/reply')
    let replys = replyResponse.data
    let count = replyResponse.count

    for (let i = 0; i < count; i++) {
        showReply(replyList, replys[i]);
    }

    showCreateReplyForm(commentNode, commentResponse, 'NOTICE')
}

function showReply(replyList, reply) {
    let cloneReply = $('#reply').clone();

    cloneReply.id = 'reply' + reply.id
    cloneReply.children('#replyEmailAndDate').children('#replyEmail').text(reply.writer)
    cloneReply.children('#replyEmailAndDate').children('#replyDate').text(reply.dateCreated)
    cloneReply.children('#replyContent').children().text(reply.content)
    cloneReply.children('#replyVote').children('#replyVoteCount').text(reply.voteCount)
    cloneReply.children('#replyVote').children('#replyVoteButton').click(function () {
        let voteRequest = {
            'articleId': reply.id,
            'identity': 'COMMENT',
        }

        request.postRequest('/api/vote', voteRequest, () => {
            cloneReply.children('#replyVote').children('#replyVoteCount').text(reply.voteCount + 1)
        })
    })

    cloneReply.css('display', 'block')
    replyList.append(cloneReply)
}

function showCreateReplyForm(commentNode, commentResponse, identity) {
    let replyFormClone = $('#createReplyForm').clone();

    replyFormClone.css('display', 'block')
    replyFormClone.children().eq(0).children().text(commentResponse.writer)
    replyFormClone.children().eq(2).children().click(function () {
        createReply(commentNode, commentResponse, replyFormClone.children().eq(1).children().val(), identity);
    })
    commentNode.append(replyFormClone)
}

function createReply(commentNode, commentResponse, content, identity) {
    let replyRequest = {}

    replyRequest.parentId = commentResponse.id
    replyRequest.content = content
    replyRequest.articleId = noticeId
    replyRequest.identity = identity

    request.postRequest('/api/comment/reply', replyRequest, function () {
        commentNode.children('#createReplyForm').remove()

        commentNode.children('#replyList').css('display', 'none')
        commentNode.children('#replyList').children().remove()

        commentNode.children('#replyButton').text(`답글 ${commentResponse.replyCount + 1}`)
        showReplyList(commentNode, commentResponse)
    });
}



/**
 * noitce event handler
 * */
function setNoticeEventHandler() {
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


/**
 * comment event handler
 * */
function setCommentEventHandler() {
    $('#commentingButton').click(function () {
        createComment()
        comments = getComments()

        removeComments()
        $('#commentFrame').append(backupComment)
        showComments(comments)
    })
}


/**
 * hide reply from
 * */
function hideReplyList() {
    $('#replyList').css('display', 'none')
    $('#reply').css('display', 'none')
}

function hideCreateReplyForm() {
    $('#createReplyForm').css('display', 'none')
}