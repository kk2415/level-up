fetch('/base/loginNavigation.html')
    .then(function (response) {
        return response.text();
    })
    .then(function (data) {
        document.querySelector("#mainNav").innerHTML = data;
    })

fetch('/base/header.html')
    .then(function (response) {
        return response.text();
    })
    .then(function (data) {
        document.querySelector(".masthead").innerHTML = data;
    })

fetch('/base/footer.html')
    .then(function (response) {
        return response.text();
    })
    .then(function (data) {
        document.querySelector(".footer").innerHTML = data;
    })