import { createSlice } from "@reduxjs/toolkit"
import { login } from "../thunk/authThunk"
import JwtService from "../../utils/jwtService"
import localStorageService from "../../utils/localStorageService"

const initialState = {
  user: localStorageService.getUser(),
  token: localStorageService.getToken(),
  role: localStorageService.getRole(),
  loading: false,
  error: "",
}

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setCredentials: (state, action) => {
      const { user, accessToken } = action.payload
      state.user = user
      state.token = accessToken
      const decodedRole = JwtService.getUserRoleFromToken(accessToken)
      state.role = decodedRole

      localStorageService.setToken(accessToken)
      localStorageService.setUser(user)
      localStorageService.setRole(state.role)
    },
    logOut: (state, action) => {
      state.user = null
      state.token = null
      state.role = null
      localStorageService.removeToken()
      localStorageService.removeUser()
      localStorageService.removeRole()
    },
  },
  extraReducers: builder => {
    builder
      .addCase(login.pending, state => {
        state.loading = true
        state.error = ""
      })
      .addCase(login.fulfilled, (state, action) => {
        state.loading = false
        state.user = localStorageService.getUser()
        state.token = localStorageService.getToken()
        state.role = localStorageService.getRole()
      })
      .addCase(login.rejected, (state, action) => {
        state.loading = false
        state.error = action.error.message!
        state.user = null
        state.token = null
        state.role = null
      })
  },
})

export const { setCredentials, logOut } = authSlice.actions
export default authSlice.reducer

export const selectCurrentUser = (state: any) => state.auth.user
export const selectCurrentToken = (state: any) => state.auth.token
export const selectCurrentRole = (state: any) => state.auth.role
