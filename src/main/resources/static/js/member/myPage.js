$(function () {
    const FILE_DIR = 'C:/Task/study/levelup/images/';

    $.ajax({
        url: '/api/member',
        method: 'GET',
        dataType: 'json',
        async: false,
    })
    .done(function (json) {
        $('#email').val(json.email);
        $('#name').val(json.name);
        $('#birthday').val(json.birthday);
        $('#phone').val(json.phone);
        if (json.gender.toString() == 'MALE') {
            $('#gender').val('남성');
        }
        else {
            $('#gender').val('여성');
        }
        $('#profile').attr('src', '/api/member/' + json.email + '/profile')
    })

    $('#updateButton').click(function () {
    })
    $('#backButton').click(function () {
        window.location.href = 'http://localhost:8080/';
    })
})

