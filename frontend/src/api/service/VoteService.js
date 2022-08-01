import { send } from "../request"

const VoteService = {

    create: async (vote) => {
        let result = null

        if (vote.memberId === null) {
            alert('로그인해야합니다.')
            return
        }

        await send('/api/v1/vote', 'POST', vote)
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