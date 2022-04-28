$(function () {
    let post = {}
    let channelId = getChannelId();
    let alert = $('#alert');

    console.log(channelId)

    hideAlertMessageBox();
    setEventHandler();

    function setEventHandler() {
        $('#postingButton').click(function () {
            alert.children('p').remove()
            let category = $('#category').val();

            if (category === 'NONE') {
                alert.css('display', 'block')
                alert.append('<p>카테고리를 입력해주세요</p>')
            }
            else {
                post.category = category
                post.channelId = channelId
                uploadPost(post)
            }
        })

        $('#cancelButton').click(function () {
            window.history.back()
        })

        $('#title').change(function () {
            post.title = $('#title').val()
        })

        $('#content').change(function () {
            post.content = $('#content').val()
        })
    }

    function uploadPost(data) {
        $.ajax({
            url: '/api/post',
            method: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            dataType: 'json',
        })
        .done(function () {
            $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
        })
        .fail(function (error) {
            console.log(error)
        })
    }

    function getChannelId() {
        let search = decodeURI($(location).attr('search'))
        return search.substr(search.indexOf('=') + 1)
    }

    function hideAlertMessageBox() {
        alert.css('display', 'none')
    }
})