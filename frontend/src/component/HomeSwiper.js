import React, {useState, useEffect, useLayoutEffect} from "react";
import {Container} from 'react-bootstrap'

import { Swiper, SwiperSlide } from "swiper/react"; // basic
import { Navigation, Pagination } from "swiper";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import ChannelService from "../api/service/ChannelService";

import CardSlide from "./CardSlide";

const StudySwiper = () => {
	const [channels, setChannels] = useState([])
	const [count, setCount] = useState(0)

	const loadChannels = async (category) => {
		const pageable = 'page=0&size=10&sort=channel_id,desc'
		let result = await ChannelService.getByCategory(category, pageable);

		setChannels(result.content)
	}

	useLayoutEffect(() => {
		loadChannels('STUDY')
	}, [])

	return (
	  <>
		  {
			  channels &&
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
						  channels.map((channel) => {
							  return (
								  <SwiperSlide>
									  <CardSlide channel={ channel } />
								  </SwiperSlide>
							  )
						  })
					  }

				  </Swiper>
			  </Container>
		  }
	  </>
	)
}

const ProjectSwiper = () => {
	const [channels, setChannels] = useState([])
	const [count, setCount] = useState(0)

	const loadChannels = async (category) => {
		const pageable = 'page=0&size=10&sort=channel_id,desc'
		let result = await ChannelService.getByCategory(category, pageable);

		setChannels(result.content)
	}

	useLayoutEffect(() => {
		loadChannels('PROJECT')
	}, [])

	return (
		<>
			{
				channels &&
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
							channels.map((channel) => {
								return (
									<SwiperSlide>
										<CardSlide channel={ channel } />
									</SwiperSlide>
								)
							})
						}

					</Swiper>
				</Container>
			}
		</>
	)
}

export {StudySwiper, ProjectSwiper}