import httpRequest from "/js/module/httpRequest.js";
import {loginMemberValidation as validation} from '/js/module/validation.js';

function getRedirectUrl() {
    let search = decodeURI($(location).attr('search'));

    return search.substr(search.indexOf('=') + 1)
}

$(function () {
    let request = new httpRequest()
    let loginForm = {}
    let redirectUrl = getRedirectUrl()

    hiadAlertMessageBox()
    setLoginInfoEventHandler()
    setButtonEventHandler()

    function setButtonEventHandler() {
        $('#loginButton').click(function () {
            onLoginButton()
        })

        $('#loginCancel').click(function () {
            $(location).attr('href', '/')
        })
    }

    function onLoginButton() {
        removeAlertMassageBox()

        if (validate()) {
            requestPost();
        }
        else {
            showAlertMessageBox()
        }
    }

    function validate() {
        let bool = true

        if (!validation.email.test($('#email').val()) || $('#email').val() == null) {
            $('#alert').append('<p>[이매일] : 유효한 이메일 형식이 아닙니다.</p>')
            bool = false
        }
        if (!validation.password.test($('#password').val()) || $('#password').val() == null) {
            $('#alert').append('<p>[패스워드] : 비밀번호는 8자리이상 24이하, 영문자/숫자만 및 특수문자만 입력하세요</p>')
            bool = false
        }

        return bool;
    }

    function requestPost() {
        let result = request.postRequest('/api/member/login', loginForm, () => {
            $(location).attr('href', redirectUrl)
        })

        if (result == null) {
            removeAlertMassageBox()

            $('#alert').append('<p>이메일 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.</p>')
            if ($('#alert').css('display') === 'none') {
                showAlertMessageBox()
            }
        }
    }

    function setLoginInfoEventHandler() {
        $('#email').change(function (event) {
            loginForm.email = event.target.value
        })

        $('#password').change(function (event) {
            loginForm.password = event.target.value
        })
    }

    function hiadAlertMessageBox() {
        $('#alert').css('display', 'none')
    }

    function showAlertMessageBox() {
        $('#alert').css('display', 'block')
    }

    function removeAlertMassageBox() {
        $('#alert').children('p').remove();
    }
})


