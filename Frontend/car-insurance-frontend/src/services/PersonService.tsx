import { CAR_SERVICE_API_URL, USER_SERVICE_API_URL } from "../utils/consts"
import { Bounce, toast } from "react-toastify"
import httpService from "../utils/httpService"
import { useSelector } from "react-redux"
import localStorageService from "../utils/localStorageService"
import { UserProfile } from "../redux/types/UserProfile"

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

export const PersonService = {
  getProfile: async () => {
    try {
      const response = await httpService.get(
        `${USER_SERVICE_API_URL}users/${localStorageService.getUser()}`,
      )

      return response.data
    } catch (error) {
      notifyError("Failed to fetch user profile")
      throw error
    }
  },

  getProfileImage: async (image: string) => {
    try {
      const response = await httpService.get(
        `${USER_SERVICE_API_URL}users/profile-image/${image}`,
      )

      return response.data
    } catch (error) {
      throw error
    }
  },

  updateProfile: async (id: number, formData: FormData) => {
    try {
      const response = await httpService.patch(
        `${USER_SERVICE_API_URL}users/update-profile/${id}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        },
      )

      notifySuccess("Profile updated successfully")
      return response.data
    } catch (error) {
      notifyError("Failed to update profile")
      throw error
    }
  },
}

export default PersonService
