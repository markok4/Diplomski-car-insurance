import { Link } from "react-router-dom"
import Table2 from "../../../components/Table2/Table2"
import styles from "./ProposalTable.module.scss"
import { Proposal } from "../../../redux/types/Proposal"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import {
  faDownload,
  faEdit,
  faInfoCircle,
} from "@fortawesome/free-solid-svg-icons"
import { ProposalStatus } from "../../../redux/enums/ProposalStatus"

type ProposalProps = {
  proposals: Array<Proposal>
}

const ProposalTable = (props: ProposalProps) => {
  function isProposalCompleted(proposal: Proposal) {
    return (
      proposal.proposalStatus == ProposalStatus.PAID ||
      proposal.proposalStatus == ProposalStatus.CONFIRMED
    )
  }
  return (
    <Table2
      headings={[
        "Plates",
        "Created",
        "Amount",
        "Insurance plan",
        "Status",
        "",
        "",
      ]}
      rows={props.proposals.map((proposal: Proposal) => [
        proposal.carPlates,
        new Date(proposal.creationDate).toLocaleString(),
        proposal.amount,
        proposal.insurancePlan.name,
        proposal.proposalStatus,
        <div className={styles.btns}>
          {!isProposalCompleted(proposal) ? (
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
        </div>,
        <span className={styles.validation}>
          {!proposal.isValid && <>Invalid</>}
        </span>,
      ])}
    ></Table2>
  )
}

export default ProposalTable
