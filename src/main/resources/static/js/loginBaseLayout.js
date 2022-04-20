fetch('/html/base/loginNavigation.html')
    .then(function (response) {
        return response.text();
    })
    .then(function (data) {
        document.querySelector("#mainNav").innerHTML = data;
    })

fetch('/html/base/header.html')
    .then(function (response) {
        return response.text();
    })
    .then(function (data) {
        document.querySelector(".masthead").innerHTML = data;
    })

fetch('/html/base/footer.html')
    .then(function (response) {
        return response.text();
    })
    .then(function (data) {
        document.querySelector(".footer").innerHTML = data;
    })