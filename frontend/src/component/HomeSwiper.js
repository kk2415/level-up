import React, { useRef, useState, useEffect } from "react";
import {Container, Col, Row, Form, Button, Card} from 'react-bootstrap'

import { Swiper, SwiperSlide } from "swiper/react"; // basic
import SwiperCore, { Navigation, Pagination } from "swiper";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

import CardSlide from "./CardSlide";
import { send } from "../api/request"


const StudySwiper = () => {
	const [channels, setChannels] = useState([])
	const [count, setCount] = useState(0)
  
	useEffect(() => {
		send('/api/channels/' + 'STUDY', 'GET')
			.then((data) => {
				setChannels(data.data)
				setCount(data.count)
			})
			.catch((error) => {
				console.log(error)
			})
	}, [])

	return (
	  <>
		<Container className='mt-5'>
		  <Swiper
			slidesPerView={3}
			spaceBetween={30}
			slidesPerGroup={3}
			loop={true}
			loopFillGroupWithBlank={true}
			pagination={{
			clickable: true,
			}}
			navigation={true}
			modules={[Pagination, Navigation]}
			className="mySwiper"
		  >
  
			{
			  channels.map(function(channel) {
				return (
				  <SwiperSlide>
					<CardSlide channel={ channel } />
				  </SwiperSlide>
				)
			  })
			}
  
			</Swiper>
		</Container>
	  </>
	)
}

const ProjectSwiper = () => {
	const [channels, setChannels] = useState([])
	const [count, setCount] = useState(0)

	useEffect(() => {
		send('/api/channels/' + 'PROJECT', 'GET')
			.then((data) => {
				setChannels(data.data)
				setCount(data.count)
			})
			.catch((error) => {
				console.log(error)
			})
	}, [])

	return (
		<>
			<Container className='mt-5'>
				<Swiper
					slidesPerView={3}
					spaceBetween={30}
					slidesPerGroup={3}
					loop={true}
					loopFillGroupWithBlank={true}
					pagination={{
						clickable: true,
					}}
					navigation={true}
					modules={[Pagination, Navigation]}
					className="mySwiper"
				>

					{
						channels.map(function(channel) {
							return (
								<SwiperSlide>
									<CardSlide channel={ channel } />
								</SwiperSlide>
							)
						})
					}

				</Swiper>
			</Container>
		</>
	)
}

export {StudySwiper, ProjectSwiper}