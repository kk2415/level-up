$(function () {
    let reg_name = /^[가-힣]{2,15}$/;
    let reg_phone = /^\d{3}-\d{3,4}-\d{4}$/;
    let reg_email =/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    let reg_password = /^[a-z0-9_-]{6,18}$/;
    let reg_birthday = /^[0-9]{6}$/;

    let member = {}
    member.gender = 'FEMALE'

    let formData = new FormData();
    formData.set('gender', 'FEMALE');

    hiadAlertMessageBox();

    setEventListenerOfInput()
    setRegisterButtonHandler();



    function setRegisterButtonHandler() {
        $('#submitButton').click(function () {
            removeAlertMassageBox();

            if (validation()) {
                let uploadedFile = uploadImage();
                member.uploadFile = uploadedFile

                registerMember(member);
            } else {
                console.log("유효성 검증 실패")
                $('#alert').css('display', 'block')
            }
        })
    }

    function setEventListenerOfInput() {
        $('#name').change(function (event) {
            member.name = event.target.value
            formData.set('name', event.target.value);
        })
        $('#email').change(function (event) {
            member.email = event.target.value
            formData.set('email', event.target.value);
        })
        $('#password').change(function (event) {
            member.password = event.target.value
            formData.set('password', event.target.value);
        })
        $('#gender1').click(function (event) {
            member.gender = 'MALE'
            formData.set('gender', event.target.value);
        })
        $('#gender2').click(function (event) {
            member.gender = 'FEMALE'
            formData.set('gender', event.target.value);
        })
        $('#birthday').change(function (event) {
            member.birthday = event.target.value
            formData.set('birthday', event.target.value);
        })
        $('#phone').change(function (event) {
            member.phone = event.target.value
            formData.set('phone', event.target.value);
        })
    }

    function registerMember(member) {
        $.ajax({
            url: '/api/member',
            method: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(member),
            async: false,
        })
        .done(function () {
            console.log("성공")
            window.location.href = 'http://localhost:8080/'
        })
        .fail(function (error) {
            console.log("전송 실패")
            console.log(error)
        })
    }

    function uploadImage() {
        let uploadImage = {}
        let file = new FormData()

        file.append('file', $('#file')[0].files[0])

        $.ajax({
            url: '/api/member/image',
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

            uploadImage.storeFileName = data.storeFileName
            uploadImage.uploadFileName = data.uploadFileName
        })
        .fail(function (error) {
            console.log(error)
        })

        return uploadImage
    }

    function validation() {
        let bool = true;

        if (!reg_email.test($('#email').val()) || $('#email').val() == null) {
            $('#alert').append('<p>[이매일] : 유효한 이메일 형식이 아닙니다.</p>')
            bool = false;
        }
        if (!reg_name.test($('#name').val()) || $('#name').val() == null) {
            $('#alert').append('<p>[이름] : 이름은 2자리 이상 15이하, 한글만 입력하세요</p>')
            bool = false;
        }
        if (!reg_phone.test($('#phone').val()) || $('#phone').val() == null) {
            $('#alert').append('<p>[휴대폰번호] : 유효한 형식이 아닙니다.</p>')
            bool = false;
        }
        if (!reg_password.test($('#password').val()) || $('#password').val() == null) {
            $('#alert').append('<p>[패스워드] : 비밀번호는 6자리이상, 영문자/숫자만 및 하이푼(_-)만 입력하세요</p>')
            bool = false;
        }
        if (!reg_birthday.test($('#birthday').val()) || $('#birthday').val() == null) {
            $('#alert').append('<p>[생년월일] : 생년월일은 6자리 이상 입력해주세요</p>')
            bool = false;
        }

        return bool;
    }

    function hiadAlertMessageBox() {
        $('#alert').css('display', 'none')
    }

    function removeAlertMassageBox() {
        $('#alert').children('p').remove();
    }

})
