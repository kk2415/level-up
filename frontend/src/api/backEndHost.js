let backendHostUrl;

const hostname = window && window.location && window.location.hostname;

if (hostname === "localhost") {
	backendHostUrl = "http://localhost:8080"
}

export const BACKEND_URL = `${backendHostUrl}`