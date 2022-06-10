import { send } from "./request"

const PostService = {

    create: async (post) => {
        await send('/api/post', 'POST', post)
            .then((data) => {
                window.history.back()
            })
            .catch((error) => {
                if (error.status === 401 || error.status === 403) {
                    alert('권한이 없습니다.')
                }
            })
    },

    get: async (postId) => {
        let post = {}

        await send('/api/post/' + postId + '?view=true')
            .then((data) => {
                post = data
            })
            .catch((error) => {
                console.log(error)
                post = null
            })

        return post
    },

    getAll: async (channelId, page, searchCondition) => {
        let result = {}
        let url = '/api/' + channelId + '/posts/' + page

        if (searchCondition !== undefined && searchCondition.field !== undefined) {
            url = '/api/' + channelId + '/posts/' + page +
                '?' + 'field=' + searchCondition.field + '&' + 'query=' + searchCondition.querys
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

    getNext: async (postId) => {
        let post = {}

        await send('/api/post/' + postId + '/nextPost')
            .then((data) => {
                post = data
            })
            .catch((error) => {
                console.log(error)
                post = null
            })

        return post
    },

    getPrev: async (postId) => {
        let post = {}

        await send('/api/post/' + postId + '/prevPost')
            .then((data) => {
                post = data
            })
            .catch((error) => {
                console.log(error)
                post = null
            })

        return post
    },

    count: async (channelId, searchCondition) => {
        let count = 0

        let url = '/api/' + channelId + '/search/count'

        if (searchCondition !== undefined && searchCondition.field !== undefined) {
            url = '/api/' + channelId + '/search/count?field=' + searchCondition.field + '&' + 'query=' + searchCondition.querys
        }

        await send(url, 'GET')
            .then((data) => {
                count = data
            })
            .catch((error) => {console.log(error)})

        return count
    },

    modify: async (post, postId, channelId) => {
        await send('/api/post/' + postId, 'PATCH', post)
            .then(() => {
                alert('수정되었습니다.')
                window.location.href = '/post/' + postId + '?channel=' + channelId
            })
            .catch((error) => {
                console.log(error)
            })
    },

    delete: async (postId, channelId) => {
        await send('/api/post/' + postId, 'DELETE')
            .then(() => {
                alert('삭제되었습니다.')
                window.location.href = '/channel/' + channelId + '?page=1'
            })
            .catch((error) => {
                console.log(error)
            })
    },
}


export default PostService;