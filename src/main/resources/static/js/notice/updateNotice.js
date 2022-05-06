import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()

    let noticeId = getNoticeId()
    let noticeResponse = getNotice()
    let requestNotice = {}

    hideAlertMessageBox()
    setEventHandler()

    configSummernote()
    showNotice(noticeResponse)


    function getNotice() {
        return request.getRequest('/api/notice/' + noticeId + '?view=false')
    }

    function setEventHandler() {
        $('#updateButton').click(function () {
            requestNotice.title = $('#title').val()
            requestNotice.content = $('#content').val()
            requestNotice.uploadFiles = getUploadFiles(requestNotice.content)

            updateNotice(requestNotice)
        })

        $('#cancelButton').click(function () {
            $(location).attr('href', '/notice?page=1')
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

    function showNotice(noticeResponse) {
        $('#title').val(noticeResponse.title)
        $('#content').html(noticeResponse.content)
    }

    function getNoticeId() {
        let pathname = decodeURI($(location).attr('pathname'))

        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    function updateNotice(noticeRequest) {
        request.patchRequest('/api/notice/' + noticeId, noticeRequest, () => {
            $(location).attr('href', '/notice?page=1')
        })
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