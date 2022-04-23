$(function () {
    let post = {}
    let channelId = getChannelId();
    let alert = $('#alert');

    alert.css('display', 'none')
    setEventHandler();

    function setEventHandler() {
        $('#postingButton').click(function () {
            alert.children('p').remove()
            let category = $('#category').val();

            if (category === 'NONE') {
                alert.css('display', 'block')
                alert.append('<p>카테고리를 입력해주세요</p>')
            }

            post.category = category
            post.channelId = channelId
            uploadPost(post)
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
            $(location).attr('href', '/channel/detail/' + channelId)
        })
        .fail(function (error) {
            console.log(error)
        })
    }

    function getChannelId() {
        let pathname = $(location).attr('pathname')
        // console.log(pathname)
        return pathname.charAt((pathname.substr(1).indexOf('/') + 1) + 1)
    }
})