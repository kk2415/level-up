import httpRequest from "/js/module/httpRequest.js";

let request = new httpRequest()
let backupComment = $('#comment');
let backupReply = $('#reply');

let postId = getPostId()
let channelId = getChannelId()
let memberEmail = getMemberEmail()

let post = getPost()
let comments = getComments()
let comment = {}

$(function () {
    hideCreateReplyForm()
    hideReplyList()

    showModifyAndDeleteButton()

    setVoteEventHandler()
    setCommentEventHandler()
    setPostEventHandler()

    showPost(post)
    showComments(comments)
})

/**
 * getter
 * */
function getMemberEmail() {
    let member = request.getRequest('/api/member');

    return member == null ? null : member.email
}

function getComments() {
    return request.getRequest('/api/comment/' + postId + '?identity=POST')
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


/**
 * post function
 * */
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

    showCreateReplyForm(commentNode, commentResponse, 'POST')
}

function showReply(replyList, reply) {
    let cloneReply = $('#reply').clone();

    cloneReply.id = 'reply' + reply.id
    cloneReply.children('#replyEmailAndDate').children('#replyEmail').text(reply.writer)
    cloneReply.children('#replyEmailAndDate').children('#replyDate').text(reply.dateCreated)
    cloneReply.children('#replyContent').children().text(reply.content)
    cloneReply.children('#replyVote').children('.btn').click(function () {
        let voteRequest = {
            'articleId': reply.id,
            'identity': 'COMMENT',
        }

        request.postRequest('/api/vote', voteRequest, function () {
            $('#replyVoteCount').text(reply.voteCount + 1)
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
    replyRequest.articleId = postId
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
 *
 * */
function showModifyAndDeleteButton() {
    if (isLoginMember() && isMyPost()) {
        $('#modifyButton').css('display', 'inline-block')
        $('#deleteButton').css('display', 'inline-block')
    }
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


/**
 * event Handler
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

function setVoteEventHandler() {
    $('#vote').click(function () {
        let voteRequest = {
            'articleId': postId,
            'identity': 'POST',
        }

        request.postRequest('/api/vote', voteRequest, function () {
            $('#voteCount2').text(post.voteCount + 1)
        })
    })
}

function setPostEventHandler() {
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