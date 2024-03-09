const bcrypt = require('bcrypt')
const crypto = require('crypto')

const hashPassword = async (password) => {
  return await bcrypt.hash(password, 10)
}

/**
 * Generate a secure random password with the specified length.
 * @param {number} length - The length of the password.
 * @returns {string} - The generated password.
 */
const generateSecurePassword = (length) => {
  // Define the character set for the password
  const characters =
    'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+'

  // Create an array to store random characters
  const passwordArray = new Array(length)

  // Generate random bytes using a cryptographically secure pseudo-random number generator (CSPRNG)
  const randomBytes = crypto.randomBytes(length)

  // Fill the password array with random characters from the character set
  for (let i = 0; i < length; i++) {
    // Get a random index within the character set length
    const randomIndex = randomBytes[i] % characters.length

    // Set the character at the random index in the password array
    passwordArray[i] = characters.charAt(randomIndex)
  }

  // Join the characters in the password array to form the password string
  return passwordArray.join('')
}

module.exports = { hashPassword, generateSecurePassword }
