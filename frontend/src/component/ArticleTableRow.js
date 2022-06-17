import React from 'react';

const ArticleTableRow = ({info, url}) => {

    const titleHandler = () => {
        window.location.href = url
    }

    return (
        <>
            {
                info &&
                <tr id="articleRow">
                    <td>{info.id}</td>
                    <td className='text-decoration-underline' onClick={titleHandler}>{info.title + ' [' + info.commentCount + ']'}</td>
                    {/*<td>*/}
                    {/*    <a href={url}>{info.title + ' [' + info.commentCount + ']'}</a>*/}
                    {/*</td>*/}
                    <td>{info.writer}</td>
                    <td>{info.views}</td>
                    <td>{info.voteCount}</td>
                    <td>{info.dateCreated}</td>
                </tr>
            }
        </>
    );
};

export default ArticleTableRow;
