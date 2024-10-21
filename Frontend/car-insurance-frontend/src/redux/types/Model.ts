import { Brand } from "./Brand"

export type Model = {
  id: number
  name: string
  brand: Brand
  createdAt: Date
  isDeleted: boolean
}
