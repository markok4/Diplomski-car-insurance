import { Link } from "react-router-dom"
import logo from "../../assets/logo/logo-white.svg"
import styles from "./Logo.module.scss"

const Logo = () => {
  return (
    <Link to="/">
      <img className={styles.logo} src={logo} alt="" />
    </Link>
  )
}

export default Logo
