$(function () {
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
        $.ajax({
            url: '/api/detail-description/' + channelId,
            method: 'GET',
            async: false,
        })
        .done(function (data) {
            console.log(data)
            channelDescription = data
        })
        .fail(function (error) {
            console.log(error)
        })
    }

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
                // $(location).attr('href', '/channel/project/edit')
            }
        })

        $('#deleteButton').click(function () {
        })
    }
})