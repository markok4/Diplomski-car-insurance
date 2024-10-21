import { USER_SERVICE_API_URL } from "../../utils/consts"
import httpService from "../../utils/httpService"
import { CountryState } from "../slices/countrySlice"

type Pagination = {
  page: number
  itemsPerPage: number
}

export async function fetchCountriesPaginated(
  { page, itemsPerPage }: Pagination,
  { rejectWithValue }: { rejectWithValue: (reason: string) => any },
): Promise<CountryState> {
  try {
    const response = await httpService.get(
      `${USER_SERVICE_API_URL}countries?page=${page}&size=${itemsPerPage}`,
    )
    const data: CountryState = {
      countries: response.data.content,
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
