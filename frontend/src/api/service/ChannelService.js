import {send} from "../request"
import {uploadFile} from "../UploadFile";


const ChannelService = {

    create: async (channel) => {
        let reuslt = false

        await send('/api/v1/channels', 'POST', channel)
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

    uploadThumbnail: async (thumbnail) => {
        return await uploadFile('/api/v1/channels/thumbnail', 'POST', thumbnail)
    },

    get: async (channelId) => {
        let result = {}

        await send('/api/v1/channels/' + channelId, 'GET')
            .then((data) => {
                result = data;
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    getByCategory: async (category, sort, pageable) => {
        let result = {}

        await send('/api/v1/channels?category=' + category + '&order=' + sort + '&' + pageable, 'GET')
            .then((data) => {
                result = data;
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    getDescription: async (channelId) => {
        let result = {}

        await send('/api/v1/channels/' + channelId + '/description', 'GET')
            .then((data) => {
                result = data;
            })
            .catch((error) => {
                result = null
                console.log(error)
            })

        return result;
    },

    getManager: async (memberId, channelId) => {
        let result = {}

        await send('/api/v1/channels/' + channelId + '/manager?member=' + memberId, 'GET')
            .then((data) => {
                result = data
            })
            .catch((error) => {
                console.log(error)
                result = null
            })

        return result
    },

    modify: async (channel, channelId) => {
        let result = false

        await send('/api/v1/channels/' + channelId, 'PATCH', channel)
            .then(() => {
                result = true
            })
            .catch((error) => {
                console.log(error)
            })

        return result;
    },

    delete: async (channelId) => {
        let result = {}

        await send('/api/v1/channels/' + channelId, 'DELETE')
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
}


export default ChannelService;
