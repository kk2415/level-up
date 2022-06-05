import React, {useState, useEffect, useContext, useLayoutEffect} from 'react';
import $ from 'jquery'
import ChannelService from '../../api/ChannelService'
import PostService from '../../api/PostService'
import {Container, Col, Row, Form, Button, Card} from 'react-bootstrap'
import ArticleTableRow from "../ArticleTableRow";

const PagerUnit = ({num, setCurPage}) => {

    const onClick = () => {
        setCurPage(num)
    }

    return (
        <li onClick={onClick} className="page-item" id="page"><a className="page-link">{num}</a></li>
    );
};

export default PagerUnit;
