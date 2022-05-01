import httpRequest from "/js/module/httpRequest.js";
import {createChannelValidation as validation} from '/js/module/validation.js';

$(function () {
    let request = new httpRequest()
    let channel = {}

    hideAlertMessageBox();
    configSummernote()
    setButtonEventHandler()


    function setButtonEventHandler() {
        $('#submitButton').click(function () {
            $('#alert').children('p').remove();

            setChannelObject()
            if (validate()) {
                uploadImage(); //이미지를 업로드하는 동시에 그 경로가 channel 오브젝트에 저장됨 -> 나중에 두 기능을 분리하는 리팩토링해야됨
                loadMemberInfo(); //멤버 이메일과 이름을 channel 오브젝트에 저장
                channel.category = "PROJECT"
                console.log(channel);

                createProjectChannel();
            }
            else {
                showAlertMessageBox();
            }
        })

        $('#cancel').click(function () {
            window.location.href = 'http://localhost:8080/';
        })
    }

    function validate() {
        let bool = true;

        if (!validation.name.test(channel.name) || channel.name == null) {
            $('#alert').append('<p>[프로젝트 이름] : 이름은 2자리 이상 15이하 자리수만 입력 가능합니다.</p>')
            bool = false;
        }
        if (!validation.limitedMemberNumber.test(channel.limitedMemberNumber) || channel.limitedMemberNumber == null) {
            $('#alert').append('<p>[프로젝트 인원] : 숫자만 입력가능하며 일의자리수부터 백의자리수까지 입력 가능합니다.</p>')
            bool = false;
        }
        if (!validation.thumbnailDescription.test(channel.thumbnailDescription) || channel.thumbnailDescription == null) {
            $('#alert').append('<p>[프로젝트 썸네일 인사말] : 1자리 이상 20이하 자리수만 입력 가능합니다.</p>')
            bool = false;
        }
        return bool;
    }

    function createProjectChannel() {
        request.postRequest('/api/channel', channel, (response) => {
            console.log("채널 등록 성공")
            $(location).attr('href', '/')
        })

        console.log("채널 등록 실패")
    }

    function setChannelObject() {
        channel.name = $('#name').val()
        channel.limitedMemberNumber = $('#limitedMemberNumber').val()
        channel.description = $('#description').val()
        channel.thumbnailDescription = $('#thumbnailDescription').val()
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

    function uploadImage() {
        let file = new FormData();
        file.append('file', $('#file')[0].files[0]);

        let result = request.postMultipartRequest('/api/channel/thumbnail', file);

        let uploadimage = {}
        uploadimage.storeFileName = result.storeFileName
        uploadimage.uploadFileName = result.uploadFileName

        channel.thumbnailImage = uploadimage;
    }

    function hideAlertMessageBox() {
        $('#alert').css('display', 'none');
    }

    function showAlertMessageBox() {
        $('#alert').css('display', 'block');
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

})