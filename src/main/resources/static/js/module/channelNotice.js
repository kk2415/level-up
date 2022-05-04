import httpRequest from "/js/module/httpRequest.js";

let request = new httpRequest()

export default class ChannelNotice {

    loadChannelNoticeList (channelId, page) {
        return request.getRequest('/api/channel-notices?channel=' + channelId + '&page=' + page)
    }

    createChannelNotice(data, channelId) {
        request.postRequest('/api/channel-notice?channel=' + channelId , data, (data) => {
            $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
        })
    }

    updateChannelNotice(data, channelNoticeId ,channelId) {
        request.patchRequest('/api/channel-notice/' + channelNoticeId + '?channel=' + channelId , data,
            (data) => {
            $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
        })
    }

}