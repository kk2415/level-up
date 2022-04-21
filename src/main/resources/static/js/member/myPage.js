$(function () {
    const FILE_DIR = 'C:/Task/study/levelup/images/';
    let member = {}
    let modifyButton = $('#modifyButton');
    let updateButton = $('#updateButton');
    let inputFile = $('#formFileLg');

    renderMemberInfo();
    modifyButton.css('display', 'none');

    updateButton.click(function () {
        updateButton.css('display', 'none')
        modifyButton.css('display', 'block');
        inputFile.attr('disabled', false)
    })

    modifyButton.click(function () {
        if (inputFile[0].files[0] !== undefined) {
            updateMemberImage();
        }
        modifyButton.css('display', 'none');
        updateButton.css('display', 'block')
        inputFile.attr('disabled', true)
    })

    $('#backButton').click(function () {
        window.location.href = 'http://localhost:8080/';
    })

    function renderMemberInfo() {
        $.ajax({
            url: '/api/member',
            method: 'GET',
            dataType: 'json',
            async: false,
        })
        .done(function (json) {
            member.email = json.email;

            $('#email').val(json.email);
            $('#name').val(json.name);
            $('#birthday').val(json.birthday);
            $('#phone').val(json.phone);
            if (json.gender.toString() == 'MALE') {
                $('#gender').val('남성');
            } else {
                $('#gender').val('여성');
            }
            $('#profile').attr('src', '/api/member/' + json.email + '/image')
        })
    }

    function updateMemberImage() {
        let formData = new FormData();
        formData.append('file', inputFile[0].files[0])

        console.log(inputFile[0].files[0])
        $.ajax({
            url: '/api/member/' + member.email + '/image',
            method: 'PUT',
            data: formData,
            enctype: 'multipart/form-data',
            processData: false, // - processData : false로 선언 시 formData를 string으로 변환하지 않음
            contentType: false, // - contentType : false 로 선언 시 content-type 헤더가 multipart/form-data로 전송되게 함
            async: false,
        })
        .fail(function (error) {
            console.log("파일 업데이트 실패")
            console.log(error)
        })
    }
})

