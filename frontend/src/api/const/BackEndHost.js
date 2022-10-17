let backendHostUrl;

const hostname = window && window.location && window.location.hostname;

backendHostUrl = "https://levelupback.link:8080"
if (hostname === "localhost") {
	backendHostUrl = "http://localhost:8080"
}

export const S3_URL = "https://mylevelupbuckets3.s3.ap-northeast-2.amazonaws.com/"
export const BACKEND_URL = `${backendHostUrl}`