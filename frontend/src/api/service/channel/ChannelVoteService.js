import { send } from "../../Request"
import {HttpMethod} from "../../const/HttpMethod";

const urlPrefix = '/api/v1/channel/votes/'

const ChannelVoteService = {

    create: async (vote) => {
        let result = null
        const url = urlPrefix

        await send(HttpMethod.POST, url, vote)
            .then((data) => {
                result = data
            })
            .catch((error) => {
                console.log(error)
                alert(error.responseJSON.message)
            })

        return result
    },
}

export default ChannelVoteService;