// import Cookies from 'js-cookie'

const TokenKey = 'Loin_token'
export default{
  
  getToken() {
    return localStorage.getItem(TokenKey)
    // return Cookies.get(TokenKey)
  },

  setToken(token) {
    localStorage.setItem(TokenKey, token)
    // return Cookies.set(TokenKey, token)
  },

  removeToken() {
    localStorage.removeItem(TokenKey)
    // return Cookies.remove(TokenKey)
  }
  
}
