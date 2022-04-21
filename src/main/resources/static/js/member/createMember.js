$(function () {
    let reg_name = /^[가-힣]{2,15}$/;
    let reg_phone = /^\d{3}-\d{3,4}-\d{4}$/;
    let reg_email =/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    let reg_password = /^[a-z0-9_-]{6,18}$/;
    let reg_birthday = /^[0-9]{6}$/;

    let member = {
        email : "",
        password : "",
        name : "",
        gender : "",
        birthday : "",
        phone : "",
    };
    let validationMessage = "";

    $('#alert').css('display', 'none')
    setEventListenerOfInput()

    $('#submitButton').click(function () {
        $('#alert').children('p').text("")
        if (validation()) {
            $.ajax({
                url: '/member/create',
                enctype: 'multipart/form-data',
                method: 'POST',
                dataType: 'json',
                data: JSON.stringify(member),
            })
            .done(function () {
                window.location.href = 'http://localhost:8080/'
            })
            .fail(function (error) {
                console.log(error)
            })
        }
        else {
            console.log("실패")
            $('#alert').css('display', 'block')
            $('#alert').children('p').text(validationMessage)
        }
    })

    function validation() {
        let bool = true;

        if (!reg_email.test(member.email)) {
            validationMessage += "[이매일] : 유효한 이메일 형식이 아닙니다\n";
            bool = false;
        }
        if (!reg_name.test(member.name)) {
            validationMessage += "[이름] : 이름은 2자리 이상 15이하, 한글만 입력하세요\n";
            bool = false;
        }
        if (!reg_phone.test(member.phone)) {
            validationMessage += "[휴대폰번호] : 유효한 형식이 아닙니다.\n";
            bool = false;
        }
        if (!reg_password.test(member.password)) {
            validationMessage += "[패스워드] : 비밀번호는 6자리이상, 영문자/숫자만 및 하이푼(_-)만 입력하세요\n";
            bool = false;
        }
        if (!reg_birthday.test(member.birthday)) {
            validationMessage += "[생년월일] : 생년월일은 6자리 이상 입력해주세요";
            bool = false;
        }
        return bool;
    }

    function setEventListenerOfInput() {
        $('#name').change(function (event) {
            member.name = event.target.value;
        })
        $('#email').change(function (event) {
            member.email = event.target.value;
        })
        $('#password').change(function (event) {
            member.password = event.target.value;
        })
        $('#gender1').change(function (event) {
            member.gender = event.target.value;
        })
        $('#gender2').change(function (event) {
            member.gender = event.target.value;
        })
        $('#birthday').change(function (event) {
            member.birthday = event.target.value;
        })
        $('#phone').change(function (event) {
            member.phone = event.target.value;
        })
    }

})
