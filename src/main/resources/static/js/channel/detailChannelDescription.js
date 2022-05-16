import httpRequest from "/js/module/httpRequest.js";

let request = new httpRequest()

let memberEmail = getMemberEmail()
let channelId = getChannelId()
let channelDescription = getChannelDescription()

$(function () {
    setEventHandler()

    showModifyAndDeleteButton()
    showChannelDescription()
})


/**
 * getter
 * */
function getMemberEmail() {
    let member = request.getRequest('/api/member');

    return member == null ? null : member.email
}

function getChannelId() {
    let pathname = decodeURI($(location).attr('pathname'))

    return pathname.substr(pathname.lastIndexOf('/') + 1)
}

function getChannelDescription() {
    return request.getRequest('/api/detail-description/' + channelId)
}


/**
 * channelDescription
 * */
function showChannelDescription() {
    $('#manager').text(channelDescription.managerName)
    $('#name').text(channelDescription.name)
    $('#dateCreated').text(channelDescription.dateCreated)
    $('#memberCount').text(channelDescription.memberCount)
    $('#limitedMemberNumber').text(channelDescription.limitedMemberNumber)
    $('#description').html(channelDescription.description)
}


/**
 * eventHandler
 * */
function setEventHandler() {
    $('#registerStudyButton').click(function () {
        let result = request.postRequest('/api/channel/' + channelId + '/waiting-member')

        if (result == null) {
            alert('로그인해야합니다.')
        }
        else {
            alert('신청되었습니다. 매니저가 수락할 때 까지 기다려주세요.')
        }
    })

    $('#enterStudyButton').click(function () {
        $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
    })

    $('#toAllStudyChannelButton').click(function () {
        $(location).attr('href', '/')
    })

    $('#modifyButton').click(function () {
        $(location).attr('href', '/channel/edit/' + channelId)
    })

    $('#deleteButton').click(function () {
        request.deleteRequest('/api/channel/' + channelId, () => {
            alert("삭제되었습니다.")
            $(location).attr('href', '/')
        });
    })
}


/**
 *
 * */
function isLoginMember() {
    if (memberEmail == null) {
        return false
    }
    return true
}

function isMyChannel() {
    if (memberEmail == channelDescription.managerName) {
        return true
    }
    return false
}

function showModifyAndDeleteButton() {
    if (isLoginMember() && isMyChannel()) {
        $('#modifyButton').css('display', 'inline-block')
        $('#deleteButton').css('display', 'inline-block')
    }
}