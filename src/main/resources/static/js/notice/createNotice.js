import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()
    let noticeRequest = {}
    let alertMessageBox = $('#alert');

    configSummernote()
    hideAlertMessageBox();
    setEventHandler();

    function setEventHandler() {
        $('#postingButton').click(function () {
            alertMessageBox.children('p').remove()

            setRequest()
            createNotice(noticeRequest)
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

    function setRequest() {
        noticeRequest.title = $('#title').val()
        noticeRequest.content = $('#content').val()
        noticeRequest.uploadFiles = getUploadFiles(noticeRequest.content)
    }

    function createNotice(noticeRequest) {
        let result = request.postRequest('/api/notice', noticeRequest, () => {
            $(location).attr('href', '/notice?page=1')
        })

        if (result == null) {
            alert('가입된 회원만 글을 작성할 수 있습니다.')
        }
    }

    function hideAlertMessageBox() {
        alertMessageBox.css('display', 'none')
    }

    function uploadFile(file, editor) {
        let form = new FormData()
        form.append("file", file)

        request.postMultipartRequest('/api/notice/file', form, (data) => {
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