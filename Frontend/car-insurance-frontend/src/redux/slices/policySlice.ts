import { PayloadAction, createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import { AxiosError } from "axios"
import { Policy } from "../types/Policy"
import {
  fetchPoliciesPaginatedThunk,
  fetchPolicyByIdThunk,
  fetchPoliciesBySalesAgentPaginatedThunk,
  fetchPoliciesBySubscriberPaginatedThunk,
} from "../thunk/policyThunk"

export interface PolicyState {
  policies: Policy[]
  selectedPolicy: Policy | null
  page: number
  itemsPerPage: number
  pages: number
  loading: boolean
  error?: string
}

const initialState: PolicyState = {
  policies: [],
  selectedPolicy: null,
  page: 0,
  itemsPerPage: 10,
  pages: -1,
  loading: false,
}

export const fetchPolicies = createAsyncThunk<
  any,
  { page: number; itemsPerPage: number; sortBy: string },
  { rejectValue: any }
>("policy/fetchPolicies", fetchPoliciesPaginatedThunk)

export const fetchPolicyById = createAsyncThunk(
  "policy/fetchPolicyById",
  fetchPolicyByIdThunk,
)

export const fetchPoliciesBySubscriber = createAsyncThunk<
  any,
  {
    page: number
    itemsPerPage: number
  },
  { rejectValue: any }
>(
  "policy/fetchPolicyBySubscriberThunk",
  fetchPoliciesBySubscriberPaginatedThunk,
)

export const fetchPoliciesBySalesAgent = createAsyncThunk<
  any,
  {
    page: number
    itemsPerPage: number
    salesAgentEmail: string
    sortBy: string
  },
  { rejectValue: any }
>("policy/fetchPoliciesBySalesAgent", fetchPoliciesBySalesAgentPaginatedThunk)

const policySlice = createSlice({
  name: "policy",
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
    builder
      .addCase(fetchPolicies.pending, state => {
        state.loading = true
        state.error = undefined
      })
      .addCase(fetchPolicies.fulfilled, (state, action) => {
        return action.payload
      })
      .addCase(fetchPolicies.rejected, (state, action) => {
        state.loading = false
        state.policies = []
        state.error = action.error.message || "Unknown error occurred"
      })
      .addCase(fetchPolicyById.pending, state => {
        state.loading = true
        state.error = ""
      })
      .addCase(fetchPolicyById.fulfilled, (state, action) => {
        state.loading = false
        state.selectedPolicy = action.payload
      })
      .addCase(fetchPolicyById.rejected, (state, action) => {
        state.loading = false
        state.selectedPolicy = null
        state.error = action.error.message || "Unknown error occurred"
        state.policies = []
      })
      .addCase(fetchPoliciesBySubscriber.pending, state => {
        state.loading = true
        state.error = undefined
      })
      .addCase(fetchPoliciesBySubscriber.fulfilled, (state, action) => {
        return action.payload
      })
      .addCase(fetchPoliciesBySubscriber.rejected, (state, action) => {
        state.loading = false
        state.error = action.error.message || "Unknown error occurred"
        state.policies = []
      })
      .addCase(fetchPoliciesBySalesAgent.pending, state => {
        state.loading = true
        state.error = undefined
      })
      .addCase(fetchPoliciesBySalesAgent.fulfilled, (state, action) => {
        return action.payload
      })
      .addCase(fetchPoliciesBySalesAgent.rejected, (state, action) => {
        state.loading = false
        state.error = action.error.message || "Unknown error occurred"
        state.policies = []
      })
  },
})

export default policySlice.reducer
