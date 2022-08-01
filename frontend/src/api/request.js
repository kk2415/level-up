import $ from 'jquery';
import { BACKEND_URL } from "./backEndHost.js"
import {TOKEN} from './token'

export async function send(url, method, requestBody) {
	let headers = {
		"Content-Type" : "application/json",
	}

	const accessToken = localStorage.getItem(TOKEN)
	if (accessToken && accessToken !== null) {
		headers.Authorization = "Bearer "+ accessToken
	}

	let options = {
		url: BACKEND_URL + url,
		method: method,
		headers : headers,
		async: false,
	}

	if (requestBody && requestBody !== null) {
		options.data = JSON.stringify(requestBody)
		// options.data = requestBody
	}

	return await new Promise((resolve, reject) => {
		$.ajax(options)
		.done(function (response, textStatus, request) {
			resolve(response)
		})
		.fail(function (error) {
			reject(error)
		})
	})
}


export async function sendMultiPart(url, method, requestBody) {
	let headers = {
	}

	const accessToken = localStorage.getItem(TOKEN)
	if (accessToken && accessToken !== null) {
		headers.Authorization = "Bearer "+ accessToken
	}

	let options = {
		url: BACKEND_URL + url,
		method: method,
		headers : headers,
		async: false,
		processData: false, // - processData : false로 선언 시 formData를 string으로 변환하지 않음
		contentType: false, // - contentType : false 로 선언 시 content-type 헤더가 multipart/form-data로 전송되게 함
		cache: false,
		enctype: 'multipart/form-data',
	}

	if (requestBody && requestBody !== null) {
		options.data = requestBody
	}

	return await new Promise((resolve, reject) => {
		$.ajax(options)
		.done(function (response) {
			resolve(response)
		})
		.fail(function (error) {
			reject(error)
		})
	})
}
