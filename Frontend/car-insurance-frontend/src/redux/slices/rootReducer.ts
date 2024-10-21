import { combineReducers } from "@reduxjs/toolkit"
import countryReducer from "./countrySlice"
import authReducer from "./authSlice"
import policyReducer from "./policySlice"
import proposalReducer from "./proposalSlice"

const rootReducer = combineReducers({
  auth: authReducer,
  policy: policyReducer,
  country: countryReducer,
  proposal: proposalReducer
})

export default rootReducer
