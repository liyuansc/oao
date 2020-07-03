const axios = require('axios')

async function login(username, password, rememberMe) {
  return axios({
    url: '/api/uaa/auth/login',
    method: 'post',
    data: {
      username,
      password,
      rememberMe
    }
  })
}

module.exports = {
  login
}
