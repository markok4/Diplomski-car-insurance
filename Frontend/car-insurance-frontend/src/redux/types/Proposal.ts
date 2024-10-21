import { InsurancePlan } from "."
import { ProposalStatus } from "../enums/ProposalStatus"
import { Car } from "./Car"

export type Proposal = {
  id: number
  isValid?: boolean
  creationDate: Date
  proposalStatus: ProposalStatus
  amount: number
  carPlates: string
  isDeleted?: boolean
  insurancePlan: InsurancePlan
  subscriberId: number
  car: Car
}
