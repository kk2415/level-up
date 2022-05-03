import httpRequest from "/js/module/httpRequest.js";
import ChannelNotice from "/js/module/channelNotice.js";

$(function () {
    let request = new httpRequest()
    let channelNotice = new ChannelNotice();

    let channelNoticeRequest = {}
    let channelId = getChannelId();
    let alert = $('#alert');

    console.log(channelId)

    configSummernote()
    hideAlertMessageBox();
    setEventHandler();

    function setEventHandler() {
        $('#createButton').click(function () {
            alert.children('p').remove()

            setChannelNotice()
            console.log(channelNoticeRequest)

            channelNotice.createChannelNotice(channelNoticeRequest, channelId)
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

    function setChannelNotice() {
        channelNoticeRequest.title = $('#title').val()
        channelNoticeRequest.content = $('#content').val()
        channelNoticeRequest.uploadFiles = getUploadFiles(channelNoticeRequest.content)
    }

    function createChannelNotice(data) {
        request.postRequest('/api/channel-notice?channel=' + channelId , data, (data) => {
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