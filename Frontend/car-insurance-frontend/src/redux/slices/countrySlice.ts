import { PayloadAction, createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import { Country } from "../types/Country"
import { fetchCountriesPaginated } from "../thunk/countryThunk"

export interface CountryState {
  countries: Country[]
  page: number
  itemsPerPage: number
  pages: number
  loading: boolean
  error?: string
}

const initialState: CountryState = {
  countries: [],
  page: 0,
  itemsPerPage: 10,
  pages: -1,
  loading: false,
}

export const fetchCountries = createAsyncThunk<
  any,
  { page: number; itemsPerPage: number },
  { rejectValue: any }
>("country/fetch", fetchCountriesPaginated)

const countrySlice = createSlice({
  name: "country",
  initialState,
  reducers: {
    setPage: (state, action: PayloadAction<number>) => {
      console.log("Setting page", action.payload)
      state.page = action.payload
    },
    setItemsPerPage: (state, action: PayloadAction<number>) => {
      console.log("Setting items per page", action.payload)
      state.itemsPerPage = action.payload
    },
  },
  extraReducers: builder => {
    builder.addCase(fetchCountries.pending, state => {
      state.loading = true
      state.error = undefined
    }),
      builder.addCase(fetchCountries.fulfilled, (state, action) => {
        return action.payload
      }),
      builder.addCase(fetchCountries.rejected, (state, action) => {
        state.loading = false
        state.error = action.error.message || "Unknown error occurred"
      })
  },
})

export const { setPage, setItemsPerPage } = countrySlice.actions

export default countrySlice.reducer
