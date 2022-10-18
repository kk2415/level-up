import {send, sendMultiPart} from "../../Request"
import {HttpMethod} from "../../const/HttpMethod";

const urlPrefix = '/api/v1/sign-up'

export const SignUpService = {

    signUp : async (member, file) => {
        let result = null
        const url = urlPrefix

        let form = new FormData();
        form.append('request', new Blob([JSON.stringify(member)], { type: "application/json" }))
        form.append('profileImage', file)

        await sendMultiPart(HttpMethod.POST, url, form)
            .then((data) => {
                result = data
            })
            .catch((error) => {
                console.log(error)
                alert(error.responseJSON.message)
            })

        return result
    },
}