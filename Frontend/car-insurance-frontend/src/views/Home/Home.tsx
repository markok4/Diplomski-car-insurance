import { Link } from "react-router-dom"
import styles from "./Home.module.scss"
import Card from "../../components/Card/Card"
import { useSelector } from "react-redux"
import {
  selectCurrentToken,
  selectCurrentRole,
} from "../../redux/slices/authSlice"

const Home = () => {
  const token = useSelector(selectCurrentToken)
  const userRole = useSelector(selectCurrentRole)

  function isAuthorized(requiredRoles: string[]) {
    return token && requiredRoles.includes(userRole)
  }

  return (
    <main className="main-wrapper">
      <h1 className="heading">Home</h1>
      <section className={styles.grid}>
        {isAuthorized(["SUBSCRIBER"]) && (
          <Card style={styles.card}>
            <span className={styles.info}>Policy</span>
            <div className={styles.links}>
              <Link to="/policies" className={styles.btn}>
                Policies
              </Link>
            </div>
          </Card>
        )}
        {isAuthorized(["SALES_AGENT"]) && (
          <>
            <Card style={styles.card}>
              <span className={styles.info}>Policy</span>
              <div className={styles.links}>
                <Link to="/policies" className={styles.btn}>
                  Policies
                </Link>
                <Link to="/proposals" className={styles.btn}>
                  Proposals
                </Link>
              </div>
            </Card>
          </>
        )}
        {isAuthorized(["MANAGER"]) && (
          <Card style={styles.card}>
            <Link to="/register" className={styles.btn}>
              Register
            </Link>
          </Card>
        )}
        {isAuthorized(["ADMINISTRATOR"]) && (
          <>
            <Card style={styles.card}>
              <span className={styles.info}>Car</span>
              <div className={styles.links}>
                <Link to="/cars" className={styles.btn}>
                  Cars
                </Link>
                <Link to="/models" className={styles.btn}>
                  Models
                </Link>
                <Link to="/brands" className={styles.btn}>
                  Brands
                </Link>
              </div>
            </Card>
            <Card style={styles.card}>
              <span className={styles.info}>Policy</span>
              <div className={styles.links}>
                <Link to="/policies" className={styles.btn}>
                  Policies
                </Link>
                <Link to="/proposals" className={styles.btn}>
                  Proposals
                </Link>
              </div>
            </Card>
            <Card style={styles.card}>
              <span className={styles.info}>Claims</span>
              <div className={styles.links}>
                <Link to="/claims" className={styles.btn}>
                  Claims
                </Link>
              </div>
            </Card>
            <Card style={styles.card}>
              <span className={styles.info}>User Management</span>
              <div className={styles.links}>
                <Link to="/countries" className={styles.btn}>
                  Countries
                </Link>
              </div>
            </Card>
            <Card style={styles.card}>
              <span className={styles.info}>Payment</span>
              <div className={styles.links}>
                <Link to="/banks" className={styles.btn}>
                  Banks
                </Link>
                <Link to="/currencies" className={styles.btn}>
                  Currencies
                </Link>
              </div>
            </Card>
          </>
        )}
      </section>
    </main>
  )
}

export default Home
