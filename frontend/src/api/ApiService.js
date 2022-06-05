import { send } from "./request"
import {TOKEN} from './token'


export const MemberService = {
    signUp: async function signUp(member) {
        let result = false

        await send('/api/member', 'POST', member)
            .then((data) => {
                alert(data.name + '님 가입되었습니다')
                result = true
            })
            .catch((error) => {
                alert('회원가입 실패')
                console.log(error)
            })

        return result
    },

    signIn: async function signIn(member) {
        let result = false

        await send('/login', 'POST', member)
            .then((data) => {
                result = true
                sessionStorage.setItem(TOKEN, data.token)
                sessionStorage.setItem('email', data.email)
                sessionStorage.setItem('id', data.id)

                console.log(data.email)
                console.log(data.id)
                console.log(sessionStorage.getItem('id'))
            })
            .catch((error) => {
                console.log(error)
                alert("이메일 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.")
            })

        return result
    },

    signOut: function signOut() {
        sessionStorage.setItem(TOKEN, null)
        alert('로그아웃되었습니다.')
        window.location.href = "/"
    },

    get : async function getMember() {
        let member = {}

        await send('/api/member', 'GET')
            .then((data) => {
                member = data
                console.log(data)
                console.log(member)
            })
            .catch((error) => {
                member = null
                console.log(error)
            })

        return member
    },

    getWaitingMembers : async function getWaitingMembers(channelId, page, count) {
        let members = {}

        await send('/api/channel/' + channelId + '/waiting-members/?page=' + page + '&count=' + count, 'GET')
            .then((data) => {
                members = data
            })
            .catch((error) => {
                members = null
                console.log(error)
            })

        return members
    },
}

export async function signUp(member) {
    let result = false

    await send('/api/member', 'POST', member)
        .then((data) => {
            alert(data.name + '님 가입되었습니다')
            result = true
        })
        .catch((error) => {
            alert('회원가입 실패')
            console.log(error)
        })

    return result
}

export async function signIn(member) {
    let result = false

    await send('/login', 'POST', member)
        .then((data) => {
            result = true
            sessionStorage.setItem(TOKEN, data.token)
            sessionStorage.setItem('email', data.email)
            sessionStorage.setItem('id', data.id)

            console.log(data.email)
            console.log(data.id)
            console.log(sessionStorage.getItem('id'))
        })
        .catch((error) => {
            console.log(error)
            alert("이메일 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.")
        })

    return result
}

export function signOut() {
    sessionStorage.setItem(TOKEN, null)
    alert('로그아웃되었습니다.')
    window.location.href = "/"
}

export async function getMember() {
    let member = {}

    await send('/api/member', 'GET')
        .then((data) => {
            member = data
            console.log(data)
            console.log(member)
        })
        .catch((error) => {
            member = null
            console.log(error)
        })

    return member
}

export async function getWaitingMembers(channelId, page, count) {
    let members = {}

    await send('/api/channel/' + channelId + '/waiting-members/?page=' + page + '&count=' + count, 'GET')
        .then((data) => {
            members = data
        })
        .catch((error) => {
            members = null
            console.log(error)
        })

    return members
}