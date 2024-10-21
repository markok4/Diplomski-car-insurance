import { Navigate, Outlet } from "react-router-dom"
import { useSelector } from "react-redux"
import {
  selectCurrentToken,
  selectCurrentRole,
} from "../redux/slices/authSlice"

interface RequireRoleProps {
  requiredRoles: string[];
}

const RequireRole: React.FC<RequireRoleProps> = ({ requiredRoles  }) => {
  const token = useSelector(selectCurrentToken)
  const userRole = useSelector(selectCurrentRole)

  const isAuthorized = token && requiredRoles.includes(userRole);

  return isAuthorized ? <Outlet /> : <Navigate to="/" replace />
}

export default RequireRole
