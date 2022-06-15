import React from 'react';

const ArticleTableRow = ({info, url}) => {

    console.log(info)

    return (
        <>
            {
                info &&
                <tr id="articleRow">
                    <td>{info.id}</td>
                    <td>{info.writer}</td>
                    <td>
                        <a href={url}>{info.title + ' [' + info.commentCount + ']'}</a>
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
