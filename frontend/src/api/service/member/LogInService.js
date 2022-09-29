import {send} from "../../Request"
import {UserInfo} from '../../const/UserInfo'
import {HttpMethod} from "../../const/HttpMethod";

const urlPrefix = '/api/v1/login'

export const LogInService = {
    signIn : async function (member) {
        let result = false

        await send(HttpMethod.POST, urlPrefix, member)
            .then((data) => {
                result = true
                localStorage.setItem(UserInfo.TOKEN, JSON.stringify(data.accessToken))
                localStorage.setItem(UserInfo.ID, data.id)
                localStorage.setItem(UserInfo.EMAIL, data.email)
                localStorage.setItem(UserInfo.IS_ADMIN, data.isAdmin)
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
}