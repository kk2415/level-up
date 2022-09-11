import {Routes, Route, Link} from 'react-router-dom'

import React from 'react';
import Home from '../page/home/Home';
import MyPage from '../page/member/MyPage'
import CreateChannel from '../page/channel/CreateChannel'
import SignIn from '../page/member/SignIn'
import SignUp from '../page/member/SignUp'
import FindingPassword from '../page/member/FindingPassword'

import ChannelDescription from "../page/channel/ChannelDescription";
import ModifyChannel from "../page/channel/ModifyChannel";
import Channel from "../page/channel/Channel"
import ChannelManager from "../page/channel/ChannelManager"
import CreateChannelPost from "../page/channelPost/CreateChannelPost"
import CreateChannelNotice from "../page/channelNotice/CreateChannelNotice"
import DetailChannelNotice from "../page/channelNotice/DetailChannelNotice"
import ModifyChannelNotice from "../page/channelNotice/ModifyChannelNotice"
import DetailChannelPost from "../page/channelPost/DetailChannelPost"

import CreateArticle from "../page/article/CreateArticle"
import ArticleList from "../page/article/ArticleList"
import Article from "../page/article/Article"
import ModifyArticle from "../page/article/ModifyArticle"
import ModifyPost from "../page/channelPost/ModifyChannelPost"
import ConfirmEmail from "../page/member/ConfirmEmail";
import ChannelInfo from "../page/channel/ChannelInfo";

const AppRouter = () => {
  return (
	<Routes>
		<Route exact path="/" element={<Home />}></Route>

		<Route path="/signin" element={<SignIn />}></Route>
		<Route path="/signup" element={<SignUp />}></Route>
		<Route path="/mypage" element={<MyPage />}></Route>
		<Route path="/confirm-email" element={<ConfirmEmail />}></Route>
		<Route path="/finding-password" element={<FindingPassword />}></Route>

		<Route path="/channel/:channelId" element={<Channel />}></Route>
		<Route path="/channels" element={<ChannelInfo />}></Route>
		<Route path="/channel/create" element={<CreateChannel />}></Route>
		<Route path="/channel/modify/:channelId" element={<ModifyChannel />}></Route>
		<Route path="/channel/description/:channelId" element={<ChannelInfo />}></Route>
		<Route path="/channel/:channelId/manager" element={<ChannelManager />}></Route>

		<Route path="/channel-notice/create" element={<CreateChannelNotice />}></Route>
		<Route path="/channel-notice/detail/:channelNoticeId" element={<DetailChannelNotice />}></Route>
		<Route path="/channel-notice/modify/:channelNoticeId" element={<ModifyChannelNotice />}></Route>

		<Route path="/post/:postId" element={<DetailChannelPost />}></Route>
		<Route path="/channel-post/:postId" element={<DetailChannelPost />}></Route>
		<Route path="/post/create" element={<CreateChannelPost />}></Route>
		<Route path="/post/modify/:postId" element={<ModifyPost />}></Route>
		<Route path="/channel-post/modify/:postId" element={<ModifyPost />}></Route>

		<Route path="/article/:articleId" element={<Article />}></Route>
		<Route path="/article/list" element={<ArticleList />}></Route>
		<Route path="/article/create" element={<CreateArticle />}></Route>
		<Route path="/article/modify/:articleId" element={<ModifyArticle />}></Route>

	</Routes>
  )
}

export default AppRouter