import httpRequest from "/js/module/httpRequest.js";
import {createMemberValidation as validation} from '/js/module/validation.js';

let request = new httpRequest()

let member = {
    gender : 'FEMALE'
}

let formData = new FormData();
formData.set('gender', 'FEMALE');



$(function () {
    hideAlertMessageBox();

    setEventListenerOfInput()
    setRegisterButtonHandler();
})


function setRegisterButtonHandler() {
    $('#submitButton').click(function () {
        removeAlertMassageBox()

        if (validate()) {
            let uploadedFile = uploadImage()

            member.uploadFile = uploadedFile
            registerMember(member)
        } else {
            console.log("유효성 검증 실패")
        }

        showAlertMessageBox()
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
    let result = request.postRequest('/api/member', member, () => {
        $(location).attr('href', '/')
    });

    if (result == null) {
        $('#alert').append('<p>[이매일] : 중복된 이메일입니다.</p>')
    }
}

function uploadImage() {
    let file = new FormData()
    let uploadImage = new Object()

    file.append('file', $('#file')[0].files[0])
    let result = request.postMultipartRequest('/api/member/image', file)

    uploadImage.storeFileName = result.storeFileName
    uploadImage.uploadFileName = result.uploadFileName

    return uploadImage
}

function validate() {
    let bool = true;

    if (!validation.email.test($('#email').val()) || $('#email').val() == null) {
        $('#alert').append('<p>[이매일] : 유효한 이메일 형식이 아닙니다.</p>')
        bool = false;
    }
    if (!validation.name.test($('#name').val()) || $('#name').val() == null) {
        $('#alert').append('<p>[이름] : 이름은 2자리 이상 15이하, 한글만 입력하세요</p>')
        bool = false;
    }
    if (!validation.phone.test($('#phone').val()) || $('#phone').val() == null) {
        $('#alert').append('<p>[휴대폰번호] : 유효한 형식이 아닙니다.</p>')
        bool = false;
    }
    if (!validation.password.test($('#password').val()) || $('#password').val() == null) {
        $('#alert').append('<p>[패스워드] : 비밀번호는 8자리이상 24이하, 영문자/숫자만 및 특수문자만 입력하세요</p>')
        bool = false;
    }
    if (!validation.birthday.test($('#birthday').val()) || $('#birthday').val() == null) {
        $('#alert').append('<p>[생년월일] : 생년월일은 6자리 이상 입력해주세요</p>')
        bool = false;
    }

    return bool;
}

function showAlertMessageBox() {
    $('#alert').css('display', 'block')
}

function hideAlertMessageBox() {
    $('#alert').css('display', 'none')
}

function removeAlertMassageBox() {
    $('#alert').children('p').remove();
}
