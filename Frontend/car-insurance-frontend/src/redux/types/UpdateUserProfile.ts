import { Gender, MaritialStatus, UserRole } from "./User"

export interface UpdateUserProfile {
  firstName: string
  lastName: string
  jmbg: string
  birth: string
  gender: Gender
  maritialStatus: MaritialStatus
  profileImage: string
}
