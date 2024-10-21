import CardFlex from "../../../components/CardFlex/CardFlex"
import { Policy } from "../../../redux/types/Policy"
import PolicyCard from "../PolicyCard/PolicyCard"
import styles from "./PolicyFlex.module.scss"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faInfoCircle } from "@fortawesome/free-solid-svg-icons"
import { Link } from "react-router-dom"

type PolicyProps = {
  policies: Array<Policy>
}

const PolicyFlex = (props: PolicyProps) => {
  return (
    <CardFlex data-testid="policy-card-flex">
      {props.policies.map((policy: Policy) => (
        <div className={styles["card-wrapper"]} key={policy.id}>
          <PolicyCard policy={policy} />
          <Link to={`/policy/${policy.id}`} className={styles.details}>
            <FontAwesomeIcon icon={faInfoCircle} size="xl" />
          </Link>
        </div>
      ))}
    </CardFlex>
  )
}

export default PolicyFlex
