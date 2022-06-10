import React, { useRef, useState } from "react";
import {Container, Col, Row, Form, Button, Card, Image} from 'react-bootstrap'
import {Link, useNavigate} from 'react-router-dom'
import { StudySwiper, ProjectSwiper } from '../../component/HomeSwiper'
import {BACKEND_URL} from "../../api/backEndHost";
import {TOKEN} from "../../api/token";

const Home = () => {
  const navigate = useNavigate();

  const handleCreateChannel = () => {
    if (sessionStorage.getItem(TOKEN) === null || sessionStorage.getItem(TOKEN) === 'null') {
      alert('로그인이 필요합니다')
    }
    else {
      console.log(sessionStorage.getItem(TOKEN))
      navigate(`/channel/create`);
    }
  }


  return (
    <>
      <Container>
        <h2 class="page-section-heading text-center text-uppercase text-secondary my-4">등록된 스터디</h2>
        <div className='w-100' style={{ textAlign: "center" }}>
          <button onClick={handleCreateChannel} className="btn btn-primary btn-lg" type="button" id="createProjectButton">스터디 모집하기</button>
        </div>
        <StudySwiper />
        
        <br></br>
        <br></br>
        <br></br>

        <h2 class="page-section-heading text-center text-uppercase text-secondary my-4">등록된 프로젝트</h2>
        <div className='w-100' style={{ textAlign: "center" }}>
          <button onClick={handleCreateChannel} className="btn btn-primary btn-lg" type="button" id="createProjectButton">프로젝트 모집하기</button>
        </div>
        <ProjectSwiper />
      </Container>
    </>
  )
}

export default Home