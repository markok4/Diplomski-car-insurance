import { render, screen } from "@testing-library/react"
import Card from "../src/components/Card/Card"

describe("Card", () => {
  it("renders the Card component", () => {
    render(<Card />)
    screen.debug()
  })
})
