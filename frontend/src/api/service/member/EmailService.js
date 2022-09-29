import { send } from "../../Request"
import {HttpMethod} from "../../const/HttpMethod";

export const EmailService = {
    sendSecurityCode : async (emailAuthRequest, email) => {
        let result = false

        await send(HttpMethod.POST, '/api/v1/email-auth?email=' + email, emailAuthRequest)
            .then(() => {
                result = true
            })
            .catch((error) => {
                console.log(error)
                alert(error.responseJSON.message)
            })

        return result
    },

    confirmEmail : async (emailAuthRequest, email) => {
        let result = false

        await send(HttpMethod.PATCH, '/api/v1/email-auth?email=' + email, emailAuthRequest)
            .then(() => {
                result = true
            })
            .catch((error) => {
                console.log(error)
                alert(error.responseJSON.message)
            })

        return result
    },
}