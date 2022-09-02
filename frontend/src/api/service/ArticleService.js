import { send } from "../request"

const ArticleService = {

    create: async (memberId, article) => {
        let result = null

        await send('/api/v1/articles?member=' + memberId, 'POST', article)
            .then((data) => {
                result = data
            })
            .catch((error) => {
                console.log(error)
                if (error.status === 403) {
                    alert('이메일 인증을 해야합니다.')
                }
            })

        return result
    },

    get: async (articleId) => {
        let post = {}

        await send('/api/v1/articles/' + articleId + '?view=true', 'GET')
            .then((data) => {
                post = data
            })
            .catch((error) => {
                console.log(error)
                post = null
            })

        return post
    },

    getAll: async (articleType, pageable, searchCondition) => {
        let result = {}

        let url = '/api/v1/articles?articleType=' + articleType + '&' + pageable;
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

    getNext: async (articleId, articleType) => {
        let post = {}

        await send('/api/v1/articles/' + articleId + '/next-article?articleType=' + articleType, 'GET')
            .then((data) => {
                post = data
            })
            .catch((error) => {
                console.log(error)
                post = null
            })

        return post
    },

    getPrev: async (articleId, articleType) => {
        let post = {}

        await send('/api/v1/articles/' + articleId + '/prev-article?articleType=' + articleType, 'GET')
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

    modify: async (memberId, article, articleId) => {
        let result = false

        await send('/api/v1/articles/' + articleId + '?member=' + memberId, 'PATCH', article)
            .then(() => {
                alert('수정되었습니다.')
                result = true
            })
            .catch((error) => {
                console.log(error)
            })

        return result
    },

    delete: async (articleId) => {
        let result = false

        await send('/api/v1/articles/' + articleId, 'DELETE')
            .then(() => {
                result = true
                alert('삭제되었습니다.')
            })
            .catch((error) => {
                console.log(error)
            })

        return result
    },
}


export default ArticleService;