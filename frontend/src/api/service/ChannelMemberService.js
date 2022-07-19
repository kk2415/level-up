import { send } from "../request"

const ChannelMemberService = {

    create: async (channelId) => {
        let result = false
        let url = '/api/channel-members?channelId=' + channelId

        await send(url, 'POST')
            .then(() => {
                result = true
            })
            .catch((error) => {
                console.log(error)
                alert(error.responseJSON.message)
            })

        return result;
    },

    getAll: async (channelId, isWaitingMember, pageable) => {
        let result = null
        let url = '/api/channel-members?channelId=' + channelId + '&isWaitingMember=' + isWaitingMember + '&' + pageable;

        await send(url, 'GET')
            .then((data) => {
                result = data
            })
            .catch((error) => {
                console.log(error)
            })

        return result;
    },

    approval: async (channelMemberId) => {
        let result = false
        let url = '/api/channel-members/' + channelMemberId

        await send(url, 'PATCH')
            .then(() => {
                result = true
            })
            .catch((error) => {
                console.log(error)
            })

        return result;
    },

    delete: async (channelMemberId, channelId) => {
        let result = false
        let url = '/api/channel-members/' + channelMemberId + '?channelId=' + channelId;

        await send(url, 'DELETE')
            .then(() => {
                result = true
            })
            .catch((error) => {
                console.log(error)
            })

        return result;
    },
}


export default ChannelMemberService;