import httpRequest from '/js/module/httpRequest.js';

let request = new httpRequest()
let url = getUrl()

$(function () {
    setEventHandler()
    $('#register').attr('href', '/member/create')
    $('#myPage').attr('href', '/member/myPage')
    $('#login').attr('href', '/member/login?redirectURL=' + url)
    $('#logout').attr('href', '/member/logout')
    $('#notice').attr('href', '/notice?page=1')

    $('#study').click(function () {
    })
    $('#project').click(function () {
    })
    $('#QNA').click(function () {
    })
})

function moveTo(url) {
    let result = request.getRequest(url)

    if (result == null) {
        alert("로그인해야 합니다.")
    }
}

function setEventHandler() {
    $('#register').click(function () {
        moveTo('/member/create')
    })
    $('#myPage').click(function () {
        moveTo('/member/myPage')
    })
    $('#login').click(function () {
        moveTo('/member/login?redirectURL=' + url)
    })
    $('#logout').click(function () {
        moveTo('/member/logout')
    })
    $('#notice').click(function () {
        moveTo('/notice?page=1')
    })
    $('#study').click(function () {
    })
    $('#project').click(function () {
    })
    $('#QNA').click(function () {
    })
}




function getUrl() {
    let pathname = decodeURI($(location).attr('pathname'))
    let search = decodeURI($(location).attr('search'))

    return pathname + search
}