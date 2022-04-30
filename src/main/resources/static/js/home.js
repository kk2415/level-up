$(function () {
    loadStudyChannels()
    loadProjectChannels()

    function loadStudyChannels() {
        $.ajax({
            url: '/api/channels/' + 'STUDY',
            method: 'GET',
            async: false,
        })
        .done(function (json) {
            createStudyChannels(json);
        })
        .fail(function (error) {
            console.log(error)
        })
    }

    function loadProjectChannels() {
        $.ajax({
            url: '/api/channels/' + 'PROJECT',
            method: 'GET',
            async: false,
        })
        .done(function (json) {
            createProjectChannels(json);
        })
        .fail(function (error) {
            console.log(error)
        })
    }
})

$('#createStudyButton').click(function () {
    $(location).attr('href', '/channel/study/create')
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
        let description = colunmNode.children().children()
            .children('.col-md-8')
            .children('.card-body')
            .children('.card-text')
        let footer = colunmNode.children().children().children('.col-md-8').children('.card-footer')
        let thumbnail = colunmNode.children().children().children('.col-md-4').children()

        title.children('a').text(channels[i].name)
        title.children('a').attr('href', '/channel/detail/' + channels[i].id + '?page=1')

        description.text(channels[i].description)
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
        title.children('a').attr('href', '/channel/detail/' + channels[i].id + '?page=1')

        thumbNailDescription.text(channels[i].thumbnailDescription)
        footer.children('.text-muted').text(channels[i].memberCount + " / " + channels[i].limitedMemberNumber)
        thumbnail.attr('src', 'api/channel/' + channels[i].id + '/thumbnail')

        channelRow.append(colunmNode)
    }
    $('#origin-slide').css('display', 'none')
}