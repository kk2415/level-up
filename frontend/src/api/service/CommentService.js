import { send } from "../request"

const CommentService = {

    create: async (comment) => {
        let result

        await send('/api/v1/comment', 'POST', comment)
            .then((data) => {
                result = data
            })
            .catch((error) => {
                result = null
                console.log(error)

                if (error.status === 403) {
                    alert('이메일 인증을 해야합니다.')
                }
            })

        return result
    },

    createReply: async (reply) => {
        let result

        await send('/api/v1/comment/reply', 'POST', reply)
            .then((data) => {
                result = data
            })
            .catch((error) => {
                result = null
                console.log(error)

                if (error.status === 403) {
                    alert('이메일 인증을 해야합니다.')
                }
            })

        return result
    },

    get: async (articleId, identity) => {
        let comment = {}

        await send('/api/v1/comment/' + articleId + '?identity=' + identity, 'GET')
            .then((data) => {
                comment = data
            })
            .catch((error) => {
                console.log(error)
                comment = null
            })

        return comment
    },

    getReply: async (commentId) => {
        let comment = {}

        await send('/api/v1/comment/' + commentId + '/reply', 'GET')
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

    delete: async (commentId) => {
        let result = false

        await send('/api/v1/comment/' + commentId, 'DELETE')
            .then(() => {
                result = true
            })
            .catch((error) => {
                console.log(error)
            })

        return result
    },

}


export default CommentService;