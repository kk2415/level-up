import { send } from "../request"

const VoteService = {

    create: async (vote) => {
        let result = null

        await send('/api/vote', 'POST', vote)
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

export default VoteService;