import React, {useState, useEffect, useContext, useLayoutEffect} from 'react';
import {Table, Container, Col, Row, Form, Button, Card} from 'react-bootstrap'
import ArticleTableRow from '../ArticleTableRow'
import PostService from "../../api/PostService";

export const ChannelTable = ({channelId, currentPage, searchCondition} ) => {
    const [posts, setPosts] = useState(null)
    const [postsCount, setPostsCount] = useState(0)

    const loadPost = async (channelId, page, searchCondition) => {
        let result = await PostService.getAll(channelId, page, searchCondition)

        setPosts(result.data)
    }

    useEffect(() => {
        loadPost(channelId, currentPage, searchCondition)
    }, [currentPage]);

    return (
        <>
            {
                posts &&
                <Table>
                    <caption className="caption-top fs-3 fw-bold">게시글</caption>
                    <thead>
                    <tr className="table-active">
                        <th scope="col">번호</th>
                        <th scope="col">글쓴이</th>
                        <th scope="col">제목</th>
                        <th scope="col">조회수</th>
                        <th scope="col">추천</th>
                        <th scope="col">작성일</th>
                    </tr>
                    </thead>
                    <tbody id="tableBody">
                    {
                        posts.map((info) => (
                            <ArticleTableRow info={info}
                                             url={'/post/' + info.id + '?channel=' + channelId} />
                        ))
                    }
                    </tbody>
                </Table>
            }
        </>
    );
};
