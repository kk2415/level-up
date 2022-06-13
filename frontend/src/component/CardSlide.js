import React, { useRef, useState } from "react";
import {} from 'react-bootstrap'
import {Container, Card, Row} from 'react-bootstrap'
import {Routes, Route, Link} from 'react-router-dom'

import { Swiper, SwiperSlide } from "swiper/react"; // basic
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

import "../css/HomeSwiper.css"
import { BACKEND_URL } from "../api/backEndHost"
import { S3_URL } from "../api/backEndHost"

const CardSlide = ({ channel }) => {
	const IMG_DIR = S3_URL + channel.storeFileName

	return (
		<Card>
			<Row className="g-0">
				<Container>
					<Card.Img className="cardImg" variant="top" src={IMG_DIR} style={{ height: '20vh'}} />
					<Card.Body className="card-body">
						<Link to={'/channel/description/' + channel.id}>
							<Card.Title className="card-title">
								{channel.name}
							</Card.Title>
						</Link>
						<Card.Text className="card-text"  style={ {minHeight: "10vh"} }>{channel.thumbnailDescription}</Card.Text>
					</Card.Body>
					<Card.Footer className="card-footer">
						<small className="text-muted">{channel.memberCount} / {channel.limitedMemberNumber}</small>
					</Card.Footer>
				</Container>
			</Row>
		</Card>
	)
}

export default CardSlide