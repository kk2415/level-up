import React, {useState} from 'react';
import {Container} from 'react-bootstrap'
import VoteService from "../../api/VoteService";

const ReplyComment = ({reply}) => {
    const [voteCount, setVoteCount] = useState(reply.voteCount)

    const createVote = async () => {
        let voteRequest = {
            'articleId' : reply.id,
            'identity' : 'COMMENT',
        }

        let result = await VoteService.create(voteRequest)
        if (result != null) {
            setVoteCount(voteCount + 1)
        }
    }

    return (
        <>
            <Container>
                <div className="col bg-secondary text-dark bg-opacity-10" id="replyEmailAndDate">
                    <span id="replyEmail">{reply.writer}</span>
                    <br/>
                    <span id="replyDate">{reply.dateCreated}</span>
                </div>

                <div className="w-100"/>

                <div className="col bg-secondary text-dark bg-opacity-10 d-flex fs-3" id="replyContent">
                    {reply.content}
                    <div className="overflow-auto"/>
                </div>

                <div className="col bg-secondary text-dark bg-opacity-10" id="replyVote">
                    <span onClick={createVote} id="replyVoteButton" className="btn-sm btn btn-info" type="button">{'추천 ' + voteCount}</span>
                    <br/><br/>
                </div>
                <hr/>
            </Container>
        </>
    );
};

export default ReplyComment;
