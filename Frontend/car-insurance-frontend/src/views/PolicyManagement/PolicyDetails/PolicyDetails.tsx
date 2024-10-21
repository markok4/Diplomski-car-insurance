import { useDispatch, useSelector } from "react-redux"
import { AppDispatch } from "../../../redux/store"
import { useEffect } from "react"
import { fetchPolicyById } from "../../../redux/slices/policySlice"
import { useParams } from "react-router-dom"
import styles from "./PolicyDetails.module.scss"
import Card from "../../../components/Card/Card"
import BackButton from "../../../components/BackButton/BackButton"

const PolicyDetails = () => {
  const { id } = useParams()

  const { selectedPolicy, loading, error } = useSelector(
    (state: any) => state.policy,
  )
  const dispatch: AppDispatch = useDispatch()

  useEffect(() => {
    id && dispatch(fetchPolicyById(id))
  }, [id])

  return (
    <main className="main-wrapper">
      <h1 className="heading">Policy details</h1>
      <BackButton />
      {loading && <span className={styles.info}>Loading...</span>}
      {!loading && error && <span className={styles.info}>Error: {error}</span>}
      {!loading && selectedPolicy ? (
        <section className={styles.grid}>
          <Card style={`${styles.card} ${styles["car-details"]}`}>
            <h2 className={styles["card-heading"]}>Car Details</h2>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Plates</span>
              <span className={styles.text}>
                {selectedPolicy.proposal.carPlates}
              </span>
            </div>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Year</span>
              <span className={styles.text}>
                {selectedPolicy.proposal.car.year}
              </span>
            </div>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Model</span>
              <span className={styles.text}>
                {selectedPolicy.proposal.car.model.name}
              </span>
            </div>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Brand</span>
              <span className={styles.text}>
                {selectedPolicy.proposal.car.model.brand.name}
              </span>
            </div>
          </Card>
          <Card style={styles.card}>
            <h2 className={styles["card-heading"]}>Policy</h2>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Date Signed</span>
              <span className={styles.text}>
                {new Date(selectedPolicy.dateSigned).toLocaleString()}
              </span>
            </div>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Expiring Date</span>
              <span className={styles.text}>
                {new Date(selectedPolicy.expiringDate).toLocaleString()}
              </span>
            </div>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Money Received Date</span>
              <span className={styles.text}>
                {new Date(selectedPolicy.moneyReceivedDate).toLocaleString()}
              </span>
            </div>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Amount</span>
              <span className={styles.text}>${selectedPolicy.amount}</span>
            </div>
          </Card>
          <Card style={styles.card}>
            <h2 className={styles["card-heading"]}>Proposal</h2>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Creation Date</span>
              <span className={styles.text}>
                {selectedPolicy.proposal.creationDate}
              </span>
            </div>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Status</span>
              <span className={styles.text}>
                {selectedPolicy.proposal.proposalStatus}
              </span>
            </div>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Amount</span>
              <span className={styles.text}>
                ${selectedPolicy.proposal.amount}
              </span>
            </div>
            {!selectedPolicy.proposal.isValid && (
              <span className={styles.invalid}>Invalid</span>
            )}
          </Card>
          <Card style={styles.card}>
            <h2 className={styles["card-heading"]}>Insurance Plan</h2>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Plan Name</span>
              <span className={styles.text}>
                {selectedPolicy.proposal.insurancePlan.name}
              </span>
            </div>
            <div className={styles["card-details"]}>
              <span className={styles.overtext}>Premium</span>
              <span className={styles.text}>
                {selectedPolicy.proposal.insurancePlan.isPremium ? "Yes" : "No"}
              </span>
            </div>
          </Card>
        </section>
      ) : (
        <span className={styles.info}>No content</span>
      )}
    </main>
  )
}

export default PolicyDetails
