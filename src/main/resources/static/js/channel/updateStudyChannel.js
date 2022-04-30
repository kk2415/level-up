$(function () {
    let reg_name = /^[ㄱ-ㅎ가-힣a-zA-Z0-9\s]{2,15}$/;
    let reg_thumbnailDescription = /^[ㄱ-ㅎ가-힣a-zA-Z0-9\s]{1,15}$/;
    let reg_limitedMemberNumber = /^[0-9]{1,3}$/;

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

            if (validation()) {
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
        $.ajax({
            url: '/api/detail-description/' + channelId,
            method: 'GET',
            async: false,
        })
        .done(function (data) {
            channel = data
        })
        .fail(function (error) {
            console.log(error)
        })
    }

    function showChannel() {
        $('#name').val(channel.name)
        $('#limitedMemberNumber').val(channel.limitedMemberNumber)
        $('#description').val(channel.description)
        $('#thumbnailDescription').val(channel.thumbnailDescription)
    }

    function validation() {
        let bool = true;

        if (!reg_name.test(channel.name) || channel.name == null) {
            $('#alert').append('<p>[채널 이름] : 이름은 2자리 이상 15이하 자리수만 입력 가능합니다.</p>')
            bool = false;
        }
        if (!reg_limitedMemberNumber.test(channel.limitedMemberNumber) || channel.limitedMemberNumber == null) {
            $('#alert').append('<p>[회원제한수] : 숫자만 입력가능하며 일의자리수부터 백의자리수까지 입력 가능합니다.</p>')
            bool = false;
        }
        if (!reg_thumbnailDescription.test(channel.thumbnailDescription) || channel.thumbnailDescription == null) {
            $('#alert').append('<p>[썸네일 인사말] : 썸네일 인사말은 1자리 이상 15자리 이하 자리수만 입력 가능합니다.</p>')
            bool = false;
        }

        return bool;
    }

    function updateChannel() {
        $.ajax({
            url: '/api/channel/' + channelId,
            method: "PATCH",
            data: JSON.stringify(channel),
            dataType: 'json',
            contentType: 'application/json',
            async: false,
        })
        .done(function () {
            console.log("채널 등록 성공")
            $(location).attr('href', '/channel/detail-description/' + channelId)
        })
        .fail(error => {
            console.log("채널 등록 실패")
            console.log(error)
        })
    }

    function loadMemberInfo() {
        $.ajax({
            url: '/api/member',
            method: "GET",
            async: false,
        })
        .done(function (data) {
            channel.memberEmail = data.email;
            channel.managerName = data.name;
        })
        .fail(error => {
            console.log('회원 로드 실패')
            console.log(error)
        })
    }

    function uploadThumbnailImage() {
        let file = new FormData();
        file.append('file', $('#file')[0].files[0]);

        $.ajax({
            url: '/api/channel/thumbnail',
            method: 'POST',
            data: file,
            processData: false, // - processData : false로 선언 시 formData를 string으로 변환하지 않음
            contentType: false, // - contentType : false 로 선언 시 content-type 헤더가 multipart/form-data로 전송되게 함
            cache: false,
            enctype: 'multipart/form-data',
            async: false,
        })
        .done(function (data) {
            console.log("이미지 업로드 성공")
            let uploadimage = {
            };

            uploadimage.storeFileName = data.storeFileName
            uploadimage.uploadFileName = data.uploadFileName

            channel.thumbnailImage = uploadimage;
        })
        .fail(function (error) {
            console.log("이미지 업로드 실패")
            console.log(error)
        })
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

        $.ajax({
            url : '/api/channel/descriptionFiles',
            method : "POST",
            processData: false, // - processData : false로 선언 시 formData를 string으로 변환하지 않음
            contentType: false, // - contentType : false 로 선언 시 content-type 헤더가 multipart/form-data로 전송되게 함
            enctype: 'multipart/form-data',
            cache: false,
            async: false,
            data : form,
        })
        .done(function (data) {
            $(editor).summernote('insertImage', data.fullPath);
        })
        .fail(function (error) {
            console.log(error)
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