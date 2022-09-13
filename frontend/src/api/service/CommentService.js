import { send } from "../request"

const CommentService = {

    create: async (comment, memberId) => {
        let result

        await send('/api/v1/comments?member=' + memberId, 'POST', comment)
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

    createReply: async (reply, memberId) => {
        let result

        await send('/api/v1/comments/reply?member=' + memberId, 'POST', reply)
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

        await send('/api/v1/comments/' + articleId, 'GET')
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

        await send('/api/v1/comments/' + commentId + '/reply', 'GET')
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

        await send('/api/v1/comments/' + commentId, 'DELETE')
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