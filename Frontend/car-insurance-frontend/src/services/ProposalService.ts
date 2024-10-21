import { POLICY_SERVICE_API_URL } from "../utils/consts"
import httpService from "../utils/httpService"
import { ToastService } from "./ToastService"

const baseUrl = POLICY_SERVICE_API_URL + "proposals"

export const startProposal = () => {
  httpService.post(baseUrl + "/initialize", {})
}

export const setProposalSubscriber = async (
  proposalId: number,
  subscriberId: number,
) => {
  try {
    const response = await httpService.patch(baseUrl + "/set-subscriber", {
      proposalId,
      subscriberId,
    })
  } catch (error) {
    ToastService.notifyError("Failed to set subscriber")
    throw error
  }
  ToastService.notifySuccess("Successfuly selected subscriber")
}

export const getProposalById = async (id: number) => {
  try {
    const response = await httpService.get(baseUrl + `/${id}`)
    return response.data
  } catch (error) {
    ToastService.notifyError("Failed to fetch proposal")
    throw error
  }
}
