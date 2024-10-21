import { Link } from "react-router-dom"
import Table2 from "../../../components/Table2/Table2"
import { Policy } from "../../../redux/types/Policy"
import styles from "./PolicyTable.module.scss"

type PolicyProps = {
  policies: Array<Policy>
}

const PolicyTable = (props: PolicyProps) => {
  function convertToDate(dateStr: string): Date {
    const parts = dateStr.split(".");
    const day = parseInt(parts[0], 10);
    const month = parseInt(parts[1], 10);
    const year = parseInt(parts[2], 10);
    return new Date(year, month - 1, day); // month is 0-indexed in JavaScript Date
  }

  function isPolicyExpired(expiringDate: string) {
    return convertToDate(expiringDate) < new Date();
  }

  return (
    <Table2
      headings={[
        "Plates",
        "Signed",
        "Expires",
        "Insurance plan",
        "Amount",
        "Status",
        "Details", // Heading for details column
        "Action"   // Heading for action column
      ]}
      rows={props.policies.map((policy: Policy) => [
        policy.proposal.carPlates,
        policy.dateSigned,
        policy.expiringDate,
        policy.proposal.insurancePlan.name,
        '$'+policy.amount,
        policy.proposal.proposalStatus,
        <div className={styles["action"]}>
          <Link to={`/policy/${policy.id}`} className={styles["action-btn"]}>
            Details
          </Link>
        </div>,
        
        isPolicyExpired(policy.expiringDate) ? (
          <div className={styles["action"]}>
            <>Expired</>
          </div>
        ) : (
          <div className={styles["action"]}>
            <Link to="/" className={styles["action-btn"]}>
              Add Claim
            </Link>
          </div>
        ),
      ])}
      data-testid="policy-card-table"
    ></Table2>
  )
}

export default PolicyTable
