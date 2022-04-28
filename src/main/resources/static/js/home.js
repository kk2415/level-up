$(function () {
    $.ajax({
        url: '/api/channels',
        method: 'GET'
    })
    .done(function (json) {
        createCards(json);
    })
})

$('#createStudyButton').click(function () {
    $(location).attr('href', '/channel/study/create')
})

function createCards(channels) {
    let count = channels.count;
    let data = channels.data;

    let channelRow = $('.swiper-wrapper');
    let channelCol = $('#origin-slide');

    for (let i = 0; i < count; i++) {
        let colunmNode = channelCol.clone();
        colunmNode.attr('id', 'studySlider' + i)

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

        title.children('a').text(data[i].name)
        title.children('a').attr('href', '/channel/detail/' + data[i].id + '?page=1')

        description.text(data[i].description)
        footer.children('.text-muted').text(data[i].memberCount + " / " + data[i].limitedMemberNumber)
        thumbnail.attr('src', 'api/channel/' + data[i].id + '/thumbnail')

        channelRow.append(colunmNode)
    }
    $('#origin-slide').css('display', 'none')
}