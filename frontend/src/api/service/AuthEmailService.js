import { send } from "../request"
import {TOKEN} from '../token'

export const AuthEmailService = {
    sendSecurityCode : async function () {
        await send('/api/send/auth-email', 'GET')
            .then((data) => {
            })
            .catch((error) => {
                console.log(error)
            })
    },
}