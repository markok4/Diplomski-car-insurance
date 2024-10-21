/// <reference types="cypress" />

describe("Policy View E2E Tests", () => {
  it("navigates from the home page to the policies page", () => {
    // Visit the home page
    cy.visit("http://localhost:5173")

    // Find the "Policies" button and click it
    cy.contains("Policies").click()

    // Assert that the URL is now '/policies'
    cy.url().should("include", "/policies")
  })

  it("displays policies in card view", () => {
    cy.visit("http://localhost:5173/policies")
    cy.get('[data-testid="policy-card-grid"]').should("be.visible")
  })

  it("displays policies in table view", () => {
    cy.visit("http://localhost:5173/policies")

    // Toggle to table view
    cy.get('[data-testid="view-toggle"]').click()
    cy.get('[data-testid="policy-card-table"]').should("be.visible")
  })

  it("sorts policies by date signed", () => {
    cy.visit("http://localhost:5173/policies")

    // Select the sort option
    cy.get("select").select("date")
    cy.get('[data-testid="policy-date-signed"]').then(dateElements => {
      const dates = [...dateElements].map(el => el.textContent.trim())
      const timestamps = dates.map(dateString => new Date(dateString).getTime())

      for (let i = 1; i < timestamps.length; i++) {
        expect(timestamps[i]).to.be.at.least(timestamps[i - 1])
      }
    })
  })

  it("sorts policies by car plates", () => {
    cy.visit("http://localhost:5173/policies")

    // Select the sort option
    cy.get("select").select("plates")
    cy.get('[data-testid="policy-car-plates"]').then(carPlatesElements => {
      const carPlates = [...carPlatesElements].map(el => el.textContent.trim())

      for (let i = 0; i < carPlates.length - 1; i++) {
        expect(carPlates[i].localeCompare(carPlates[i + 1])).to.be.at.most(0)
      }
    })
  })

  it("paginates through policies", () => {
    cy.visit("http://localhost:5173/policies")

    let firstPageIdentifiers = []
    cy.get('[data-testid="policy-car-plates"]').each($el => {
      firstPageIdentifiers.push($el.text().trim())
    })

    // Interact with pagination control to go to the next page
    cy.get('[data-testid="pagination-next"]').click()

    // Verify that new identifiers are displayed on the new page
    cy.get('[data-testid="policy-car-plates"]').each(($el, index) => {
      expect(firstPageIdentifiers).to.not.include($el.text().trim())
    })

    // Optionally, go back to the previous page and verify the original identifiers are displayed again
    cy.get('[data-testid="pagination-prev"]').click()
    cy.get('[data-testid="policy-car-plates"]').each(($el, index) => {
      expect(firstPageIdentifiers[index]).to.equal($el.text().trim())
    })
  })
})
