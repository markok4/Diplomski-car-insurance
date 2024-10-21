import jwt_decode, { jwtDecode } from "jwt-decode"

class JwtService {
  public static getUserRoleFromToken(accessToken: string): string | null {
    try {
      const decodedToken: any = jwtDecode(accessToken)
      const userRole: string = decodedToken.role
      return userRole
    } catch (error) {
      console.error("Error decoding JWT token:", error)
      return null
    }
  }
}

export default JwtService
