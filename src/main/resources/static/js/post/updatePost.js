import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()

    let postId = getPostId()
    let channelId = getChannelId()
    let memberEmail = getMemberEmail()
    let post = {}
    let requestPost = {}
    console.log(channelId)

    configSummernote()

    setPost()
    setPostContents()

    setEventHandler()
    hideAlertMessageBox()


    function setEventHandler() {
        $('#updateButton').click(function () {
            requestPost.memberEmail = memberEmail
            requestPost.writer = post.writer
            requestPost.title = $('#title').val()
            requestPost.content = $('#content').val()
            requestPost.category = $('#category').val();
            requestPost.uploadFiles = getUploadFiles(requestPost.content)

            updatePost(requestPost)
        })

        $('#cancelButton').click(function () {
            $(location).attr('href', '/channel/detail/' + channelId)
        })
    }

    function getUploadFiles(htmlCode) {
        let uploadFiles = []
        let offset = 0

        while (htmlCode.indexOf('img src', offset) !== -1) {
            let uploadFile = {}

            let imgTagStr = htmlCode.substr(htmlCode.indexOf('img src', offset))
            let firstIdx = imgTagStr.indexOf('"') + 1
            let lastIdx = imgTagStr.indexOf('"', firstIdx)

            uploadFile.storeFileName = imgTagStr.substring(firstIdx, lastIdx)
            uploadFile.uploadFileName = 'image'

            uploadFiles.push(uploadFile)

            offset = htmlCode.indexOf('img src', offset) + 'img src'.length
        }
        return uploadFiles;
    }

    function hideAlertMessageBox() {
        $('#alert').css('display', 'none')
    }

    function setPost() {
        $.ajax({
            url: '/api/post/' + postId,
            method: "GET",
            async: false,
        })
        .done(function (data) {
            post.title = data.title
            post.writer = data.writer
            post.content = data.content
            post.category = data.category
        })
        .fail(function (error) {
            console.log(error)
        })
    }

    function setPostContents() {
        $('#title').val(post.title)
        $('#content').html(post.content)
        $("#category").val(post.category).attr("selected", "selected");
    }

    function getPostId() {
        let pathname = decodeURI($(location).attr('pathname'))

        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    function getChannelId() {
        let queryString = decodeURI($(location).attr('search'))

        return queryString.substr(queryString.lastIndexOf('=') + 1)
    }

    function getMemberEmail() {
        let queryString = decodeURI($(location).attr('search'))

        return queryString.substring(queryString.indexOf('=') + 1, queryString.indexOf('&'))
    }

    function updatePost(request) {
        $.ajax({
            url: '/api/post/' + postId,
            method: 'PATCH',
            data: JSON.stringify(request),
            contentType: 'application/json',
            dataType: 'json',
            async: false,
        })
        .done(function () {
            console.log(channelId)
            $(location).attr('href', '/post/detail/' + postId + '?channel=' + channelId)
        })
        .fail(function (error) {
            console.log(error)
        })
    }

    function uploadFile(file, editor) {
        let form = new FormData()
        form.append("file", file)

        request.postMultipartRequest('/api/post/files', form, (data) => {
            $(editor).summernote('insertImage', data)
        })
    }

    function configSummernote() {
        $(document).ready(function() {
            $('#content').summernote({
                height: 400,
                minHeight: null,
                maxHeight: null,
                callbacks: {
                    onImageUpload : function(files) {

                        for (let i = 0; i < files.length; i++) {
                            uploadFile(files[i], this);
                        }
                    },
                    onPaste: function (e) {
                        let clipboardData = e.originalEvent.clipboardData;
                        if (clipboardData && clipboardData.items && clipboardData.items.length) {
                            let item = clipboardData.items[0];
                            if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                                e.preventDefault();
                            }
                        }
                    }
                }
            })
        })
    }

})