import { City } from "./City"
import { Zip } from "./Zip"

export type Address = {
  id: number
  street: string
  streetNumber: string
  city: City
  zip: Zip
  isDeleted: boolean
}
