import axios from "axios"
import { USER_SERVICE_API_URL } from "../utils/consts"
import httpService from "../utils/httpService"
import { http } from "msw"

const SubscriberService = {
  getAll: async (page: number, itemsPerPage: number) => {
    try {
      const result = await httpService.get(
        `${USER_SERVICE_API_URL}subscribers?page=${page}&size=${itemsPerPage}`,
      )
      return result.data
    } catch (error) {
      console.error(error)
      throw error
    }
  },

  getById: async (id: number) => {
    try {
      const result = await httpService.get(
        `${USER_SERVICE_API_URL}subscribers/${id}`,
      )
      return result.data
    } catch (error) {
      console.error(error)
      throw error
    }
  },

  getAllRoles: async () => {
    try {
      const result = await httpService.get(
        `${USER_SERVICE_API_URL}subscribers/roles`,
      )
      return result.data
    } catch (error) {
      console.error(error)
      throw error
    }
  },

  register: async (userData: any): Promise<string> => {
    try {
      const response = await httpService.post(
        `${USER_SERVICE_API_URL}subscribers`,
        userData,
      )
      return response.data
    } catch (error) {
      console.error("Error", error)
      throw error
    }
  },
}

export default SubscriberService
