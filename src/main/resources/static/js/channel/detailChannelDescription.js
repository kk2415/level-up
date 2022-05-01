import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()
    let memberEmail = getMemberEmail()
    let channelId = getChannelId()
    let channelDescription = {}

    setEventHandler()
    setChannelDescription()
    console.log(channelDescription)

    showChannelDescription()
    showModifyAndDeleteButton()

    function showChannelDescription() {
        $('#manager').text(channelDescription.managerName)
        $('#name').text(channelDescription.name)
        $('#dateCreated').text(channelDescription.dateCreated)
        $('#memberCount').text(channelDescription.memberCount)
        $('#limitedMemberNumber').text(channelDescription.limitedMemberNumber)
        $('#description').html(channelDescription.description)
    }

    function setChannelDescription() {
        channelDescription = request.getRequest('/api/detail-description/' + channelId)
    }

    function getMemberEmail() {
        let member = request.getRequest('/api/member');

        return member.email
    }

    function isLoginMember() {
        if (memberEmail == null) {
            return false
        }
        return true
    }

    function getChannelId() {
        let pathname = decodeURI($(location).attr('pathname'))
        return pathname.substr(pathname.lastIndexOf('/') + 1)
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

    function setEventHandler() {
        $('#toAllStudyChannelButton').click(function () {
            $(location).attr('href', '/')
        })

        $('#registerStudyButton').click(function () {
        })

        $('#modifyButton').click(function () {
            if (channelDescription.category === 'STUDY') {
                $(location).attr('href', '/channel/study/edit/' + channelId)
            }
            else {
                // $(location).attr('href', '/channel/project/edit/' + channelId)
            }
        })

        $('#deleteButton').click(function () {
            request.deleteRequest('/api/channel/' + channelId, () => {
                alert("삭제되었습니다.")
                $(location).attr('href', '/')
            });
        })
    }
})