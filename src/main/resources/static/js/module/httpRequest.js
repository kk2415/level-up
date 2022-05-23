export default class httpRequest {

    constructor() {
        this.ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    getRequest(url, callback) {
        let result = {}
        let headers = {}
        const accessToken = sessionStorage.getItem(this.ACCESS_TOKEN)

        if (accessToken && accessToken !== null) {
            headers.Authorization = "Bearer "+ accessToken
        }

        $.ajax({
            url: url,
            method: "GET",
            headers : headers,
            async: false,
        })
        .done(function (response) {
            if (callback !== undefined) {
                callback(response)
            }
            result = response
        })
        .fail(function (error) {
            console.log(error)
            result = null
        })
        return result
    }

    postRequest(url, requestData, callback) {
        let result = {}
        let headers = {}
        const accessToken = sessionStorage.getItem(this.ACCESS_TOKEN)

        if (accessToken && accessToken !== null) {
            headers.Authorization = "Bearer "+ accessToken
        }

        $.ajax({
            url: url,
            method: 'POST',
            dataType: 'json',
            headers : headers,
            contentType : "application/json",
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
            console.log(error)
            result = null
        })
        return result
    }

    patchRequest(url, requestData, callback) {
        let result = {}
        let headers = {}
        const accessToken = sessionStorage.getItem(this.ACCESS_TOKEN)

        if (accessToken && accessToken !== null) {
            headers.Authorization = "Bearer "+ accessToken
        }

        $.ajax({
            url: url,
            method: 'PATCH',
            dataType: 'json',
            headers : headers,
            contentType : "application/json",
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
                console.log(error)
                result = null
            })
        return result
    }

    postMultipartRequest(url, form, callback) {
        let result = {}
        let headers = {}
        const accessToken = sessionStorage.getItem(this.ACCESS_TOKEN)

        if (accessToken && accessToken !== null) {
            headers.Authorization = "Bearer "+ accessToken
        }

        $.ajax({
            url: url,
            method: 'POST',
            data: form,
            headers : headers,
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
            console.log(error)
            result = null
        })

        return result
    }

    patchMultipartRequest(url, form, callback) {
        let result = {}
        let headers = {}
        const accessToken = sessionStorage.getItem(this.ACCESS_TOKEN)

        if (accessToken && accessToken !== null) {
            headers.Authorization = "Bearer "+ accessToken
        }

        $.ajax({
            url: url,
            method: 'PATCH',
            data: form,
            headers : headers,
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
            console.log(error)
            result = null
        })

        return result
    }

    deleteRequest(url, callback, requestData) {
        let result = {}
        let headers = {}
        const accessToken = sessionStorage.getItem(this.ACCESS_TOKEN)

        if (accessToken && accessToken !== null) {
            headers.Authorization = "Bearer "+ accessToken
        }

        $.ajax({
            url: url,
            method: "DELETE",
            dataType: 'json',
            headers : headers,
            contentType : "application/json",
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
                console.log(error)
                result = null
            })
        return result
    }

    moveTo(url) {
        let result = this.getRequest(url)

        if (result == null) {
            alert("로그인해야 합니다.")
        }
    }
}