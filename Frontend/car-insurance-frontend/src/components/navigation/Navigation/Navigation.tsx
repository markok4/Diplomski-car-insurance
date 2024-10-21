import { Link, useNavigate } from "react-router-dom"
import styles from "./Navigation.module.scss"
import { useDispatch, useSelector } from "react-redux"
import { logOut, selectCurrentToken } from "../../../redux/slices/authSlice"

const Navigation = ({ isHidden, onToggleMenu }: any) => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const token = useSelector(selectCurrentToken)

  const onLogoutClick = (event: any) => {
    event.preventDefault()
    dispatch(logOut(undefined))
    navigate("/login")
  }

  return (
    <nav hidden={isHidden}>
      <ul className={styles.menu}>
        <li className={styles.link}>
          <Link to="/" onClick={onToggleMenu}>
            Home
          </Link>
        </li>
        {token && (
          <li className={styles.link}>
            <Link to="/profile" onClick={onToggleMenu}>
              Profile
            </Link>
          </li>
        )}
        {!token && (
          <li className={styles.link}>
            <Link to="/login" onClick={onToggleMenu}>
              Login
            </Link>
          </li>
        )}
        {token && (
          <li className={styles.link}>
            <Link to="/login" onClick={onLogoutClick}>
              Log out
            </Link>
          </li>
        )}
      </ul>
    </nav>
  )
}

export default Navigation
