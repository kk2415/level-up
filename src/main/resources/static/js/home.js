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
    let channelRow = document.querySelector("div[id=channelRow]");
    let baseColunm = document.querySelector("div[id=channelRow] .col");

    for (let i = 0; i < count; i++) {
        let colunmNode = baseColunm.cloneNode(true);

        if (i == 0) {
            colunmNode = baseColunm;
        }

        colunmNode.id = i;
        colunmNode.querySelector(".card-title").innerHTML = data[i].name;
        colunmNode.querySelector(".card-text").innerHTML = data[i].descript;
        colunmNode.querySelector(".card-footer").innerHTML = data[i].memberCount + " / " + data[i].limitedMemberNumber;

        channelRow.appendChild(colunmNode);
    }
}