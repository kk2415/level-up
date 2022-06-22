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

    getAll: async (channelId, pageable, searchCondition) => {
        let result = {}

        let url = '/api/channel-posts?channel=' + channelId + '&' + pageable;
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

    getNext: async (articleId, channelId) => {
        let post = {}

        await send('/api/channel-posts/' + articleId + '/nextPost?channel=' + channelId)
            .then((data) => {
                post = data
            })
            .catch((error) => {
                console.log(error)
                post = null
            })

        return post
    },

    getPrev: async (articleId, channelId) => {
        let post = {}

        await send('/api/channel-posts/' + articleId + '/prevPost?channel=' + channelId)
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
        await send('/api/channel-posts/' + articleId, 'PATCH', channelPost)
            .then(() => {
                alert('수정되었습니다.')
                window.location.href = '/post/' + articleId + '?channel=' + channelId
            })
            .catch((error) => {
                console.log(error)
            })
    },

    delete: async (articleId, channelId) => {
        await send('/api/channel-posts/' + articleId, 'DELETE')
            .then(() => {
                alert('삭제되었습니다.')
                window.location.href = '/channel/' + channelId + '?page=1'
            })
            .catch((error) => {
                console.log(error)
            })
    },
}


export default ChannelPostService;