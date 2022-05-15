export let createMemberValidation =  {
    name : /^[가-힣]{2,15}$/,
    phone : /^0\\d{3}-\d{3,4}-\d{4}$/,
    email : /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/,
    password : /^[a-zA-Z\\d`~!@#$%^&*()-_=+]{8,24}$/,
    birthday : /^[0-9]{6}$/,
}


export let loginMemberValidation =  {
    email : /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/,
    password : /^[a-zA-Z\\d`~!@#$%^&*()-_=+]{8,24}$/,
}

export let createChannelValidation =  {
    name : /^[가-힣a-zA-Z0-9\s]{2,15}$/,
    limitedMemberNumber : /^[0-9]{1,3}$/,
    thumbnailDescription : /^[ㄱ-ㅎ가-힣a-zA-Z0-9\s]{1,20}$/,
}

