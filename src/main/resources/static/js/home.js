import httpRequest from '/js/module/httpRequest.js';

let request = new httpRequest()

$(function () {
    loadStudyChannels()
    loadProjectChannels()

    function loadStudyChannels() {
        request.getRequest('/api/channels/' + 'STUDY', createStudyChannels)
    }

    function loadProjectChannels() {
        request.getRequest('/api/channels/' + 'PROJECT', createProjectChannels)
    }
})

$('#createStudyButton').click(function () {
    let result = request.getRequest('/channel/study/create')

    if (result == null) {
        alert("스터디 생성을 위해선 로그인해야 합니다.")
    }
})

$('#createProjectButton').click(function () {
    let result = request.getRequest('/channel/project/create')

    if (result == null) {
        alert("프로젝트 생성을 위해선 로그인해야 합니다.")
    }
})

function createProjectChannels(data) {
    let projectChannelCount = data.count;
    let channels = data.data;

    let channelRow = $('#project-swiper-wrapper');
    $('#origin-slide').css('display', '')
    let channelCol = $('#origin-slide');

    for (let i = 0; i < projectChannelCount; i++) {
        let colunmNode = channelCol.clone();
        colunmNode.attr('id', 'projectSlider' + i)

        let title = colunmNode.children().children()
            .children('.col-md-8')
            .children('.card-body')
            .children('.card-title');
        let thumbnailDescription = colunmNode.children().children()
            .children('.col-md-8')
            .children('.card-body')
            .children('.card-text')
        let footer = colunmNode.children().children().children('.col-md-8').children('.card-footer')
        let thumbnail = colunmNode.children().children().children('.col-md-4').children()

        title.children('a').text(channels[i].name)
        title.children('a').attr('href', '/channel/detail-description/' + channels[i].id)

        thumbnailDescription.text(channels[i].thumbnailDescription)
        footer.children('.text-muted').text(channels[i].memberCount + " / " + channels[i].limitedMemberNumber)
        thumbnail.attr('src', 'api/channel/' + channels[i].id + '/thumbnail')

        channelRow.append(colunmNode)
    }
    $('#origin-slide').css('display', 'none')
}

function createStudyChannels(data) {
    let studyChannelCount = data.count;
    let channels = data.data;

    let channelRow = $('#study-swiper-wrapper');
    let channelCol = $('#origin-slide');

    for (let i = 0; i < studyChannelCount; i++) {
        let colunmNode = channelCol.clone();
        colunmNode.attr('id', 'studySlider' + i)

        let title = colunmNode.children().children()
            .children('.col-md-8')
            .children('.card-body')
            .children('.card-title')
        let thumbNailDescription = colunmNode.children().children()
            .children('.col-md-8')
            .children('.card-body')
            .children('.card-text')
        let footer = colunmNode.children().children().children('.col-md-8').children('.card-footer')
        let thumbnail = colunmNode.children().children().children('.col-md-4').children()

        title.children('a').text(channels[i].name)
        title.children('a').attr('href', '/channel/detail-description/' + channels[i].id)

        thumbNailDescription.text(channels[i].thumbnailDescription)
        footer.children('.text-muted').text(channels[i].memberCount + " / " + channels[i].limitedMemberNumber)
        thumbnail.attr('src', 'api/channel/' + channels[i].id + '/thumbnail')

        channelRow.append(colunmNode)
    }
    $('#origin-slide').css('display', 'none')
}