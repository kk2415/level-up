import {send} from "../../Request"
import {HttpMethod} from "../../const/HttpMethod";

const urlPrefix = '/api/v1/sign-up'

export const SignUpService = {
    signUp : async function (member) {
        let result = null
        const url = urlPrefix

        await send(HttpMethod.POST, url, member)
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