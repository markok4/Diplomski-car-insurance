import axios from "axios"
import { USER_SERVICE_API_URL } from "../utils/consts"
import { Country } from "../redux/types/Country"
import { City } from "../redux/types/City"
import { Address } from "../redux/types/Address"
import { Zip } from "../redux/types/Zip"
import httpService from "../utils/httpService"
import { User } from "../redux/types/User"
import { Subscriber } from "../redux/types/Subscriber"

interface CountryPageData {
  content: Country[]
  totalPages: number
}

interface CityPageData {
  content: City[]
  totalPages: number
}

interface AddressPageData {
  content: Address[]
  totalPages: number
}

interface ZipPageData {
  content: Zip[]
  totalPages: number
}

interface UserPageData {
  content: Subscriber[]
  totalPages: number
}

const UserService = {
  getAllCountries: async (
    page: number,
    itemsPerPage: number,
  ): Promise<CountryPageData> => {
    try {
      const response = await httpService.get(
        `${USER_SERVICE_API_URL}countries`,
        {
          params: { page, size: itemsPerPage },
        },
      )
      return response.data
    } catch (error) {
      console.error("Error while getting countries:", error)
      throw error
    }
  },

  getSubscribers: async (
    page: number,
    itemsPerPage: number,
  ): Promise<UserPageData> => {
    try {
      const response = await httpService.get(
        `${USER_SERVICE_API_URL}subscribers`,
        {
          params: { page, size: itemsPerPage },
        },
      )
      return response.data
    } catch (error) {
      console.error("Error while getting subscribers:", error)
      throw error
    }
  },

  search: async (
    page: number,
    itemsPerPage: number,
    credentials: string,
  ): Promise<UserPageData> => {
    try {
      console.log(credentials)
      const response = await httpService.get(
        `${USER_SERVICE_API_URL}subscribers/search`,
        {
          params: { page, size: itemsPerPage, credentials },
        },
      )
      console.log(response)
      return response.data
    } catch (error) {
      console.error("Error while getting subscribers:", error)
      throw error
    }
  },

  getCitiesByCountry: async (
    page: number,
    itemsPerPage: number,
    countryId: number,
  ): Promise<CityPageData> => {
    try {
      const response = await httpService.get(
        `${USER_SERVICE_API_URL}cities/country/${countryId}`,
        {
          params: { page, size: itemsPerPage },
        },
      )
      return response.data
    } catch (error) {
      console.error("Error while getting cities:", error)
      throw error
    }
  },

  getAddressesByCity: async (
    page: number,
    itemsPerPage: number,
    cityId: number,
  ): Promise<AddressPageData> => {
    try {
      const response = await httpService.get(
        `${USER_SERVICE_API_URL}addresses/city/${cityId}`,
        {
          params: { page, size: itemsPerPage },
        },
      )
      return response.data
    } catch (error) {
      console.error("Error while getting addresses:", error)
      throw error
    }
  },

  getZipsByCity: async (
    page: number,
    itemsPerPage: number,
    cityId: number,
  ): Promise<ZipPageData> => {
    try {
      const response = await httpService.get(
        `${USER_SERVICE_API_URL}zips/city/${cityId}`,
        {
          params: { page, size: itemsPerPage },
        },
      )
      return response.data
    } catch (error) {
      console.error("Error while getting zips:", error)
      throw error
    }
  },

  register: async (userData: any): Promise<string> => {
    try {
      console.log(userData)
      const response = await httpService.post(
        `${USER_SERVICE_API_URL}users/register`,
        userData,
      )
      return response.data
    } catch (error) {
      console.error("Error", error)
      throw error
    }
  },
}

export default UserService
