import React, {useState, useEffect, useLayoutEffect} from "react";
import {Container} from 'react-bootstrap'

import {useNavigate} from 'react-router-dom'
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination } from "swiper";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import ChannelService from "../api/service/ChannelService";

import $ from "jquery";
import CardSlide from "./CardSlide";
import {TOKEN} from "../api/token";

const StudySwiper = ({category}) => {
	const navigate = useNavigate();
	const orderByPopularButton = category + 'orderByPopular'
	const orderByCreatedAtButton = category + 'orderByCreatedAt'

	const handleOrderByPopularButton = () => {
		setOrderBy('memberCount')
		$('#' + orderByPopularButton).attr('checked', true)
		$('#' + orderByCreatedAtButton).attr('checked', false)
	}

	const handleOrderByCreatedAtButton = () => {
		setOrderBy('id')
		$('#' + orderByCreatedAtButton).attr('checked', true)
		$('#' + orderByPopularButton).attr('checked', false)
	}

	const handleCreateChannel = () => {
		if (localStorage.getItem(TOKEN) === null || localStorage.getItem(TOKEN) === 'null') {
			alert('로그인이 필요합니다')
		}
		else {
			navigate(`/channel/create`);
		}
	}

	const loadChannels = async (category) => {
		const pageable = 'page=0&size=10&sort=' + orderBy + ',desc'
		let result = await ChannelService.getByCategory(category, orderBy, pageable);
		setChannels(result.content)
	}

	const [channels, setChannels] = useState([])
	const [orderBy, setOrderBy] = useState('id')

	useLayoutEffect(() => {
		loadChannels(category)
		$('#' + orderByCreatedAtButton).attr('checked', true)

	}, [orderBy])

	return (
	  <>
		  {
			  channels &&
			  <Container className='mt-5' style={{width: '100%'}}>
				  <div className="d-flex flex-row mb-3">
					  <input onClick={handleOrderByPopularButton} type="radio" className="btn-check" name={orderByPopularButton} id={orderByPopularButton} autoComplete="off" />
					  <label className="btn btn-outline-primary" htmlFor={orderByPopularButton}>인기순</label>

					  <input onClick={handleOrderByCreatedAtButton} type="radio" className="btn-check" name={orderByPopularButton} id={orderByCreatedAtButton} autoComplete="off" />
					  <label className="btn btn-outline-primary" htmlFor={orderByCreatedAtButton}>최신순</label>

					  <button className="btn btn-info fixed p-2" style={{marginLeft: 30}} onClick={handleCreateChannel} type="button" id="createProjectButton">
						  스터디/프로젝트 모집하기
					  </button>
				  </div>
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

export {StudySwiper}