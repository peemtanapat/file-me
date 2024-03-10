export const BACKEND_API_GATEWAY_PORT = 8001
export const BACKEND_API_GATEWAY_URL = `http://localhost:${BACKEND_API_GATEWAY_PORT}`
// export const FILE_SERVICE_URL = `http://localhost:8080`
export const USER_SERVICE_URL = `http://localhost:3000/api`

export const GET_FILES_URL = `${BACKEND_API_GATEWAY_URL}/files`
export const UPLOAD_FILES_URL = `${BACKEND_API_GATEWAY_URL}/files/upload`
export const DOWNLOAD_FILE_URL = `${BACKEND_API_GATEWAY_URL}/files/download`
export const DELETE_FILE_URL = `${BACKEND_API_GATEWAY_URL}/files/delete`

export const REPLACE_FILE_CONFIRM_MSG = `An item named “{{filename}}” already exists in this location. Do you want to replace it with the one you’re moving?`
export const DELETE_FILE_CONFIRM_MSG = `Are you sure you want to delete “{{filename}}”?`

export const VERIFY_USER_URL = `${USER_SERVICE_URL}/login/verify`
export const LOGIN_USER_URL = `${USER_SERVICE_URL}/login`
export const LOGGED_FILE_ME_APP_USER = 'loggedFileMeAppUser'

export const INVALID_TOKEN_MSG = 'invalid token'
