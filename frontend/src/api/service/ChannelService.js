import { send } from "../request"


const ChannelService = {

    create: async (channel) => {
        let reuslt = false

        await send('/api/channel', 'POST', channel)
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
        await send('/api/channel-notice?channel=' + channelId, 'POST', notice)
            .then(() => {
                // window.location.href = '/channel/' + channelId + '?page=1'
            })
            .catch((error) => {
                console.log(error)
            })
    },

    get: async (channelId) => {
        let result = {}

        await send('/api/channel/' + channelId, 'GET')
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

        await send('/api/channel-notice/' + channelNoticeId + '?view=true', 'GET')
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

        await send('/api/channel-notice/' + channelNoticeId + '/nextPost', 'GET')
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

        await send('/api/channel-notice/' + channelNoticeId + '/prevPost', 'GET')
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

        await send('/api/channel-notices?channel=' + channelId + '&page=' + page, 'GET')
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

        await send('/api/channel/' + channelId, 'PATCH', channel)
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

        await send('/api/channel-notice/' + channelNoticeId + '?channel=' + channelId, 'PATCH', notice)
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

        await send('/api/channel/' + channelId, 'DELETE')
            .then((data) => {
                alert('삭제되었습니다.')
                window.location.href = '/'
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    deleteNotice: async (channelId, channelNoticeId) => {
        let result = {}

        await send('/api/channel-notice/' + channelNoticeId + '?channel=' + channelId, 'DELETE')
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

        await send('/api/channel/' + channelId + '/description', 'GET')
            .then((data) => {
                result = data;
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    addWaitingMember: async (channelId) => {
        await send('/api/channel/' + channelId + '/waiting-member', 'POST')
            .then(() => {
                alert('신청되었습니다. 매니저가 수락할 때 까지 기다려주세요.')
            })
            .catch((error) => {
                console.log(error)
                alert(error.responseJSON.message)
            })
    },

    getManager: async (channelId) => {
        let result = {}

        await send('/api/channel/' + channelId + '/manager', 'GET')
            .then((data) => {
                result = data
            })
            .catch((error) => {
                console.log(error)
                result = null
            })

        return result
    },

}


export default ChannelService;
