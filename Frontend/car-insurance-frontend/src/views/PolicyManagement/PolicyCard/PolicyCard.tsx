import { Link } from "react-router-dom"
import Card from "../../../components/Card/Card"
import { Policy } from "../../../redux/types/Policy"
import styles from "./PolicyCard.module.scss"

type PolicyProps = {
  policy: Policy
}

const PolicyCard = ({ policy }: PolicyProps) => {
  function convertToDate(dateStr: string): Date {
    const parts = dateStr.split(".")
    const day = parseInt(parts[0], 10)
    const month = parseInt(parts[1], 10)
    const year = parseInt(parts[2], 10)
    return new Date(year, month - 1, day) // month is 0-indexed in JavaScript Date
  }

  function isPolicyExpired(expiringDate: string) {
    return convertToDate(expiringDate) < new Date()
  }

  return (
    <Card
      key={policy.id}
      style={`${styles.card} ${isPolicyExpired(policy.expiringDate) && styles.expired}`}
      data-testid={`policy-card-${policy.id}`}
    >
      <div className={styles.plates}>
        <span className={styles.subtext}>Plates: </span>
        <span
          className={styles["important-text"]}
          data-testid="policy-car-plates"
        >
          {policy.proposal.carPlates}
        </span>
      </div>

      <span className={styles.action}>
        {isPolicyExpired(policy.expiringDate) ? (
          <>Expired</>
        ) : (
          <Link to="/" className={styles["action-btn"]}>
            Add Claim
          </Link>
        )}
      </span>
      <div className={styles.dates}>
        <div>
          <span className={styles.subtext}>Signed: </span>
          <span data-testid="policy-date-signed">{policy.dateSigned}</span>
        </div>
        <div>
          <span className={styles.subtext}>Expires: </span>
          <span>{policy.expiringDate}</span>
        </div>
      </div>
      <span className={styles.amount}>$ {policy.amount}</span>
      <span className={styles["important-text"]}>
        {policy.proposal.insurancePlan.name}
      </span>
      <div className={styles.status}>
        <span className={styles.subtext}>Status: </span>
        <span className={styles["important-text"]}>
          {policy.proposal.proposalStatus}
        </span>
      </div>
    </Card>
  )
}

export default PolicyCard
