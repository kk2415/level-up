let backendHostUrl;

const hostname = window && window.location && window.location.hostname;

backendHostUrl = "http://3.34.98.169:8080"
if (hostname === "localhost") {
	backendHostUrl = "http://localhost:8080"
}

export const BACKEND_URL = `${backendHostUrl}`