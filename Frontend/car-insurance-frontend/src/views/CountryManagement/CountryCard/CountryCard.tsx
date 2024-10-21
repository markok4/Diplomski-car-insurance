import React, { useEffect, useState } from "react"
import { Country } from "../../../redux/types/Country"
import styles from "./CountryCard.module.scss"
import Button from "../../../components/Button/Button"
import DialogBox from "../../../components/Dialog/Dialog"
import { USER_SERVICE_API_URL } from "../../../utils/consts"
import { Bounce, toast } from "react-toastify"
import { useNavigate } from "react-router-dom"
import httpService from "../../../utils/httpService"

type Props = { countryId?: number }

function CountryCard(props: Props) {
  let [isDeleteOpen, setIsDeleteOpen] = useState(false)
  let [isRestoreOpen, setIsRestoreOpen] = useState(false)
  const navigate = useNavigate()

  const [country, setCountry] = useState<Country>()
  useEffect(() => {
    const getCountry = async () => {
      const { data } = await httpService.get(
        USER_SERVICE_API_URL + `countries/${props.countryId}`,
      )
      setCountry({
        ...data,
      })
    }
    getCountry()
  }, [props.countryId])

  const notifySuccess = (message: string) =>
    toast.success(message, {
      position: "bottom-right",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "light",
      transition: Bounce,
    })
  const notifyError = (errorMessage: string) =>
    toast.error(errorMessage, {
      position: "bottom-right",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "light",
      transition: Bounce,
    })

  function closeDeleteModal(success: boolean) {
    if (success) {
      httpService.delete(USER_SERVICE_API_URL + "countries/" + country?.id)
      notifySuccess("Successfully deleted country")
      country!.isDeleted = true
      setCountry(country)
    }
    setIsDeleteOpen(false)
  }

  function openDeleteModal() {
    setIsDeleteOpen(true)
  }

  function closeRestoreModal(success: boolean) {
    if (success) {
      httpService.patch(
        USER_SERVICE_API_URL + "countries/restore/" + country?.id,
      )
      notifySuccess("Successfully restored country")
      country!.isDeleted = false
      setCountry(country)
    }
    setIsRestoreOpen(false)
  }

  function openRestoreModal() {
    setIsRestoreOpen(true)
  }

  return (
    <div className={styles.card}>
      <h1 className={styles.title}>{country?.name}</h1>
      <div className={styles.row}>
        <span className={styles.label}>Id: </span>
        <span className={styles.value}>{country?.id}</span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Abbreviation: </span>
        <span className={styles.value}>{country?.abbreviation}</span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Created At: </span>
        <span className={styles.value}>
          {country?.createdAt.toString().split("T")[0]}
        </span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Active: </span>
        <span className={styles.value}>{(!country?.isDeleted).toString()}</span>
      </div>
      <div className={`${styles.row} ${styles["btn-row"]}`}>
        <Button
          className={styles.btn}
          color="primary"
          onClick={() => navigate(`/country/${country?.id}/update`)}
        >
          Edit
        </Button>
        <Button
          className={styles.btn}
          color="accent"
          onClick={country?.isDeleted ? openRestoreModal : openDeleteModal}
        >
          {country?.isDeleted ? "Restore" : "Deactivate"}
        </Button>
      </div>
      <DialogBox
        isOpen={isRestoreOpen}
        closeModal={closeRestoreModal}
        label={"Restore country?"}
        header={"Restore country?"}
      />
      <DialogBox
        isOpen={isDeleteOpen}
        closeModal={closeDeleteModal}
        label={"Delete country?"}
        header={"Delete country?"}
      />
    </div>
  )
}

export default CountryCard
