import React from 'react';

// '/post/' + posts[idx].id + '?channel=' + channelId
const ArticleTableRow = ({info, url}) => {
    return (
        <>
            {
                info &&
                <tr id="articleRow">
                    <td>{info.id}</td>
                    <td>{info.writer}</td>
                    <td>
                        <a href={url}>{info.title}</a>
                    </td>
                    <td>{info.views}</td>
                    <td>{info.voteCount}</td>
                    <td>{info.dateCreated}</td>
                </tr>
            }
        </>
    );
};

export default ArticleTableRow;
