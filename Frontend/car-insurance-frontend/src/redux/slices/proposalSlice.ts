import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import { fetchProposalsActiveThunk } from "../thunk/proposalThunk"

const initialState = {
  loading: false,
  proposals: {
    content: [],
  },
  error: "",
}

export const fetchProposalsActive = createAsyncThunk(
  "policy/fetchProposalsActive",
  fetchProposalsActiveThunk,
)

const proposalSlice = createSlice({
  name: "proposal",
  initialState,
  reducers: {},
  extraReducers: builder => {
    builder
      .addCase(fetchProposalsActive.pending, state => {
        state.loading = true
        state.error = ""
      })
      .addCase(fetchProposalsActive.fulfilled, (state, action) => {
        state.loading = false
        state.proposals = action.payload
      })
      .addCase(fetchProposalsActive.rejected, (state, action) => {
        state.loading = false
        state.proposals.content = []
        state.error = action.error.message || "Unknown error occurred"
      })
  },
})

export default proposalSlice.reducer
