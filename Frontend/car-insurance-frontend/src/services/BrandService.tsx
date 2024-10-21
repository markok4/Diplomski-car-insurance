import { CAR_SERVICE_API_URL } from "../utils/consts"
import { Bounce, toast } from "react-toastify"
import httpService from "../utils/httpService"

const notifySuccess = (message: string) =>
  toast.success(message, {
    position: "bottom-right",
    autoClose: 5000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "light",
    transition: Bounce,
  })
const notifyError = (errorMessage: string) =>
  toast.error(errorMessage, {
    position: "bottom-right",
    autoClose: 5000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "light",
    transition: Bounce,
  })

const BrandService = {
  deleteBrand: async (id: number) => {
    try {
      await httpService.delete(`${CAR_SERVICE_API_URL}brands/${id}`)
    } catch (error) {
      console.error(error)
    }
  },
  restoreBrand: async (id: number) => {
    try {
      await httpService.patch(`${CAR_SERVICE_API_URL}brands/${id}`)
    } catch (error) {
      console.error("Error restoring brand:", error)
    }
  },

  saveBrand: async (brandData: any) => {
    try {
      const response = await httpService.post(
        `${CAR_SERVICE_API_URL}brands`,
        brandData,
      )
      notifySuccess("Brand created successfully")
      return response.data
    } catch (error) {
      notifyError("Failed to create brand")
      throw error
    }
  },
  updateBrand: async (brandData: any) => {
    try {
      console.log("iz service")
      console.log(brandData)

      const response = await httpService.put(
        `${CAR_SERVICE_API_URL}brands/${brandData.id}`,
        brandData,
      )
      notifySuccess("Brand updated successfully")
      return response.data
    } catch (error) {
      notifyError("Failed to update brand")
      throw error
    }
  },
  deactivateBrand: async (id: number) => {
    try {
      const response = await httpService.patch(
        `${CAR_SERVICE_API_URL}brands/${id}/deactivate`,
      )
      notifySuccess("Brand deactivated/restored successfully")
      return response.data
    } catch (error) {
      notifyError("Failed to deactivate/restore brand")
      throw error
    }
  },
  getBrand: async (id: number) => {
    try {
      const response = await httpService.get(
        `${CAR_SERVICE_API_URL}brands/${id}`,
      )
      return response
    } catch (error) {
      console.error("Error getting brand:", error)
    }
  },
  getAllBrands: async () => {
    try {
      const response = await httpService.get(`${CAR_SERVICE_API_URL}brands`)
      return response.data.content
    } catch (error) {
      console.error(error)
      throw new Error("Failed to fetch brands")
    }
  },
}

export default BrandService
