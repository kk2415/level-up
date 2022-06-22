import { send } from "../request"

const ArticleService = {

    create: async (article) => {
        await send('/api/article', 'POST', article)
            .then((data) => {
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

        await send('/api/article/' + articleId + '?view=true')
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

        let url = '/api/articles?articleType=' + articleType + '&' + pageable;
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

        await send('/api/article/' + articleId + '/nextArticle?articleType=' + articleType)
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

        await send('/api/article/' + articleId + '/prevArticle?articleType=' + articleType)
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

    modify: async (article, articleId) => {
        await send('/api/article/' + articleId, 'PATCH', article)
            .then(() => {
                alert('수정되었습니다.')
            })
            .catch((error) => {
                console.log(error)
            })
    },

    delete: async (articleId) => {
        await send('/api/article/' + articleId, 'DELETE')
            .then(() => {
                alert('삭제되었습니다.')
            })
            .catch((error) => {
                console.log(error)
            })
    },
}


export default ArticleService;