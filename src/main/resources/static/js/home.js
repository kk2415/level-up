$(function () {
    $.ajax({
        url: '/api/channels',
        method: 'GET'
    })
    .done(function (json) {
        createCards(json);
    })

    // $('#createChannelButton').
})

// fetch('/api/channels')
//     .then(function (response) {
//         return response.json();
//     })
//     .then(function (channels) {
//         createCards(channels);
//     });

function createCards(channels) {
    let count = channels.count;
    let data = channels.data;

    let channelRow = $('#channelRow');
    let channelCol = $('#channelCol');
    // let channelRow = document.querySelector("div[id=channelRow]");
    // let baseColunm = document.querySelector("div[id=channelRow] .col");

    for (let i = 0; i < count; i++) {
        let colunmNode = channelCol.clone();

        if (i == 0) {
            colunmNode = channelCol;
        }
        colunmNode.id = i;

        colunmNode.children().children('.card-body').children('.card-title').text(data[i].name)
        colunmNode.children().children('.card-body').children('.card-text').text(data[i].description)
        colunmNode.children().children('.card-body').children('#channelConnetion').attr('href', '/channel/detail/' + data[i].id)
        colunmNode.children().children('.card-footer').text(data[i].memberCount + " / " + data[i].limitedMemberNumber)

        colunmNode.children()
            .children('#thumbnail')
            .attr('src', 'api/channel/' + data[i].id + '/thumbnail')

        channelRow.append(colunmNode)
    }
}