$(function () {
    $.ajax({
        url: '/api/member',
        method: 'GET',
    })
    .done(function () {
        // $('#login').text('로그아웃');
        // $('#login').attr('href', '/member/logout');
        // $('#navUl').append('<li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded" href="/member/myPage">마이페이지</a></li>');
    })
})

//
// function setNavigation(uri) {
//     fetch(uri)
//         .then(function (response) {
//             return response.text();
//         })
//         .then(function (data) {
//             document.querySelector("#mainNav").innerHTML = data;
//         })
// }
//
// fetch('/html/base/header.html')
//     .then(function (response) {
//         return response.text();
//     })
//     .then(function (data) {
//         document.querySelector(".masthead").innerHTML = data;
//     })

// fetch('/html/base/footer.html')
//     .then(function (response) {
//         return response.text();
//     })
//     .then(function (data) {
//         document.querySelector(".footer").innerHTML = data;
//     })