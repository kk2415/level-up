import { send } from "./request"

const CommentService = {

    create: async (vote) => {
        let result

        await send('/api/vote', 'POST', vote)
            .then((data) => {
                result = data
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result
    },

    get: async () => {
        let comment = {}

        await send('', 'GET')
            .then((data) => {
                comment = data
            })
            .catch((error) => {
                console.log(error)
                comment = null
            })

        return comment
    },

    modify: async () => {
        await send()
            .then(() => {
            })
            .catch((error) => {
            })
    },

    delete: async () => {
        await send()
            .then(() => {
            })
            .catch((error) => {
            })
    },

}


export default CommentService;