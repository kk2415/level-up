import httpRequest from "/js/module/httpRequest.js";

$(function () {
    let request = new httpRequest()

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
        $(location).attr('href', '/')
    })

    function renderMemberInfo() {
        let result = request.getRequest('/api/member');

        if ('status' in result && result.status !== 200) {
            alert("로그인이 필요합니다")
            $(location).attr('href', '/')
        }
        else {
            console.log(result)
            member.email = result.email;

            $('#email').val(result.email);
            $('#name').val(result.name);
            $('#birthday').val(result.birthday);
            $('#phone').val(result.phone);
            if (result.gender.toString() == 'MALE') {
                $('#gender').val('남성');
            } else {
                $('#gender').val('여성');
            }
            $('#profile').attr('src', result.uploadFile.storeFileName)
        }
    }

    function updateMemberImage() {
        let formData = new FormData();
        formData.append('file', inputFile[0].files[0])

        request.patchMultipartRequest('/api/member/' + member.email + '/image', formData)
    }

})

