$(function () {
    let channelId = getChannelId()
    let page = 1
    let postSearch = {}
    let result = {}

    $('#postingButton').click(function () {
        $(location).attr('href', '/channel/' + channelId + '/posting')
    })

    $('#backButton').click(function () {
        $(location).attr('href', '/')
    })

    // result = loadPosts(channelId, page, postSearch)
    console.log(result)

    function loadPosts(channelId, page, postSearch) {
        $.ajax({
            url: '/api/' + channelId + '/posts/' + page,
            method: 'GET',
            data: JSON.stringify(postSearch),
            contentType: 'application/json',
            dataType: 'json',
            async: false,
        })
        .done(function (data) {
            let result = {}

            result = data
            return data;
        })
    }

    function getChannelId() {
        let pathname = $(location).attr('pathname')
        return pathname.charAt(pathname.lastIndexOf('/') + 1)
    }

})