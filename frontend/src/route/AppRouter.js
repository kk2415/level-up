import {Routes, Route, Link} from 'react-router-dom'

import React from 'react';
import Home from '../page/home/Home';
import MyPage from '../page/member/MyPage'
import CreateChannel from '../page/channel/CreateChannel'
import SignIn from '../page/member/SignIn'
import SignUp from '../page/member/SignUp'
import ChannelDescription from "../page/channel/ChannelDescription";
import ModifyChannel from "../page/channel/ModifyChannel";
import Channel from "../page/channel/Channel"
import ChannelManager from "../page/channel/ChannelManager"
import CreatePost from "../page/post/CreatePost"
import CreateChannelNotice from "../page/channelNotice/CreateChannelNotice"
import DetailChannelNotice from "../page/channelNotice/DetailChannelNotice"
import ModifyChannelNotice from "../page/channelNotice/ModifyChannelNotice"
import Post from "../page/post/Post"
import Qna from "../page/qna/Qna"

import CreateArticle from "../page/article/CreateArticle"
import ArticleList from "../page/article/ArticleList"
import Article from "../page/article/Article"
import ModifyArticle from "../page/article/ModifyArticle"

import ModifyPost from "../page/post/ModifyPost"
import ConfirmEmail from "../page/member/ConfirmEmail";

const AppRouter = () => {
  return (
	<Routes>
		<Route exact path="/" element={<Home />}></Route>

		<Route path="/signin" element={<SignIn />}></Route>
		<Route path="/signup" element={<SignUp />}></Route>
		<Route path="/mypage" element={<MyPage />}></Route>
		<Route path="/confirm-email" element={<ConfirmEmail />}></Route>

		<Route path="/channel/:channelId" element={<Channel />}></Route>
		<Route path="/channel/create" element={<CreateChannel />}></Route>
		<Route path="/channel/modify/:channelId" element={<ModifyChannel />}></Route>
		<Route path="/channel/description/:channelId" element={<ChannelDescription />}></Route>
		<Route path="/channel/:channelId/manager" element={<ChannelManager />}></Route>

		<Route path="/channel-notice/create" element={<CreateChannelNotice />}></Route>
		<Route path="/channel-notice/detail/:channelNoticeId" element={<DetailChannelNotice />}></Route>
		<Route path="/channel-notice/modify/:channelNoticeId" element={<ModifyChannelNotice />}></Route>

		<Route path="/post/:postId" element={<Post />}></Route>
		<Route path="/channel-post/:postId" element={<Post />}></Route>
		<Route path="/post/create" element={<CreatePost />}></Route>
		<Route path="/post/modify/:postId" element={<ModifyPost />}></Route>
		<Route path="/channel-post/modify/:postId" element={<ModifyPost />}></Route>

		<Route path="/qna" element={<Qna />}></Route>

		<Route path="/article/:articleId" element={<Article />}></Route>
		<Route path="/article/list" element={<ArticleList />}></Route>
		<Route path="/article/create" element={<CreateArticle />}></Route>
		<Route path="/article/modify/:articleId" element={<ModifyArticle />}></Route>

	</Routes>
  )
}

export default AppRouter