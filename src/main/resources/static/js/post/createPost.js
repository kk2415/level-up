import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()
    let post = {}
    let channelId = getChannelId();
    let alert = $('#alert');

    console.log(channelId)

    configSummernote()
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
                setPost()
                post.uploadFiles = getUploadFiles(post.content)
                post.category = category

                console.log(post)
                uploadPost(post)
            }
        })

        $('#cancelButton').click(function () {
            window.history.back()
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

    function setPost() {
        post.title = $('#title').val()
        post.content = $('#content').val()
        post.channelId = channelId
    }

    function uploadPost(data) {
        request.postRequest('/api/post', data, () => {
            $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
        })
    }

    function getChannelId() {
        let search = decodeURI($(location).attr('search'))
        return search.substr(search.indexOf('=') + 1)
    }

    function hideAlertMessageBox() {
        alert.css('display', 'none')
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