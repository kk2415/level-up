import httpRequest from "/js/module/httpRequest.js";

let request = new httpRequest()

export default class Notice {

    loadNoticeList (channelId, page) {
        // return request.getRequest('/api/channel-notices?channel=' + channelId + '&page=' + page)
    }

    createNotice(data, channelId) {
        // request.postRequest('/api/channel-notice?channel=' + channelId , data, (data) => {
        //     $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
        // })
    }

    updateNotice(data, channelNoticeId ,channelId) {
        // request.patchRequest('/api/channel-notice/' + channelNoticeId + '?channel=' + channelId , data,
        //     (data) => {
        //     $(location).attr('href', '/channel/detail/' + channelId + '?page=1')
        // })
    }

}