import { HttpResponse, http } from "msw"
import { POLICY_SERVICE_API_URL } from "../../src/utils/consts"
import mockPolicies from "./mockPolicies.json"

export const handlers = [
  http.get(`${POLICY_SERVICE_API_URL}policies`, () => {
    return HttpResponse.json(mockPolicies)
  }),
]
