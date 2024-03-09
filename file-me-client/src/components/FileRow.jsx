import { DELETE_FILE_CONFIRM_MSG, DOWNLOAD_FILE_URL } from '../constants'
import { deleteFile } from '../services/fileService'

const FileRow = ({ file, index }) => {
  const urlToDownload = `${DOWNLOAD_FILE_URL}?fileId=${file.name}`

  const deleteFileHandler = async () => {
    const deleteConfirmMessage = DELETE_FILE_CONFIRM_MSG.replace(
      '{{filename}}',
      file.name,
    )
    if (window.confirm(deleteConfirmMessage)) {
      await deleteFile({ file })
    }
  }

  return (
    <tr>
      <th>{index}</th>
      <th>{file.name}</th>
      <th>{file.size}</th>
      <th>{file?.createdAt}</th>
      <th>
        <a href={urlToDownload}>Download</a>
      </th>
      <th>
        <a href="" onClick={deleteFileHandler}>
          Delete
        </a>
      </th>
    </tr>
  )
}

export default FileRow
