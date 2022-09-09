import { send } from "../request"

export const EmailService = {
    sendSecurityCode : async (emailAuthRequest, email) => {
        let result = false

        await send('/api/v1/email-auth?email=' + email, 'POST', emailAuthRequest)
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

        await send('/api/v1/email-auth?email=' + email, 'PATCH', emailAuthRequest)
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