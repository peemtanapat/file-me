import axios from 'axios'
import {
  DELETE_FILE_URL,
  DOWNLOAD_FILE_URL,
  GET_FILES_URL,
  INVALID_TOKEN_MSG,
  UPLOAD_FILES_URL,
} from '../constants'
import FormData from 'form-data'

let token = null

const setToken = (newToken) => {
  token = `Bearer ${newToken}`
}

const getAllFiles = async () => {
  const config = {
    headers: { Authorization: token },
  }

  try {
    const res = await axios.get(GET_FILES_URL, config)
    return res.data?.files
  } catch (error) {
    const resMessage = error.response?.data?.message
    const isInvalidToken = resMessage && resMessage.includes(INVALID_TOKEN_MSG)
    if (isInvalidToken) {
      throw new Error(INVALID_TOKEN_MSG)
    }
    return null
  }
}

const uploadFile = async ({ file }) => {
  const formData = new FormData()
  formData.append('file', file)

  try {
    await axios.post(UPLOAD_FILES_URL, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: token,
      },
    })

    return ''
  } catch (error) {
    return error?.response?.data?.message
  }
}

const getDownloadFileUrl = ({ file }) => {
  return `${DOWNLOAD_FILE_URL}?fileId=${file.name}&token=${token}`
}

const deleteFile = async ({ file }) => {
  const config = {
    headers: { Authorization: token },
  }

  const deleteFileUrl = `${DELETE_FILE_URL}?fileId=${file.name}`
  const res = await axios.delete(deleteFileUrl, config)

  return res.data
}

export default {
  setToken,
  getAllFiles,
  uploadFile,
  getDownloadFileUrl,
  deleteFile,
}
