import httpService from "../../utils/httpService"
import { POLICY_SERVICE_API_URL } from "../../utils/consts"

export async function fetchProposalsActiveThunk() {
  const response = await httpService.get(
    POLICY_SERVICE_API_URL + "proposals/active",
  )
  return response.data
}
