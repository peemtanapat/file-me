import React, { Fragment, useEffect, useRef, useState } from 'react'
import { checkFilenameDuplicate } from '../helpers/fileHelper'
import { LOGGED_FILE_ME_APP_USER, REPLACE_FILE_CONFIRM_MSG } from '../constants'
import fileService from '../services/fileService'
import FileList from './FileList'
import { useDispatch } from 'react-redux'
import { setLoggedUser } from '../reducers/userReducer'

const UploadFileForm = () => {
  const dispatch = useDispatch()
  const [files, setFiles] = useState(null)
  const inputFileRef = useRef(null)
  const [toUploadFile, setToUploadFile] = useState(null)
  const [filesIsAdded, setFilesIsAdded] = useState(false)
  const [errorMessage, setErrorMessage] = useState(null)

  useEffect(() => {
    const getFilesHandler = async () => {
      try {
        const allFiles = await fileService.getAllFiles()
        setFiles(allFiles)
      } catch (error) {
        if (error.message === 'invalid token') {
          window.localStorage.removeItem(LOGGED_FILE_ME_APP_USER)
          fileService.setToken(null)
          dispatch(setLoggedUser(null))
          return
        }
      }
    }

    getFilesHandler()
  }, [dispatch, filesIsAdded])

  const setFileHandler = (event) => {
    if (!event.target?.files) return

    const targetFile = event.target.files[0]

    if (files && checkFilenameDuplicate({ files, targetFile })) {
      const confirmMessage = REPLACE_FILE_CONFIRM_MSG.replace(
        '{{filename}}',
        targetFile.name,
      )
      if (!window.confirm(confirmMessage)) {
        event.target.value = null
        return
      }
    }
    setToUploadFile(event.target.files[0])
  }

  const uploadFileHandler = async (event) => {
    const errorMessage = await fileService.uploadFile({ file: toUploadFile })
    setErrorMessage(errorMessage)
    setFilesIsAdded(!filesIsAdded)

    setToUploadFile(null)
    inputFileRef.current.value = null
  }

  console.log('%c⧭', 'color: #807160', { files })

  return (
    <Fragment>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
      <input
        id="file"
        type="file"
        ref={inputFileRef}
        onChange={setFileHandler}
      />
      {toUploadFile && (
        <button type="submit" onClick={uploadFileHandler}>
          Upload to Me 💚
        </button>
      )}
      <br />
      <hr />
      {files && <FileList files={files} />}
    </Fragment>
  )
}

export default UploadFileForm
