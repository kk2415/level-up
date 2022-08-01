import {send} from "../request"
import {uploadFile} from "../UploadFile";


const ChannelService = {

    create: async (channel) => {
        let reuslt = false

        await send('/api/v1/channel', 'POST', channel)
            .then((data) => {
                alert('채널을 만들었습니다.')
                reuslt = true
                window.location.href = '/'
            })
            .catch((error) => {
                if (error.status === 403) {
                    alert('이메일 인증을 해야합니다.')
                }
                console.log(error)
            })

        return reuslt
    },

    createNotice: async (notice, channelId) => {
        await send('/api/v1/channel-notice?channel=' + channelId, 'POST', notice)
            .then(() => {
                // window.location.href = '/channel/' + channelId + '?page=1'
            })
            .catch((error) => {
                console.log(error)
            })
    },

    get: async (channelId) => {
        let result = {}

        await send('/api/v1/channel/' + channelId, 'GET')
            .then((data) => {
                result = data;
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    getNotice: async (channelNoticeId) => {
        let result = {}

        await send('/api/v1/channel-notice/' + channelNoticeId + '?view=true', 'GET')
            .then((data) => {
                result = data;
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    getNextNotice: async (channelNoticeId) => {
        let result = {}

        await send('/api/v1/channel-notice/' + channelNoticeId + '/nextPost', 'GET')
            .then((data) => {
                result = data;
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    getPrevNotice: async (channelNoticeId) => {
        let result = {}

        await send('/api/v1/channel-notice/' + channelNoticeId + '/prevPost', 'GET')
            .then((data) => {
                result = data;
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    getAllNotice: async (channelId, page) => {
        let result = {}

        await send('/api/v1/channel-notices?channel=' + channelId + '&page=' + page, 'GET')
            .then((data) => {
                result = data;
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    modify: async (channel, channelId) => {
        let result = {}

        await send('/api/v1/channel/' + channelId, 'PATCH', channel)
            .then((data) => {
                alert('수정되었습니다..')
                window.location.href = '/channel/description/' + channelId
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    modifyNotice: async (notice, channelId, channelNoticeId) => {
        let result = {}
        console.log(channelNoticeId)

        await send('/api/v1/channel-notice/' + channelNoticeId + '?channel=' + channelId, 'PATCH', notice)
            .then((data) => {
                alert('수정되었습니다..')
                window.location.href = '/channel/' + channelId + '?page=1'
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    delete: async (channelId) => {
        let result = {}

        await send('/api/v1/channel/' + channelId, 'DELETE')
            .then((data) => {
                alert('삭제되었습니다.')
                window.location.href = '/'
            })
            .catch((error) => {
                result = null
                alert('삭제 실패')
                console.log(error)
            })

        return result;
    },

    deleteNotice: async (channelId, channelNoticeId) => {
        let result = {}

        await send('/api/v1/channel-notice/' + channelNoticeId + '?channel=' + channelId, 'DELETE')
            .then((data) => {
                alert('삭제되었습니다.')
                window.location.href = '/channel/' + channelId + '?page=1'
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    getDescription: async (channelId) => {
        let result = {}

        await send('/api/v1/channel/' + channelId + '/description', 'GET')
            .then((data) => {
                result = data;
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    getManager: async (channelId) => {
        let result = {}

        await send('/api/v1/channel/' + channelId + '/manager', 'GET')
            .then((data) => {
                result = data
            })
            .catch((error) => {
                console.log(error)
                result = null
            })

        return result
    },

    uploadThumbnail: async (thumbnail) => {
        return await uploadFile('/api/v1/channel/thumbnail', 'POST', thumbnail)
    },
}


export default ChannelService;
