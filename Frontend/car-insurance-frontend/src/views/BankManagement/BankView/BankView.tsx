import React, { useEffect, useState } from "react"
import { Link, useNavigate, useParams } from "react-router-dom"
import { Bank } from "../../../redux/types/Bank"
import styles from "./BankView.module.scss"
import BankCard from "../BankCard/BankCard"
import Button from "../../../components/Button/Button"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faChevronLeft } from "@fortawesome/free-solid-svg-icons"

type Props = {}

function BankView({}: Props) {
  const [bank, setBank] = useState<Bank>()
  const { id } = useParams()
  const navigate = useNavigate()

  useEffect(() => {    
  }, [id])
  return (
    <div className={styles.container}>
      <BankCard bank={bank} />
      <Button
        className={styles["btn-back"]}
        color="primary"
        onClick={() => {
          navigate("/countries")
        }}
      >
        <FontAwesomeIcon className={styles.info} icon={faChevronLeft} />
      </Button>
    </div>
  )
}

export default BankView
