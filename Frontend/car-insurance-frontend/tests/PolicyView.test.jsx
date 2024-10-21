import { render, screen, waitFor, within } from "@testing-library/react"
import PolicyView from "../src/views/PolicyManagement/PolicyView/PolicyView"
import { Provider } from "react-redux"
import policySlice from "../src/redux/slices/policySlice"
import { configureStore } from "@reduxjs/toolkit"
import { BrowserRouter as Router } from "react-router-dom"

const mockStore = configureStore({
  reducer: {
    policy: policySlice,
  },
})

describe("PolicyView", () => {
  it("displays PolicyView component", () => {
    render(
      <Provider store={mockStore}>
        <Router>
          <PolicyView />
        </Router>
      </Provider>,
    )
  })

  it("displays a list of policies after fetching", async () => {
    render(
      <Provider store={mockStore}>
        <Router>
          <PolicyView />
        </Router>
      </Provider>,
    )

    await waitFor(() => {
      expect(screen.getByTestId("policy-card-flex")).toBeInTheDocument()

      const policies = mockStore.getState().policy.policies

      policies.forEach(policy => {
        const policyCard = screen.getByTestId(`policy-card-${policy.id}`)
        const utils = within(policyCard)

        expect(utils.getByText(policy.proposal.carPlates)).toBeInTheDocument()
        expect(
          utils.getByText(policy.proposal.insurancePlan.name),
        ).toBeInTheDocument()
        expect(utils.getByText(`\$ ${policy.amount}`)).toBeInTheDocument()
        expect(
          utils.getByText(policy.proposal.proposalStatus),
        ).toBeInTheDocument()
        expect(
          utils.getByText(new Date(policy.dateSigned).toLocaleString()),
        ).toBeInTheDocument()
        expect(
          utils.getByText(new Date(policy.expiringDate).toLocaleString()),
        ).toBeInTheDocument()
      })
    })
  })
})
