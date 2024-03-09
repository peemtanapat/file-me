import axios from 'axios'
import { DELETE_FILE_URL, GET_FILES_URL, UPLOAD_FILES_URL } from '../constants'
import FormData from 'form-data'

export const getAllFiles = async () => {
  const res = await axios.get(GET_FILES_URL)

  return res.data
}

export const uploadFile = async ({ file }) => {
  const formData = new FormData()
  formData.append('file', file)

  try {
    const res = await axios.post(UPLOAD_FILES_URL, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })

    return ''
  } catch (error) {
    return error?.response?.data?.message
  }
}

export const deleteFile = async ({ file }) => {
  const deleteFileUrl = `${DELETE_FILE_URL}?fileId=${file.name}`
  const res = await axios.delete(deleteFileUrl)

  return res.data
}
