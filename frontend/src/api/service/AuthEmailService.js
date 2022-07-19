import { send } from "../request"
import {TOKEN} from '../token'

export const AuthEmailService = {
    sendSecurityCode : async function () {
        let result = true

        await send('/api/send/auth-email', 'GET')
            .then(() => {
            })
            .catch((error) => {
                result = false
                console.log(error)
            })

        return result
    },
}