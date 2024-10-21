import { Gender, MaritialStatus, UserRole } from "./User"

export interface UserProfile {
  id: number
  firstName: string
  lastName: string
  jmbg: string
  birth: string
  gender: Gender
  maritialStatus: MaritialStatus
  email: string
  isEnabled: boolean
  userRole: UserRole
  profileImage: string
}
