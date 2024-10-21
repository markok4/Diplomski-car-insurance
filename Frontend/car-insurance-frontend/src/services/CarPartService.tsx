import { CAR_SERVICE_API_URL } from "../utils/consts"
import { CarPart } from "../redux/types/CarPart"
import httpService from "../utils/httpService"

export const CarPartService = {
  async getAll(): Promise<{ content: CarPart[]; totalPages: number }> {
    try {
      const response = await httpService.get(CAR_SERVICE_API_URL + "car-parts")
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  },
}
