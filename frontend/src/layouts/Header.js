import React, { useState, useEffect } from 'react'
import {Link, useNavigate} from 'react-router-dom'
import {Navbar, Container, Nav, Button} from 'react-bootstrap'

import {TOKEN} from '../api/token'
import { MemberService } from '../api/service/MemberService'

const Header = () => {
	const navigate = useNavigate();

	const [signUpShow, setSignUpShow] = useState(true)
	const [signInShow, setSignInShow] = useState(true)
	const [signOutShow, setSignOutShow] = useState(false)
	const [onShowAdmin, setOnShowAdmin] = useState(false)

	const signInButtonHandler = () => {
		setSignUpShow(false)
		setSignInShow(false)
		setSignOutShow(true)
	}

	const signOutButtonHandler = () => {
		MemberService.signOut()
		setSignUpShow(true)
		setSignInShow(true)
		setSignOutShow(false)
	}

	useEffect(() => {
		if (sessionStorage.getItem(TOKEN) === 'null' || sessionStorage.getItem(TOKEN) === null) {
			setSignUpShow(true)
			setSignInShow(true)
			setSignOutShow(false)
			setOnShowAdmin(false)
		}
		else {
			setSignUpShow(false)
			setSignInShow(false)
			setSignOutShow(true)
		}

		if (sessionStorage.getItem('role') === 'ADMIN') {
			setOnShowAdmin(true)
		}
	})

	const myPageHanlder = () => {
		if (sessionStorage.getItem(TOKEN)) {
			navigate(`/mypage`);
		}
		else {
			alert('로그인이 필요합니다')
		}
	}

	const qnaHandler = () => {
		alert('서비스 준비중입니다.')
	}

	const noticeHandler = () => {
		alert('서비스 준비중입니다.')
	}

	return (
		<>
			<header>
				<Container>
					<Navbar expand="lg" className="ml-auto border-bottom">
						<Navbar.Brand href="/" className=''>
							Level Up
						</Navbar.Brand>
						<Navbar.Collapse className="ml-auto">
							<Nav className="ml-auto float-end ">
								{
									signUpShow &&
									<Nav.Link>
										<Link to='/signup'>
											<Button size="md" className='btn-info'>
												회원가입
											</Button>
										</Link>
									</Nav.Link>
								}
								{
									signInShow &&
									<Nav.Link>
										<Link to='/signin'>
											<Button size="md" className='btn-info'>
												로그인
											</Button>
										</Link>
									</Nav.Link>
								}
								{
									signOutShow &&
									<Nav.Link>
										<Button onClick={signOutButtonHandler} size="md" className='btn-info'>
											로그아웃
										</Button>
									</Nav.Link>
								}
								<Nav.Link>
									<Button onClick={myPageHanlder} size="md" className='btn-info'>
										마이페이지
									</Button>
								</Nav.Link>
								<Nav.Link>
									<Button onClick={qnaHandler} size="md" className='btn-info'>
										QNA
									</Button>
								</Nav.Link>
								<Nav.Link>
									<Button onClick={noticeHandler} size="md" className='btn-info'>
										공지사항
									</Button>
								</Nav.Link>
								{
									onShowAdmin &&
									<Nav.Link>
										<Button size="md" className='btn-info'>
											관리자홈
										</Button>
									</Nav.Link>
								}
							</Nav>
						</Navbar.Collapse>
					</Navbar>
				</Container>
			</header>
		</>
	)
}

export default Header