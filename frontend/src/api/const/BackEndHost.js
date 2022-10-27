const hostname = window && window.location && window.location.hostname;

let backendHostUrl = "https://levelupback.link:8080"
let imageServerUrl = "http://localhost:8090"

if (hostname === "localhost") {
	backendHostUrl = "http://localhost:8080"
	imageServerUrl = "http://localhost:8090"
}

export const S3_URL = "https://mylevelupbuckets3.s3.ap-northeast-2.amazonaws.com/"
export const SERVICE_APP_URL = `${backendHostUrl}`
export const IMAGE_SERVER_URL = `${imageServerUrl}`