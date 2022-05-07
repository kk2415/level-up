$(function () {
    let url = getUrl()

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

function getUrl() {
    let pathname = decodeURI($(location).attr('pathname'))
    let search = decodeURI($(location).attr('search'))

    return pathname + search
}