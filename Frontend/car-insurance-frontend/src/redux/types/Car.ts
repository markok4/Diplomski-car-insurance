import { CarPart } from "./CarPart"
import { Model } from "./Model"

export interface Car {
  id: number
  year: number
  image: string
  isDeleted: boolean
  model: Model
  carParts: CarPart[]
}

