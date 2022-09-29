import {send} from "../../Request"
import {uploadFile} from "../../UploadFile";
import {HttpMethod} from "../../const/HttpMethod";

const urlPrefix = '/api/v1/members/'

export const MemberService = {

    signOut : function () {
        localStorage.clear()
        window.location.href = "/"
    },

    get : async function getMember(memberId) {
        let member = {}
        const url = urlPrefix + memberId

        await send(HttpMethod.GET, url)
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
        const url = urlPrefix + memberId

        await send(HttpMethod.PATCH, url, member)
            .then(() => {
                result = true
            })
            .catch((error) => {
                console.log(error)
            })

        return result
    },

    modifyPassword: async function modifyMember(request, email) {
        let result = false
        const url = urlPrefix + email + '/password'

        await send(HttpMethod.PATCH, url, request)
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
        const url = urlPrefix + memberId

        await send(HttpMethod.DELETE, url)
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
        const url = urlPrefix + 'profile'

        return await uploadFile(HttpMethod.POST, url, thumbnail)
    },

    modifyProfile: async (id, profile) => {
        const url = urlPrefix + id + '/profile'

        return await uploadFile(HttpMethod.PATCH, url, profile)
    },
}