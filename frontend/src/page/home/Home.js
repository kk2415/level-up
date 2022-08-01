import React from "react";
import {Container} from 'react-bootstrap'
import {useNavigate} from 'react-router-dom'
import { StudySwiper, ProjectSwiper } from '../../component/HomeSwiper'
import {TOKEN} from "../../api/token";

const Home = () => {
  const navigate = useNavigate();

  const handleCreateChannel = () => {
    if (localStorage.getItem(TOKEN) === null || localStorage.getItem(TOKEN) === 'null') {
      alert('로그인이 필요합니다')
    }
    else {
      console.log(localStorage.getItem(TOKEN))
      navigate(`/channel/create`);
    }
  }


  return (
      <>
        <Container>
          <h2 class="page-section-heading text-center text-uppercase text-secondary my-4">등록된 스터디</h2>
          <div className='w-100' style={{ textAlign: "center" }}>
            <button onClick={handleCreateChannel} className="btn btn-info" type="button" id="createProjectButton">
              스터디 모집하기
            </button>
          </div>
          <StudySwiper />

          <br></br>
          <br></br>
          <br></br>

          <h2 class="page-section-heading text-center text-uppercase text-secondary my-4">등록된 프로젝트</h2>
          <div className='w-100' style={{ textAlign: "center" }}>
            <button onClick={handleCreateChannel} className="btn btn-info" type="button" id="createProjectButton">프로젝트 모집하기</button>
          </div>
          <ProjectSwiper />
        </Container>
      </>
  )
}

export default Home