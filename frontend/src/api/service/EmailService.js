import { send } from "../request"
import {TOKEN} from '../token'

export const EmailService = {
        sendSecurityCode : async function (memberId) {
        let result = true

        await send('/api/v1/email?member=' + memberId, 'POST')
            .then(() => {
            })
            .catch((error) => {
                result = false
                console.log(error)
            })

        return result
    },

    confirmEmail : async function confirmEmail(memberId, auth) {
        let members = {}

        await send('/api/v1/email?member=' + memberId, 'PATCH', auth)
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
}