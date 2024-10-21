import { POLICY_SERVICE_API_URL } from "../utils/consts"
import httpService from "../utils/httpService"

const PolicyService = {
  delete: async (id: number) => {
    try {
      await httpService.delete(`${POLICY_SERVICE_API_URL}policy/${id}`)
    } catch (error) {
      console.error(error)
      throw error
    }
  },

  restore: async (id: number) => {
    try {
      await httpService.patch(`${POLICY_SERVICE_API_URL}policy/${id}`)
    } catch (error) {
      console.error(error)
      throw error
    }
  },

  getAll: async (page: number, itemsPerPage: number) => {
    try {
      const result = await httpService.get(
        `${POLICY_SERVICE_API_URL}policies?page=${page}&size=${itemsPerPage}`,
      )
      return result.data
    } catch (error) {
      console.error(error)
      throw error
    }
  },

  search: async (
    page: number = 0,
    itemsPerPage: number = 10,
    nameOrEmail: string,
    startDate?: Date,
    brandId?: number,
    modelId?: number,
    year?: number,
  ) => {
    try {
      const result = await httpService.get(
        `${POLICY_SERVICE_API_URL}policies/search?page=${page}&size=${itemsPerPage}${nameOrEmail ? `&nameOrEmail=${nameOrEmail}` : ""}${startDate ? `&year=${startDate.getFullYear()}&month=${startDate.getMonth() + 1}&day=${startDate.getDate()}` : ""}${brandId ? `&brandId=${brandId}` : ""}${modelId ? `&modelId=${modelId}` : ""}${year ? `&carYear=${year}` : ""}
        `,
      )
      return result.data
    } catch (error) {
      console.error(error)
      throw error
    }
  },
}

export default PolicyService
