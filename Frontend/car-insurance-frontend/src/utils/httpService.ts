import axios from "axios"
import localStorageService from "./localStorageService"

const httpService = axios.create({})

httpService.interceptors.request.use(
  config => {
    const token = localStorageService.getToken()
    if (token) {
      config.headers["Authorization"] = "Bearer " + token
    }
    return config
  },
  error => Promise.reject(error),
)

export default httpService
