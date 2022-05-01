import httpRequest from "/js/module/httpRequest.js";
import {createChannelValidation as validation} from '/js/module/validation.js';

$(function () {
    let request = new httpRequest()
    let channelId = getChannelId()
    let channel = {}

    configSummernote()
    hideAlertMessageBox()
    setButtonEventHandler()

    setChannel()
    showChannel()

    function setButtonEventHandler() {
        $('#updateButton').click(function () {
            $('#alert').children('p').remove();

            setChannelObject();

            if (validate()) {
                uploadThumbnailImage(); //이미지를 업로드하는 동시에 그 경로가 channel 오브젝트에 저장됨 -> 나중에 두 기능을 분리하는 리팩토링해야됨
                console.log(channel);

                updateChannel();
            }
            else {
                showAlertMessageBox();
            }
        })

        $('#cancel').click(function () {
            $(location).attr('href', '/channel/detail-description/' + channelId)
        })
    }

    function setChannel() {
        channel = request.getRequest('/api/detail-description/' + channelId)
    }

    function showChannel() {
        $('#name').val(channel.name)
        $('#limitedMemberNumber').val(channel.limitedMemberNumber)
        $('#description').val(channel.description)
        $('#thumbnailDescription').val(channel.thumbnailDescription)
    }

    function validate() {
        let bool = true;

        if (!validation.name.test(channel.name) || channel.name == null) {
            $('#alert').append('<p>[채널 이름] : 이름은 2자리 이상 15이하 자리수만 입력 가능합니다.</p>')
            bool = false;
        }
        if (!validation.limitedMemberNumber.test(channel.limitedMemberNumber) || channel.limitedMemberNumber == null) {
            $('#alert').append('<p>[회원제한수] : 숫자만 입력가능하며 일의자리수부터 백의자리수까지 입력 가능합니다.</p>')
            bool = false;
        }
        if (!validation.thumbnailDescription.test(channel.thumbnailDescription) || channel.thumbnailDescription == null) {
            $('#alert').append('<p>[썸네일 인사말] : 썸네일 인사말은 1자리 이상 15자리 이하 자리수만 입력 가능합니다.</p>')
            bool = false;
        }

        return bool;
    }

    function updateChannel() {
        request.patchRequest('/api/channel/' + channelId, channel, () => {
            $(location).attr('href', '/channel/detail-description/' + channelId)
        })
    }

    function loadMemberInfo() {
        let result = request.getRequest('/api/member');

        if ('status' in result && result.status !== 200) {
            console.log('회원 로드 실패')
            console.log(result)
        }
        else {
            channel.memberEmail = result.email;
            channel.managerName = result.name;
        }
    }

    function uploadThumbnailImage() {
        let file = new FormData();
        file.append('file', $('#file')[0].files[0]);

        let result = request.postMultipartRequest('/api/channel/thumbnail', file);

        let uploadimage = {}
        uploadimage.storeFileName = result.storeFileName
        uploadimage.uploadFileName = result.uploadFileName

        channel.thumbnailImage = uploadimage;
    }

    function setChannelObject() {
        channel.name = $('#name').val()
        channel.limitedMemberNumber = $('#limitedMemberNumber').val()
        channel.description = $('#description').val()
        channel.thumbnailDescription = $('#thumbnailDescription').val()
    }

    function uploadFile(files, editor) {
        let form = new FormData();
        form.append("files", files);

        request.postMultipartRequest('/api/channel/descriptionFiles', form, (data) => {
            $(editor).summernote('insertImage', data.fullPath)
        })
    }

    function configSummernote() {
        $(document).ready(function() {
            $('#description').summernote({
                height: 400,
                minHeight: null,
                maxHeight: null,
                callbacks: {
                    onImageUpload : function(files) {
                        uploadFile(files[0], this);
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

    function getChannelId() {
        let pathname = decodeURI($(location).attr('pathname'))
        return pathname.substr(pathname.lastIndexOf('/') + 1)
    }

    function hideAlertMessageBox() {
        $('#alert').css('display', 'none');
    }

    function showAlertMessageBox() {
        $('#alert').css('display', 'block');
    }

})