import { send } from "../request"
import {TOKEN} from '../token'

export const MemberService = {
    signUp : async function (member) {
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

    signIn : async function (member) {
        let result = false

        await send('/login', 'POST', member)
            .then((data) => {
                result = true
                sessionStorage.setItem(TOKEN, data.token)
                sessionStorage.setItem('email', data.email)
                sessionStorage.setItem('id', data.id)
                sessionStorage.setItem('role', data.authority)

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

    signOut : function () {
        sessionStorage.clear()
        window.location.href = "/"
    },

    get : async function getMember() {
        let member = {}

        await send('/api/member', 'GET')
            .then((data) => {
                member = data
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

    confirmEmail : async function confirmEmail(auth) {
        let members = {}

        await send('/api/confirm-email', 'POST', auth)
            .then((data) => {
                members = data
            })
            .catch((error) => {
                members = null

                console.log(error)
                alert(error.responseJSON.message)
            })

        return members
    },

    delete : async function deleteMember(memberId) {
        let members = {}

        await send('/api/member/' + memberId, 'DELETE')
            .then((data) => {
                members = data
            })
            .catch((error) => {
                members = null
                console.log(error)
                alert(error)
            })

        return members
    },


}