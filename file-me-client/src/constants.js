export const BACKEND_PORT = 8080
export const BACKEND_URL = `http://localhost:${BACKEND_PORT}`

export const GET_FILES_URL = `${BACKEND_URL}/files`
export const UPLOAD_FILES_URL = `${BACKEND_URL}/files/upload`
export const DOWNLOAD_FILE_URL = `${BACKEND_URL}/files/download`
export const DELETE_FILE_URL = `${BACKEND_URL}/files/delete`

export const REPLACE_FILE_CONFIRM_MSG = `An item named “{{filename}}” already exists in this location. Do you want to replace it with the one you’re moving?`
export const DELETE_FILE_CONFIRM_MSG = `Are you sure you want to delete “{{filename}}”?`
