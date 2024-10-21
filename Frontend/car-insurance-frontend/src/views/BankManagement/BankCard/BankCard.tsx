import { useEffect, useState } from "react"
import { Bank } from "../../../redux/types/Bank"
import styles from "./BankCard.module.scss"
import Button from "../../../components/Button/Button"
import DialogBox from "../../../components/Dialog/Dialog"
import { Link, useParams } from "react-router-dom"
import {
  deleteBank,
  fetchBankById,
  restoreBank,
} from "../../../services/PaymentService"
import { Bounce, toast } from "react-toastify"

type Props = { bank?: Bank }

function BankCard(props: Props) {
  let [isDeleteOpen, setIsDeleteOpen] = useState(false)
  let [isRestoreOpen, setIsRestoreOpen] = useState(false)
  const [bank, setBank] = useState<Bank>()
  const { id } = useParams<{ id: string }>()

  useEffect(() => {
    fetchBankById(parseInt(id!)).then(res => {
      console.log(res)
      setBank(res)
    })
  }, [id])

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

  async function closeDeleteModal(sucess: boolean) {
    if (sucess) {
      const response = await deleteBank(bank!.id)
      if (response.status === 200) {
        notifySuccess("Successfully deleted bank")
        bank!.isDeleted = true
      } else {
        notifyError(response.data.error)
      }
    }
    setIsDeleteOpen(false)
  }

  function openDeleteModal() {
    setIsDeleteOpen(true)
  }

  async function closeRestoreModal(sucess: boolean) {
    if (sucess) {
      const response = await restoreBank(bank!.id)
      if (response.status === 200) {
        notifySuccess("Successfully restored bank")
        bank!.isDeleted = false
      } else {
        notifyError(response.data.error)
      }
    }
    setIsRestoreOpen(false)
  }

  function openRestoreModal() {
    setIsRestoreOpen(true)
  }

  return (
    <div className={styles.container}>
      {bank ? (
        <div className={styles.card}>
          <h1 className={styles.title}>{bank?.name}</h1>
          <div
            className={styles.logo}
            style={{ backgroundImage: `url(${bank?.logo})` }}
          />
          <div className={styles.row}>
            <span className={styles.label}>Id: </span>
            <span className={styles.value}>{bank?.id}</span>
          </div>
          <div className={styles.row}>
            <span className={styles.label}>Created At: </span>
            <span className={styles.value}>
              {bank?.createdAt
                ? new Date(bank.createdAt).toLocaleDateString("en-GB")
                : ""}
            </span>
          </div>
          <div className={styles.row}>
            <span className={styles.label}>Active: </span>
            <span className={styles.value}>
              {(!bank?.isDeleted).toString()}
            </span>
          </div>
          <div className={`${styles.row} ${styles["btn-row"]}`}>
            <Button className={styles.btn} color="primary">
              <Link
                to={`/bank/${bank.id}/update`}
                style={{ textDecoration: "none", color: "inherit" }}
              >
                Edit
              </Link>
            </Button>
            <Button
              className={styles.btn}
              color="accent"
              onClick={bank?.isDeleted ? openRestoreModal : openDeleteModal}
            >
              {bank?.isDeleted ? "Restore" : "Deactivate"}
            </Button>
          </div>
          <DialogBox
            isOpen={isRestoreOpen}
            closeModal={closeRestoreModal}
            label={"Restore bank?"}
            header={"Restore bank?"}
          />
          <DialogBox
            isOpen={isDeleteOpen}
            closeModal={closeDeleteModal}
            label={"Delete bank?"}
            header={"Delete bank?"}
          />
        </div>
      ) : (
        <div className={styles.nodata}>No data found</div>
      )}
    </div>
  )
}

export default BankCard
