let backendHostUrl;

const hostname = window && window.location && window.location.hostname;

backendHostUrl = "http://15.164.220.179:8080"
if (hostname === "localhost") {
	backendHostUrl = "http://localhost:8080"
}

export const S3_URL = "https://levelupbackends3.s3.ap-northeast-2.amazonaws.com/"
export const BACKEND_URL = `${backendHostUrl}`