import { Address } from "./Address"
import { Contact } from "./Contact"

export type User = {
  id: number
  firstName: string
  lastName: string
  jmbg: string
  birth: Date
  gender: Gender
  maritialStatus: MaritialStatus
  contact: Contact
  isDeleted: boolean
  email: string
  password: string
  isEnabled: boolean
  isActive: boolean
  address: Address
  userRole: UserRole
}

export enum UserRole {
  SUBSCRIBER,
  DRIVER,
  MANAGER,
  CLAIMS_ADJUSTER,
  SALES_AGENT,
  CUSTOMER_SERVICE_REPRESENTATIVE,
  ADMINISTRATOR,
  CLAIM_HANDLER,
}

export enum Gender {
  MALE,
  FEMALE,
  OTHER,
}

export enum MaritialStatus {
  SINGLE,
  TAKEN,
  DIVORCED,
  WIDOWED,
  OTHER,
}
