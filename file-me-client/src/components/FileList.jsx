import FileRow from './FileRow'
const FileList = ({ files }) => {
  return (
    <table>
      <thead>
        <tr>
          <th>No.</th>
          <th>File Name</th>
          <th>Size (KB)</th>
          <th>Created At</th>
          <th>URL</th>
          <th>Delete</th>
        </tr>
      </thead>
      <tbody>
        {files &&
          files.map((file, index) => (
            <FileRow
              key={index + file.name}
              file={file}
              index={++index}
            ></FileRow>
          ))}
      </tbody>
    </table>
  )
}

export default FileList
