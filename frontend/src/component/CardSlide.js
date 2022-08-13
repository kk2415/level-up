import React from "react";
import {Container, Card, Row} from 'react-bootstrap'
import {Link} from 'react-router-dom'

import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

import "../css/HomeSwiper.css"
import { S3_URL } from "../api/backEndHost"
import $ from "jquery";

const CardSlide = ({ channel }) => {
	const IMG_DIR = S3_URL + channel.storeFileName

	const onMouseOver = () => {
		$('.cardImg').css('cursor', 'pointer')
	}

	const handleCardImage = () => {
		window.location.href = `/channel/description/` + channel.channelId
	}

	return (
		<Card>
			<Row className="g-0">
				<Container>
					<Card.Img onClick={handleCardImage} onMouseOver={onMouseOver} className="cardImg"
							  variant="top" src={IMG_DIR} style={{ height: '25vh', objectFit: "fill"}} />
					<Card.Body className="card-body">
						<Link to={'/channel/description/' + channel.channelId}>
							<Card.Title className="card-title">
								{channel.channelName}
							</Card.Title>
						</Link>
						<Card.Text className="card-text"  style={ {minHeight: "10vh"} }>{channel.mainDescription}</Card.Text>
					</Card.Body>
					<Card.Footer className="card-footer">
						<div className="row">
							<div className="card-title col-lg-6 col-sm-12 text-lg-start text-center">
								{channel.managerName}
							</div>
							<div className="card-title col-lg-6 col-sm-12 text-lg-end text-center">
								<small className="text-muted">{channel.memberCount} / {channel.memberMaxNumber}</small>
							</div>
						</div>

					</Card.Footer>
				</Container>
			</Row>
		</Card>
	)
}

export default CardSlide