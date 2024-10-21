import { CAR_SERVICE_API_URL } from "../utils/consts"
// eslint-disable-next-line @typescript-eslint/consistent-type-imports
import { Car } from "../redux/types/Car"
import httpService from "../utils/httpService"
import { AxiosResponse } from "axios"

interface PageData {
  content: Car[]
  totalPages: number
}

const CarService = {
  getAllCars: async (page: number, itemsPerPage: number) => {
    try {
      const response = await httpService.get(`${CAR_SERVICE_API_URL}cars`, {
        params: { page, size: itemsPerPage },
      })

      const cars = response.data.content

      const carsWithImages = await Promise.all(
        cars.map(async (car: any) => {
          try {
            const logoResponse: AxiosResponse = await httpService.get(
              `${CAR_SERVICE_API_URL}cars/images/${car.image}`,
            )
            return { ...car, logoData: logoResponse.data }
          } catch (logoError) {
            console.error(`Error fetching logo for car ${car.id}:`, logoError)
            return car
          }
        }),
      )
      return {
        content: carsWithImages, // Set the processed currency data as 'content'
        totalPages: response.data.totalPages, // Set the total pages from the original response
      }
    } catch (error) {
      console.error("Error while getting cars with images:", error)
      throw error
    }
  },
}

const baseUrl = "http://localhost:8080/car-service/api"

export const fetchCarById = async (id: number) => {
  try {
    const response = await httpService.get(`${baseUrl}/cars/${id}`)
    return response.data
  } catch (error) {
    console.error(`Error fetching bank with id \${id}:`, error)
    return null // Return null for error case
  }
}

export const deleteCar = async (id: number) => {
  return await httpService.delete(`${baseUrl}/cars/` + id)
}

export const restoreCar = async (id: number) => {
  return await httpService.patch(`${baseUrl}/cars/` + id + `/restore`)
}

export default CarService
