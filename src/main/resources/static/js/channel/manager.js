import HttpRequest from "/js/module/httpRequest.js";

const pagerLength = 5

let request = new HttpRequest()
let channelId = getChannelId()
let response = request.getRequest('/api/channel/' + channelId + '/manager');
let lastPostPagerNum = getLastPagerNum(response.channelInfo.postCount, 5)
let lastMemberPagerNum = getLastPagerNum(response.channelInfo.memberCount, 5)
let lastWaitingMemberPagerNum = getLastPagerNum(response.channelInfo.waitingMemberCount, 5)

$(function () {
    console.log(lastMemberPagerNum)

    setEventHandler()
    showChannelInfo(response.channelInfo)

    showWaitingMembers(response.waitingMembers)
    showWaitingMembersPager(1, lastWaitingMemberPagerNum)

    showMembers(response.members)
    showMembersPager(1, lastMemberPagerNum)

    showPosts(response.posts)
    showPostsPager(1, lastPostPagerNum)
})



/**
 * getter
 * */
function getChannelId() {
    let pathname = decodeURI($(location).attr('pathname'))

    return pathname.substring(pathname.indexOf('/', 2) + 1, pathname.lastIndexOf('/'))
}

function getChannelPosts(channelId, page, postSearch, postCount) {
    let url = '/api/' + channelId + '/posts/' + page + '?postCount=' + postCount +
        '&field=' + postSearch.field + '&' + 'query=' + postSearch.querys

    if (postSearch.field === undefined || postSearch.field === "") {
        url = '/api/' + channelId + '/posts/' + page + '?postCount=' + postCount
    }

    return request.getRequest(url)
}

function getMembers(channelId, page, count) {
    let url = '/api/channel/' + channelId + '/members/?page=' + page + '&count=' + count

    return request.getRequest(url)
}

function getWaitingMembers(channelId, page, count) {
    let url = '/api/channel/' + channelId + '/waiting-members/?page=' + page + '&count=' + count

    return request.getRequest(url)
}

function getLastPagerNum(allCount, numOnScreen) {
    return Math.floor(allCount / numOnScreen) + 1;
}



/**
 * channelInfo
 * */
function showChannelInfo(channelInfo) {
    $('#channelName').val(channelInfo.channelName)
    $('#manager').val(channelInfo.manager)
    $('#date').val(channelInfo.date)
    $('#memberCount').val(channelInfo.memberCount)
    $('#postCount').val(channelInfo.postCount)
    $('#thumbnail').attr('src', channelInfo.thumbnail)
}



/**
 * WaitingMembers
 * */
function showWaitingMembers(waitingMembers) {
    for(let i = 0; i < waitingMembers.length && i < pagerLength; i++) {
        let waitingMemberClone = $('#waitingMember').clone();

        waitingMemberClone.id = 'waitingMember' + i
        waitingMemberClone.children().eq(0).children('a').text(waitingMembers[i].email)
        waitingMemberClone.children().eq(0).children('a').attr('href', '')
        waitingMemberClone.children().eq(1).click(function (e) {
            e.preventDefault()

            request.deleteRequest('/api/channel/' + channelId + '/waiting-member/' + waitingMembers[i].email,
                () => {
                alert('채널 가입을 거절하였습니다')
                    $(location).attr('href', '/channel/' + channelId + '/manager')
                })
        })
        waitingMemberClone.children().eq(2).click(function (e) {
            e.preventDefault()

            request.postRequest('/api/channel/' + channelId + '/member/' + waitingMembers[i].email, {},
                () => {
                alert('채널 가입을 승인하였습니다.')
                $(location).attr('href', '/channel/' + channelId + '/manager')
            })
        })

        waitingMemberClone.css('display', 'block')

        $('#waitingMemberList').append(waitingMemberClone)
    }
}

function removeWaitingMembers() {
    $('#waitingMemberList').children('li').remove()
}



/**
 * waiting member pager
 * */
function showWaitingMembersPager(currentPage, lastPagerNum) {
    let pagerPrevButton = $('#prevPagerButton').clone();
    let nextPagerButton = $('#nextPagerButton').clone();
    let startNum = currentPage - (currentPage - 1) % pagerLength
    let loopCount = pagerLength

    if (lastPagerNum - startNum < pagerLength) {
        loopCount = lastPagerNum - startNum + 1
    }

    setWaitingMemberPrevPagerButton(pagerPrevButton, currentPage, lastPagerNum)
    $('#waitingMemberPagingFrame').append(pagerPrevButton)

    for (let i = 0; i < loopCount; i++) {
        let pager = $('#pagerNum').clone();

        pager.id = 'waitMemberPager' + i + startNum
        pager.children('a').text(i + startNum)
        pager.children('a').click(function (e) {
            e.preventDefault()

            let waitingMembers = getWaitingMembers(channelId, i + startNum, 5)
            console.log(waitingMembers)

            removeWaitingMembers()
            showWaitingMembers(waitingMembers.data)
        })

        pager.css('display', 'inline-block')
        $('#waitingMemberPagingFrame').append(pager)
    }

    setWaitingMemberNextPagerButton(nextPagerButton, currentPage, lastPagerNum)
    $('#waitingMemberPagingFrame').append(nextPagerButton)
}

function removeWaitingMembersPager() {
    $('#waitingMemberPagingFrame').children().remove()
}

function setWaitingMemberPrevPagerButton(nextPrevButtonNode, currentPage, lastPagerNum) {
    nextPrevButtonNode.css('display', 'block')
    nextPrevButtonNode.click(function (e) {
        e.preventDefault()

        let startNum = currentPage - (currentPage - 1) % pagerLength
        let previousPage = startNum - pagerLength

        if (previousPage > 0) {
            removeWaitingMembersPager()
            showWaitingMembersPager(previousPage, lastPagerNum)
        }
        else {
            alert("이전 페이지가 없습니다")
        }
    })
}

function setWaitingMemberNextPagerButton(nextPagerButtonNode, currentPage, lastPagerNum) {
    nextPagerButtonNode.css('display', 'block')
    nextPagerButtonNode.click(function (e) {
        e.preventDefault()

        let startNum = currentPage - (currentPage - 1) % pagerLength
        let nextPage = startNum + pagerLength

        if (nextPage < lastPagerNum) {
            removeWaitingMembersPager()
            showWaitingMembersPager(nextPage, lastPagerNum)
        }
        else {
            alert("다음 페이지가 없습니다")
        }
    })
}



/**
 * members
 * */
function showMembers(members) {
    for(let i = 0; i < members.length && i < pagerLength; i++) {
        let memberClone = $('#member').clone();

        memberClone.id = 'member' + i
        memberClone.children().eq(0).children('a').text(members[i].email)
        memberClone.children().eq(0).children('a').attr('href', '')
        memberClone.children().eq(1).click(function (e) {
            e.preventDefault()

            request.deleteRequest('/api/channel/' + channelId + '/member/' + members[i].email,
                () => {
                    alert('회원이 퇴장되었습니다.')
                    $(location).attr('href', '/channel/' + channelId + '/manager')
                })
        })

        memberClone.css('display', 'block')

        $('#memberList').append(memberClone)
    }
}

function removeMembers() {
    $('#memberList').children('li').remove()
}



/**
 * member pager
 * */
function showMembersPager(currentPage, lastPagerNum) {
    let pagerPrevButton = $('#prevPagerButton').clone();
    let nextPagerButton = $('#nextPagerButton').clone();
    let startNum = currentPage - (currentPage - 1) % pagerLength
    let loopCount = pagerLength

    if (lastPagerNum - startNum < pagerLength) {
        loopCount = lastPagerNum - startNum + 1
    }

    setMemberPrevPagerButton(pagerPrevButton, currentPage, lastPagerNum)
    $('#memberPagingFrame').append(pagerPrevButton)

    for (let i = 0; i < loopCount; i++) {
        let pager = $('#pagerNum').clone();

        pager.id = 'memberPager' + i + startNum
        pager.children('a').text(i + startNum)
        pager.children('a').click(function (e) {
            e.preventDefault()

            let members = getMembers(channelId, i + startNum, 5)

            removeMembers()
            showMembers(members.data)
        })

        pager.css('display', 'inline-block')
        $('#memberPagingFrame').append(pager)
    }

    setMemberNextPagerButton(nextPagerButton, currentPage, lastPagerNum)
    $('#memberPagingFrame').append(nextPagerButton)
}

function removeMembersPager() {
    $('#memberPagingFrame').children().remove()
}

function setMemberPrevPagerButton(nextPrevButtonNode, currentPage, lastPagerNum) {
    nextPrevButtonNode.css('display', 'block')
    nextPrevButtonNode.click(function (e) {
        e.preventDefault()

        let startNum = currentPage - (currentPage - 1) % pagerLength
        let previousPage = startNum - pagerLength

        if (previousPage > 0) {
            removeMembersPager()
            showMembersPager(previousPage, lastPagerNum)
        }
        else {
            alert("이전 페이지가 없습니다")
        }
    })
}

function setMemberNextPagerButton(nextPagerButtonNode, currentPage, lastPagerNum) {
    nextPagerButtonNode.css('display', 'block')
    nextPagerButtonNode.click(function (e) {
        e.preventDefault()

        let startNum = currentPage - (currentPage - 1) % pagerLength
        let nextPage = startNum + pagerLength

        if (nextPage < lastPagerNum) {
            removeMembersPager()
            showMembersPager(nextPage, lastPagerNum)
        }
        else {
            alert("다음 페이지가 없습니다")
        }
    })
}



/**
 * posts
 * */
function showPosts(posts) {
    for(let i = 0; i < posts.length && i < pagerLength; i++) {
        let postClone = $('#post').clone();

        postClone.id = 'post' + i
        postClone.children().eq(0).text(posts[i].writer)
        postClone.children().eq(1).children('a').text(posts[i].title)
        postClone.children().eq(1).children('a').attr('href', '/post/detail/' + posts[i].id)
        postClone.children().eq(2).click(function () {
            request.deleteRequest('/api/post/' + posts[i].id, () => {
                alert('게시물이 삭제되었습니다.')
                $(location).attr('href', '/channel/' + channelId + '/manager')
            })
        })

        postClone.css('display', 'block')

        $('#postList').append(postClone)
    }
}

function removePosts() {
    $('#postList').children('li').remove()
}



/**
 * posts pager
 * */
function showPostsPager(currentPage, lastPagerNum) {
    let pagerPrevButton = $('#prevPagerButton').clone();
    let nextPagerButton = $('#nextPagerButton').clone();
    let startNum = currentPage - (currentPage - 1) % pagerLength
    let loopCount = pagerLength

    if (lastPagerNum - startNum < pagerLength) {
        loopCount = lastPagerNum - startNum + 1
    }

    setPostPrevPagerButton(pagerPrevButton, currentPage, lastPagerNum)
    $('#postPagingFrame').append(pagerPrevButton)

    for (let i = 0; i < loopCount; i++) {
        let pager = $('#pagerNum').clone();

        pager.id = 'postPager' + i + startNum
        pager.children('a').text(i + startNum)
        pager.children('a').click(function (e) {
            e.preventDefault()
            let posts = getChannelPosts(channelId, i + startNum, {}, 5)

            removePosts()
            showPosts(posts.data)
        })

        pager.css('display', 'inline-block')
        $('#postPagingFrame').append(pager)
    }

    setPostNextPagerButton(nextPagerButton, currentPage, lastPagerNum)
    $('#postPagingFrame').append(nextPagerButton)
}

function removePostsPager() {
    $('#postPagingFrame').children().remove()
}

function setPostPrevPagerButton(nextPrevButtonNode, currentPage, lastPagerNum) {
    nextPrevButtonNode.css('display', 'block')
    nextPrevButtonNode.click(function (e) {
        e.preventDefault()
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let previousPage = startNum - pagerLength

        if (previousPage > 0) {
            removePostsPager()
            showPostsPager(previousPage, lastPagerNum)
        }
        else {
            alert("이전 페이지가 없습니다")
        }
    })
}

function setPostNextPagerButton(nextPagerButtonNode, currentPage, lastPagerNum) {
    nextPagerButtonNode.css('display', 'block')
    nextPagerButtonNode.click(function (e) {
        e.preventDefault()
        let startNum = currentPage - (currentPage - 1) % pagerLength
        let nextPage = startNum + pagerLength

        if (nextPage < lastPagerNum) {
            removePostsPager()
            showPostsPager(nextPage, lastPagerNum)
        }
        else {
            alert("다음 페이지가 없습니다")
        }
    })
}



/**
 * event handler
 * */
function setEventHandler() {
    $('#updateButton').click(function () {
        $(location).attr('href', '/channel/edit/' + channelId)
    })
}