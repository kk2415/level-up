$(function () {
    let channel = {
    };

    let reg_name = /^[가-힣a-zA-Z0-9]{2,15}$/;
    let reg_limitedMemberNumber = /^[0-9]{1,3}$/;
    let reg_description = /^[가-힣a-zA-Z0-9]{2,30}$/;

    $('#alert').css('display', 'none');
    $('#cancel').click(function () {
        window.location.href = 'http://localhost:8080/';
    })
    setEventListenerOfInput();

    $('#submitButton').click(function () {
        $('#alert').children('p').remove();
        if (validation()) {
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
                console.log(error)
            })

            console.log(channel);
            $.ajax({
                url: '/api/channel',
                method: "POST",
                data: JSON.stringify(channel),
                dataType: 'json',
                contentType: 'application/json',
                async: false,
            })
            .done(function () {
                console.log("성공")
                window.location.href = 'http://localhost:8080/';
            })
            .fail(error => {
                console.log("실패")
                console.log(error.status)
            })
        }
        else {
            $('#alert').css('display', 'block');
        }
    })

    function validation() {
        let bool = true;

        if (!reg_name.test(channel.name) || channel.name == null) {
            $('#alert').append('<p>[이름] : 이름은 2자리 이상 15이하 자리수만 입력 가능합니다.</p>')
            bool = false;
        }
        if (!reg_limitedMemberNumber.test(channel.limitedMemberNumber) || channel.limitedMemberNumber == null) {
            $('#alert').append('<p>[회원제한수] : 숫자만 입력가능하며 일의자리수부터 백의자리수까지 입력 가능합니다.</p>')
            bool = false;
        }
        if (!reg_description.test(channel.description) || channel.description == null) {
            $('#alert').append('<p>[휴대폰번호] : 2자리 이상 30이하 자리수만 입력 가능합니다.</p>')
            bool = false;
        }
        return bool;
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

})