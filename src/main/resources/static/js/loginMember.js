let loginForm = {
};

let alertForm = document.getElementById('alert2');
alertForm.style.display = "none";
let unmatchedLoginInfoMsg = "";

setEventListenerOfInput();

let form = document.getElementById('loginForm');
// form.onsubmit = validation;
// form.addEventListener('submit', requestPost);

document.getElementById('loginButton').addEventListener('click', onLoginButton);

function onLoginButton() {
    if (validation()) {
        requestPost();
    }
}

function validation() {
    let bool = true;

    if (!form.email.value) {
        bool = false;
        alertForm.style.marginTop = "10px"
        alertForm.style.display = "block";
        alertForm.innerText = "아이디를 입력하세요";
        form.email.focus();
    }
    else if (!form.password.value) {
        bool = false;
        alertForm.style.marginTop = "10px"
        alertForm.style.display = "block";
        alertForm.innerText = "비밀번호를 입력하세요";
        form.password.focus();
    }
    return bool;
}

async function requestPost() {
    const response = await fetch('/api/member/login', {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginForm)
    })

    if (response.ok) {
        window.location.href='http://localhost:8080/';
    }
    else {
        alertForm.style.marginTop = "10px"
        alertForm.style.display = "block";
        alertForm.innerText = "이메일 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.";
    }
}


// 데이터 전송, 페이지 전환 방지
function handleSubmit(event) {
    event.preventDefault()
}

function setEventListenerOfInput() {
    document.getElementById('email').addEventListener('change', event => {
        loginForm.email = event.target.value;
    })

    document.getElementById('password').addEventListener('change', event => {
        loginForm.password = event.target.value;
    })
}