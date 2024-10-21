import { POLICY_SERVICE_API_URL } from "../../utils/consts"
import httpService from "../../utils/httpService"
import { PolicyState } from "../slices/policySlice"

type Pagination = {
  page: number
  itemsPerPage: number
}

export async function fetchPoliciesPaginatedThunk(
  { page, itemsPerPage, sortBy = "" }: Pagination & { sortBy?: string },
  { rejectWithValue }: { rejectWithValue: (reason: string) => any },
): Promise<PolicyState> {
  try {
    const response = await httpService.get(
      `${POLICY_SERVICE_API_URL}policies?page=${page - 1}&size=${itemsPerPage}${sortBy ? `&sortBy=${sortBy}` : ""}`,
    )
    const data: PolicyState = {
      policies: response.data.content,
      selectedPolicy: response.data.selectedPolicy,
      itemsPerPage: response.data.pageable.pageSize,
      page: response.data.pageable.pageNumber,
      pages: response.data.totalPages,
      loading: false,
    }
    return data
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || error.message)
  }
}

export async function fetchPolicyByIdThunk(id: string) {
  const response = await httpService.get(
    POLICY_SERVICE_API_URL + "policies/" + id,
  )
  return response.data
}

export async function fetchPoliciesBySubscriberPaginatedThunk(
  {
    page,
    itemsPerPage,
  }: Pagination,
  { rejectWithValue }: { rejectWithValue: (reason: string) => any },
): Promise<PolicyState> {
  try {
    const response = await httpService.get(
      `${POLICY_SERVICE_API_URL}policies/by-subscriber?page=${page }&size=${itemsPerPage}`,
    )
    const data: PolicyState = {
      policies: response.data.content,
      selectedPolicy: response.data.selectedPolicy,
      itemsPerPage: response.data.pageable.pageSize,
      page: response.data.pageable.pageNumber,
      pages: response.data.totalPages,
      loading: false,
    }
    return data
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || error.message)
  }
}


export async function fetchPoliciesBySalesAgentPaginatedThunk(
  {
    page,
    itemsPerPage,
    salesAgentEmail,
    sortBy = "",
  }: Pagination & { salesAgentEmail: string } & { sortBy?: string }, // Add salesAgentEmail to the type
  { rejectWithValue }: { rejectWithValue: (reason: string) => any },
): Promise<PolicyState> {
  try {
    const response = await httpService.get(
      `${POLICY_SERVICE_API_URL}policies/by-sales-agent?page=${page - 1}&size=${itemsPerPage}&salesAgentEmail=${encodeURIComponent(salesAgentEmail)}${sortBy ? `&sortBy=${sortBy}` : ""}`,
    )
    const data: PolicyState = {
      policies: response.data.content,
      selectedPolicy: response.data.selectedPolicy,
      itemsPerPage: response.data.pageable.pageSize,
      page: response.data.pageable.pageNumber,
      pages: response.data.totalPages,
      loading: false,
    }
    return data
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || error.message)
  }
}
