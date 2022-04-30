export default class httpRequest {

    getRequest(url, callback) {
        let result = {}

        $.ajax({
            url: url,
            method: "GET",
            async: false,
        })
        .done(function (response) {
            if (callback !== undefined) {
                callback(response)
            }
            result = response
        })
        .fail(function (error) {
            result = error
        })
        return result
    }

    postRequest(url, requestData, callback) {
        let result = {}

        $.ajax({
            url: url,
            method: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(requestData),
            async: false,
        })
        .done(function (response) {
            if (callback !== undefined) {
                callback(response)
            }
            result = response
        })
        .fail(function (error) {
            result = error
        })
        return result
    }

    postMultipartRequest(url, file, callback) {
        let result = {}

        $.ajax({
            url: url,
            method: 'POST',
            data: file,
            processData: false, // - processData : false로 선언 시 formData를 string으로 변환하지 않음
            contentType: false, // - contentType : false 로 선언 시 content-type 헤더가 multipart/form-data로 전송되게 함
            cache: false,
            enctype: 'multipart/form-data',
            async: false,
        })
        .done(function (response) {
            if (callback !== undefined) {
                callback(response)
            }
            result = response
        })
        .fail(function (error) {
            result = error
        })

        return result
    }

}