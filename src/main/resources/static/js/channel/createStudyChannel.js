$(function () {
    let reg_name = /^[가-힣a-zA-Z0-9\s]{2,15}$/;
    let reg_limitedMemberNumber = /^[0-9]{1,3}$/;
    let reg_description = /^[가-힣a-zA-Z0-9\s]{2,30}$/;
    let channel = {}

    hideAlertMessageBox();

    setEventListenerOfInput();
    setButtonEventHandler()




    function setButtonEventHandler() {
        $('#submitButton').click(function () {
            $('#alert').children('p').remove();

            if (validation()) {
                uploadImage(); //이미지를 업로드하는 동시에 그 경로가 channel 오브젝트에 저장됨 -> 나중에 두 기능을 분리하는 리팩토링해야됨
                loadMemberInfo(); //멤버 이메일과 이름을 channel 오브젝트에 저장
                channel.category = "STUDY"
                console.log(channel);

                createChannel();
            }
            else {
                showAlertMessageBox();
            }
        })

        $('#cancel').click(function () {
            window.location.href = 'http://localhost:8080/';
        })
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
        if (!reg_description.test(channel.description) || channel.description == null) {
            $('#alert').append('<p>[채널 설명] : 2자리 이상 30이하 자리수만 입력 가능합니다.</p>')
            bool = false;
        }
        return bool;
    }

    function createChannel() {
        $.ajax({
            url: '/api/channel',
            method: "POST",
            data: JSON.stringify(channel),
            dataType: 'json',
            contentType: 'application/json',
            async: false,
        })
            .done(function () {
                console.log("채널 등록 성공")
                window.location.href = 'http://localhost:8080/';
            })
            .fail(error => {
                console.log("채널 등록 실패")
                console.log(error.status)
            })
    }

    function setEventListenerOfInput() {
        $('#name').change(event => {
            channel.name = event.target.value;
        });

        $('#limitedMemberNumber').change(event => {
            channel.limitedMemberNumber = event.target.value;
        });

        $('#description').change(event => {
            channel.description = event.target.value;
        });
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

    function uploadImage() {
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
            console.log(data)
            let uploadimage = {
            };

            uploadimage.storeFileName = data.storeFileName
            uploadimage.uploadFileName = data.uploadFileName

            channel.uploadFile = uploadimage;
        })
        .fail(function (error) {
            console.log("이미지 업로드 실패")
            console.log(error)
        })
    }

    function hideAlertMessageBox() {
        $('#alert').css('display', 'none');
    }

    function showAlertMessageBox() {
        $('#alert').css('display', 'block');
    }

})