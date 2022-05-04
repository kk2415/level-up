import httpRequest from "/js/module/httpRequest.js";
import ChannelNotice from "/js/module/channelNotice.js";

$(function () {
    let request = new httpRequest()
    let channelNotice = new ChannelNotice();

    let channelNoticeId = getChannelNoticeId()
    let channelId = getChannelId()
    let channelNoticeResponse = getChannelNoticeResponse()

    let channelNoticeRequest = {}

    configSummernote()

    setEventHandler()
    setPostContents()

    hideAlertMessageBox()


    function setEventHandler() {
        $('#updateButton').click(function () {
            channelNoticeRequest.title = $('#title').val()
            channelNoticeRequest.content = $('#content').val()
            channelNoticeRequest.uploadFiles = getUploadFiles(channelNoticeRequest.content)
            console.log(channelNoticeRequest)

            channelNotice.updateChannelNotice(channelNoticeRequest, channelNoticeId, channelId)
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

    function getChannelNoticeResponse() {
        return request.getRequest('/api/channel-notice/' + channelNoticeId + '?view=false')
    }

    function setPostContents() {
        $('#title').val(channelNoticeResponse.title)
        $('#content').html(channelNoticeResponse.content)
    }

    function getChannelNoticeId() {
        let pathname = decodeURI($(location).attr('pathname'))

        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    function getChannelId() {
        let queryString = decodeURI($(location).attr('search'))

        return queryString.substr(queryString.lastIndexOf('=') + 1)
    }

    // function getMemberEmail() {
    //     let queryString = decodeURI($(location).attr('search'))
    //
    //     return queryString.substring(queryString.indexOf('=') + 1, queryString.indexOf('&'))
    // }

    function uploadFile(file, editor) {
        let form = new FormData()
        form.append("file", file)

        request.postMultipartRequest('/api/channel-notice/files', form, (data) => {
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