import { Link, useNavigate, useParams } from "react-router-dom"
import styles from "./CountryView.module.scss"
import CountryCard from "../CountryCard/CountryCard"
import Button from "../../../components/Button/Button"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faChevronLeft } from "@fortawesome/free-solid-svg-icons"

type Props = {}

function CountryView({}: Props) {
  const { id } = useParams()
  const navigate = useNavigate()

  return (
    <div className={styles.container}>
      <CountryCard countryId={id ? Number.parseInt(id) : undefined} />
      <Button
        className={styles["btn-back"]}
        color="primary"
        onClick={() => {
          navigate(-1)
        }}
      >
        <FontAwesomeIcon className={styles.info} icon={faChevronLeft} />
      </Button>
    </div>
  )
}

export default CountryView
