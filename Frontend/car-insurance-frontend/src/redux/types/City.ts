import { Country } from "./Country"

export type City = {
  id: number
  name: string
  country: Country
  isDeleted: boolean
}
