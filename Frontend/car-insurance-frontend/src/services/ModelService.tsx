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

export const ModelService = {
  createModel: async (name: string, brandId: number) => {
    try {
      const response = await httpService.post(`${CAR_SERVICE_API_URL}models`, {
        name,
        brandId,
      })

      notifySuccess("Model created successfully")
      return response.data
    } catch (error) {
      notifyError("Failed to create model")
      throw error
    }
  },

  updateModel: async (id: number, name: string, brandId: number) => {
    try {
      const response = await httpService.put(
        `${CAR_SERVICE_API_URL}models/${id}`,
        {
          name,
          brandId,
        },
      )

      notifySuccess("Model updated successfully")
      return response.data
    } catch (error) {
      notifyError("Failed to update model")
      throw error
    }
  },

  handleDelete: async (id: number) => {
    try {
      await httpService.delete(`${CAR_SERVICE_API_URL}models/${id}`)

      notifySuccess("Model deleted successfully")
      return true
    } catch (error) {
      notifyError("Failed to delete model")
      throw error
    }
  },

  handleRestore: async (id: number) => {
    try {
      await httpService.patch(`${CAR_SERVICE_API_URL}models/${id}`)

      notifySuccess("Model restored successfully")
      return true
    } catch (error) {
      notifyError("Failed to restore model")
      throw error
    }
  },

  getAll: async (page: number, itemsPerPage: number) => {
    try {
      const response = await httpService.get(`${CAR_SERVICE_API_URL}models`, {
        params: {
          page,
          size: itemsPerPage,
        },
      })

      return {
        content: response.data.content,
        totalPages: response.data.totalPages,
      }
    } catch (error) {
      notifyError("Failed to get models")
      throw error
    }
  },

  getOneModel: async (id: number) => {
    try {
      const response = await httpService.get(
        `${CAR_SERVICE_API_URL}models/${id}`,
      )

      return response.data
    } catch (error) {
      notifyError("Failed to fetch model")
      throw error
    }
  },

  getAllByBrand: async (brandId: number) => {
    try {
      const response = await httpService.get(
        `${CAR_SERVICE_API_URL}models/brand/${brandId}`,
      )
      return response.data
    } catch (error) {
      notifyError("Failed to fetch models")
      console.error(error)
      throw error
    }
  },
}

export default ModelService
