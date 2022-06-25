import { send } from "../request"

const ChannelPostService = {

    create: async (channelPost, channelId) => {
        await send('/api/channel-post?channel=' + channelId, 'POST', channelPost)
            .then(() => {
                window.history.back()
            })
            .catch((error) => {
                console.log(error)

                if (error.status === 403) {
                    alert('이메일 인증을 해야합니다.')
                }
            })
    },

    get: async (articleId) => {
        let post = {}

        await send('/api/channel-post/' + articleId + '?view=true', 'GET')
            .then((data) => {
                post = data
            })
            .catch((error) => {
                console.log(error)
                post = null
            })

        return post
    },

    getAll: async (channelId, articleType, pageable, searchCondition) => {
        let result = {}

        let url = '/api/channel-posts?channel=' + channelId + '&articleType=' + articleType + '&' + pageable;
        if (searchCondition !== undefined && searchCondition.field !== undefined) {
            url += '&field=' + searchCondition.field + '&query=' + searchCondition.querys
        }

        await send(url, 'GET')
            .then((data) => {
                result = data
            })
            .catch((error) => {
                console.log(error)
            })

        return result;
    },

    getNext: async (articleId, articleType, channelId) => {
        let post = {}

        await send('/api/channel-posts/' + articleId + '/nextPost?articleType=' + articleType + '&channel=' + channelId, 'GET')
            .then((data) => {
                post = data
            })
            .catch((error) => {
                console.log(error)
                post = null
            })

        return post
    },

    getPrev: async (articleId, articleType, channelId) => {
        let post = {}

        await send('/api/channel-posts/' + articleId + '/prevPost?articleType=' + articleType + '&channel=' + channelId, 'GET')
            .then((data) => {
                post = data
            })
            .catch((error) => {
                console.log(error)
                post = null
            })

        return post
    },

    count: async () => {

    },

    modify: async (channelPost, articleId, channelId) => {
        let result = null

        await send('/api/channel-posts/' + articleId, 'PATCH', channelPost)
            .then((data) => {
                alert('수정되었습니다.')
                result = data
                window.location.href = '/post/' + articleId + '?channel=' + channelId
            })
            .catch((error) => {
                console.log(error)
            })

        return result
    },

    delete: async (articleId, channelId) => {
        let result = null

        await send('/api/channel-posts/' + articleId, 'DELETE')
            .then((data) => {
                result = data
                alert('삭제되었습니다.')
                window.location.href = '/channel/' + channelId + '?page=1'
            })
            .catch((error) => {
                console.log(error)
            })

        return result
    },
}


export default ChannelPostService;