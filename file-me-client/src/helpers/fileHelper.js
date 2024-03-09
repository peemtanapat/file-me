export const checkFilenameDuplicate = ({ files, targetFile }) => {
  const foundFile = files.find((file) => file.name == targetFile.name)
  return foundFile
}
