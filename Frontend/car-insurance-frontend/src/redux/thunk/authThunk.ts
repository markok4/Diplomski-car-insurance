import { createAsyncThunk } from "@reduxjs/toolkit"
import { USER_SERVICE_API_URL } from "../../utils/consts"
import httpService from "../../utils/httpService"

interface LoginResponse {
  user: string
}

export const login = createAsyncThunk<any, any>(
  "auth/login",
  async credentials => {
    try {
      const response = await httpService.post<LoginResponse>(
        `${USER_SERVICE_API_URL}login`,
        credentials,
        {
          headers: {
            "Content-Type": "application/json",
          },
        },
      )
      return response.data
    } catch (error) {
      throw error
    }
  },
)
