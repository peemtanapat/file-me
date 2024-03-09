import { Fragment, useEffect, useRef, useState } from 'react'
import './App.css'
import { getAllFiles, uploadFile } from './services/fileService'
import FileList from './components/FileList'
import { checkFilenameDuplicate } from './helpers/fileHelper'
import { REPLACE_FILE_CONFIRM_MSG } from './constants'

function App() {
  const [files, setFiles] = useState(null)
  const inputFileRef = useRef(null)
  const [toUploadFile, setToUploadFile] = useState(null)
  const [filesIsAdded, setFilesIsAdded] = useState(false)
  const [errorMessage, setErrorMessage] = useState(null)

  useEffect(() => {
    const getFilesHandler = async () => {
      const allFiles = await getAllFiles()
      setFiles(allFiles)
    }

    getFilesHandler()
  }, [filesIsAdded])

  const setFileHandler = (event) => {
    if (!event.target?.files) return

    const targetFile = event.target.files[0]

    if (files && checkFilenameDuplicate({ files, targetFile })) {
      // display confirmation popup
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
    const errorMessage = await uploadFile({ file: toUploadFile })
    setErrorMessage(errorMessage)
    setFilesIsAdded(!filesIsAdded)

    setToUploadFile(null)
    inputFileRef.current.value = null
  }

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

export default App
