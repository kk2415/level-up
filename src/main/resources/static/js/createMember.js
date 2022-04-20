let member = {
    email : "",
    password : "",
    name : "",
    gender : "",
    birthday : "",
    phone : "",
};

let validationMessage = "";

let alertForm = document.getElementById('alert');
alertForm.style.display = "none";

setEventListenerOfInput();

let form = document.getElementById('contactForm');
form.onsubmit = validation;
form.addEventListener('submit', requestPost);

// let button = document.getElementById('submitButton');
// button.addEventListener('click', requestPost);

let reg_name = /^[가-힣]{2,15}$/;
let reg_phone = /^\d{3}-\d{3,4}-\d{4}$/;
let reg_email =/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
let reg_password = /^[a-z0-9_-]{6,18}$/;
let reg_birthday = /^[0-9]{6}$/;

function validation() {
    let bool = true;

    if (!reg_email.test(member.email)) {
        validationMessage += "[이매일] : 유효한 이메일 형식이 아닙니다\n";
        bool = false;
    }
    if (!reg_name.test(member.name)) {
        validationMessage += "[이름] : 이름은 2자리 이상 15이하, 한글만 입력하세요\n";
        bool = false;
    }
    if (!reg_phone.test(member.phone)) {
        validationMessage += "[휴대폰번호] : 유효한 형식이 아닙니다.\n";
        bool = false;
    }
    if (!reg_password.test(member.password)) {
        validationMessage += "[패스워드] : 비밀번호는 6자리이상, 영문자/숫자만 및 하이푼(_-)만 입력하세요\n";
        bool = false;
    }
    if (!reg_birthday.test(member.birthday)) {
        validationMessage += "[생년월일] : 생년월일은 6자리 이상 입력해주세요";
        bool = false;
    }

    if (bool === false) {
        alertForm.style.display = "block";
        alertForm.innerText = validationMessage;
        // alertForm.innerText = JSON.stringify(validationMessage);
    }
    validationMessage = "";
    return bool;
}

async function requestPost() {
    const response = await fetch('/api/member', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(member)
        })

    if (response.ok) {
        let promise = await response.json();
        await promise.then(function (data) {
            alert(JSON.stringify(data));
        })
    }
}

function setEventListenerOfInput() {
    document.getElementById('name').addEventListener('change', event => {
        member.name = event.target.value;
    })

    document.getElementById('email').addEventListener('change', event => {
        member.email = event.target.value;
    })

    document.getElementById('password').addEventListener('change', event => {
        member.password = event.target.value;
    })

    document.getElementById('gender1').addEventListener('click', event => {
        member.gender = event.target.value;
    })

    document.getElementById('gender2').addEventListener('click', event => {
        member.gender = event.target.value;
    })

    document.getElementById('birthday').addEventListener('change', event => {
        member.birthday = event.target.value;
    })

    document.getElementById('phone').addEventListener('change', event => {
        member.phone = event.target.value;
    })
}