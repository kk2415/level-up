import {send} from "../request"
import {TOKEN} from '../token'
import {uploadFile} from "../UploadFile";

export const MemberService = {
    signUp : async function (member) {
        let result = null

        await send('/api/v1/sign-up', 'POST', member)
            .then((data) => {
                result = data
            })
            .catch((error) => {
                console.log(error)
                alert(error.responseJSON.message)
            })

        return result
    },

    signIn : async function (member) {
        let result = false

        await send('/api/v1/login', 'POST', member)
            .then((data) => {
                result = true
                localStorage.setItem(TOKEN, JSON.stringify(data.accessToken))
                localStorage.setItem('email', data.email)
                localStorage.setItem('id', data.id)
                localStorage.setItem('isAdmin', data.isAdmin)
            })
            .catch((error) => {
                console.log(error)
                alert("이메일 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.")
            })

        return result
    },

    signOut : function () {
        localStorage.clear()
        window.location.href = "/"
    },

    get : async function getMember(memberId) {
        let member = {}

        await send('/api/v1/members/' + memberId, 'GET')
            .then((data) => {
                member = data
            })
            .catch((error) => {
                member = null
                console.log(error)
            })

        return member
    },

    modify: async function modifyMember(memberId, member) {
        let result = false

        await send('/api/v1/members/' + memberId, 'PATCH', member)
            .then((data) => {
                result = true
            })
            .catch((error) => {
                console.log(error)
            })

        return result
    },

    modifyPassword: async function modifyMember(request, email) {
        let result = false

        await send('/api/v1/members/' + email + '/password', 'PATCH', request)
            .then(() => {
                result = true
            })
            .catch((error) => {
                console.log(error)
                alert(error.responseJSON.message)
                alert(error)
            })

        return result
    },

    delete : async function deleteMember(memberId) {
        let members = {}

        await send('/api/v1/members/' + memberId, 'DELETE')
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

    uploadProfile : async (thumbnail) => {
        return await uploadFile('/api/v1/members/profile', 'POST', thumbnail)
    },

    modifyProfile: async (id, profile) => {
        return await uploadFile('/api/v1/members/' + id + '/profile', 'PATCH', profile)
    },
}