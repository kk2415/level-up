import {send, sendMultiPart} from "../../Request"
import {uploadFile} from "../../UploadFile";
import {HttpMethod} from "../../const/HttpMethod";

const urlPrefix = '/api/v1/channels/'
const ChannelService = {

    create: async (channel, memberId, file) => {
        let result = false
        const url = urlPrefix + '?member=' + memberId

        let form = new FormData();
        form.append('request', new Blob([JSON.stringify(channel)], { type: "application/json" }))
        form.append('thumbnail', file)

        await sendMultiPart(HttpMethod.POST, url, form)
            .then(() => {
                alert('채널을 만들었습니다.')
                result = true
            })
            .catch((error) => {
                if (error.status === 403) {
                    alert('이메일 인증을 해야합니다.')
                }
                console.log(error)
            })

        return result
    },

    uploadThumbnail: async (thumbnail) => {
        const url = urlPrefix + 'thumbnail'

        return await uploadFile(HttpMethod.POST , url, thumbnail)
    },

    get: async (channelId) => {
        let result = {}
        const url = urlPrefix + channelId

        await send(HttpMethod.GET, url)
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
        const url = urlPrefix + '?category=' + category + '&order=' + sort + '&' + pageable

        await send(HttpMethod.GET, url)
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
        const url = urlPrefix + channelId + '/manager?member=' + memberId

        await send(HttpMethod.GET, url)
            .then((data) => {
                result = data
            })
            .catch((error) => {
                console.log(error)
                result = null
            })

        return result
    },

    modify: async (channel, file, channelId) => {
        let result = false
        const url = urlPrefix + channelId

        let form = new FormData();
        form.append('request', new Blob([JSON.stringify(channel)], { type: "application/json" }))
        form.append('thumbnail', file)

        await sendMultiPart(HttpMethod.PATCH, url, form)
            .then(() => {
                result = true
            })
            .catch((error) => {
                console.log(error)
            })

        return result;
    },

    delete: async (channelId, category) => {
        let result = true
        const url = urlPrefix + channelId + '?category=' + category

        await send(HttpMethod.DELETE, url)
            .then(() => {
                alert('삭제되었습니다.')
            })
            .catch((error) => {
                result = false
                alert('삭제에 실패하였습니다.')
                console.log(error)
            })

        return result;
    },
}

export default ChannelService;
