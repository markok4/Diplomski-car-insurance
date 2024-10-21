import Card from "../../../components/Card/Card"
import styles from "./ProposalCard.module.scss"
import { Proposal } from "../../../redux/types/Proposal"
import { Link } from "react-router-dom"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import {
  faDownload,
  faEdit,
  faInfoCircle,
} from "@fortawesome/free-solid-svg-icons"
import { ProposalStatus } from "../../../redux/enums/ProposalStatus"

type ProposalProps = {
  proposal: Proposal
}

const ProposalCard = ({ proposal }: ProposalProps) => {
  function isProposalCompleted() {
    return (
      proposal.proposalStatus == ProposalStatus.PAID ||
      proposal.proposalStatus == ProposalStatus.CONFIRMED
    )
  }

  return (
    <Card
      key={proposal.id}
      style={`${styles.card} ${!proposal.isValid && styles.invalid}`}
    >
      <div className={styles.plates}>
        <span className={styles.subtext}>Plates: </span>
        <span className={styles["important-text"]}>{proposal.carPlates}</span>
      </div>
      <span className={styles.validation}>
        {!proposal.isValid && <>Invalid</>}
      </span>
      <div className={styles.dates}>
        <div>
          <span className={styles.subtext}>Created: </span>
          <span>{new Date(proposal.creationDate).toLocaleString()}</span>
        </div>
      </div>
      <span className={styles.amount}>$ {proposal.amount}</span>
      <span className={styles["important-text"]}>
        {proposal.insurancePlan.name}
      </span>
      <div className={styles.status}>
        <span className={styles.subtext}>Status: </span>
        <span className={styles["important-text"]}>
          {proposal.proposalStatus}
        </span>
      </div>
      <div className={styles.action}>
        {!isProposalCompleted() ? (
          <Link to={`/proposal/create/${proposal.id}`} className={styles.link}>
            <FontAwesomeIcon icon={faEdit} />
          </Link>
        ) : (
          <>
            <Link to={"/"} className={styles.link}>
              <FontAwesomeIcon icon={faDownload} />
            </Link>
            {/* //TODO link ka detaljima polise je /policy/{id}, treba povezati */}
            <Link to={"/"} className={styles.link}>
              <FontAwesomeIcon icon={faInfoCircle} />
            </Link>
          </>
        )}
      </div>
    </Card>
  )
}

export default ProposalCard
