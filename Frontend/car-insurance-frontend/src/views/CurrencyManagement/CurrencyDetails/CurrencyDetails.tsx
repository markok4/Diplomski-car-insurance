import React, { useEffect, useState } from "react"
import { Link, useNavigate, useParams } from "react-router-dom"
import { Currency } from "../../../redux/types/Currency"
import styles from "./CurrencyDetails.module.scss"
import Button from "../../../components/Button/Button"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faChevronLeft } from "@fortawesome/free-solid-svg-icons"
import CurrencyCard from "../CurrencyCard/CurrencyCard"
import {
  fetchCurrencyById,
  toggleCurrencyDeletedStatus,
} from "../../../services/CurrencyService"
import { Alert } from "antd"

type Props = {}

function CurrencyDetails({}: Props) {
  const [currency, setCurrency] = useState<Currency>()
  const [deleteRestoreTrigger, setDeleteRestoreTrigger] = useState(false)
  const { id } = useParams()
  const navigate = useNavigate()
  const [alertInfo, setAlertInfo] = useState({
    visible: false,
    type: "",
    message: "",
  })

  const setAlertWithTimeout = (
    type: string,
    message: string,
    duration = 5000,
  ) => {
    setAlertInfo({ visible: true, type, message })
    setTimeout(() => {
      setAlertInfo({ visible: false, type: "", message: "" })
    }, duration)
  }

  const handleDelete = async (id: number) => {
    try {
      await toggleCurrencyDeletedStatus(id)
      setDeleteRestoreTrigger(prev => !prev)
      setAlertWithTimeout("success", "Currency deleted successfully!")
    } catch (error) {
      console.error("Error deleting currency:", error)
      setAlertWithTimeout("error", "Failed to delete currency.")
    }
  }

  const handleRestore = async (id: number) => {
    try {
      await toggleCurrencyDeletedStatus(id)
      setDeleteRestoreTrigger(prev => !prev)
      setAlertWithTimeout("success", "Currency restored successfully!")
    } catch (error) {
      console.error("Error restoring currency:", error)
      setAlertWithTimeout("error", "Failed to restore currency.")
    }
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        const currencyData = await fetchCurrencyById(Number(id))
        setCurrency(currencyData)
      } catch (error) {
        console.error("Error fetching currency details:", error)
      }
    }
    if (id) {
      fetchData()
    }
  }, [id, deleteRestoreTrigger])

  return (
    <div className={styles.container}>
      <CurrencyCard
        currency={currency}
        handleDelete={handleDelete}
        handleRestore={handleRestore}
      />
      <Button
        className={styles["btn-back"]}
        color="primary"
        onClick={() => {
          navigate(-1)
        }}
      >
        <FontAwesomeIcon className={styles.info} icon={faChevronLeft} />
      </Button>
      {alertInfo.visible && (
        <div
          className={`${styles.alertContainer} ${alertInfo.visible ? "" : styles.hidden}`}
        >
          <div className={styles.alertItem}>
            <Alert
              message={alertInfo.message}
              type={alertInfo.type}
              showIcon
              closable
              onClose={() => setAlertInfo({ ...alertInfo, visible: false })}
              afterClose={() =>
                setAlertInfo({ visible: false, type: "", message: "" })
              }
            />
          </div>
        </div>
      )}
    </div>
  )
}

export default CurrencyDetails
