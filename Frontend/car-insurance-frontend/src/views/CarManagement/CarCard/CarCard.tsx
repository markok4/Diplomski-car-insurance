import { useEffect, useState } from "react"
import { Car } from "../../../redux/types/Car"
import styles from "./CarCard.module.scss"
import Button from "../../../components/Button/Button"
import DialogBox from "../../../components/Dialog/Dialog"
import { useParams } from "react-router-dom"
import {
  deleteCar,
  fetchCarById,
  restoreCar,
} from "../../../services/CarService"
import { Bounce, toast } from "react-toastify"

type Props = { car?: Car }

function CarCard(props: Props) {
  let [isDeleteOpen, setIsDeleteOpen] = useState(false)
  let [isRestoreOpen, setIsRestoreOpen] = useState(false)
  const [car, setCar] = useState<Car>()
  const { id } = useParams<{ id: string }>()

  useEffect(() => {
    fetchCarById(parseInt(id!)).then(res => {
      console.log(res)
      setCar(res)
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
      const response = await deleteCar(car!.id)
      if (response.status === 200) {
        notifySuccess("Successfully deleted car")
        car!.isDeleted = true
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
      const response = await restoreCar(car!.id)
      if (response.status === 200) {
        notifySuccess("Successfully restored car")
        car!.isDeleted = false
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
      {car ? (
        <div className={styles.card}>
          <div
            className={styles.logo}
            style={{ backgroundImage: `url(${car?.image})` }}
          />
          <div className={styles.row}>
            <span className={styles.label}>Id: </span>
            <span className={styles.value}>{car?.id}</span>
          </div>
          <div className={styles.row}>
            <span className={styles.label}>Year: </span>
            <span className={styles.value}>{car?.year}</span>
          </div>
          <div className={styles.row}>
            <span className={styles.label}>Active: </span>
            <span className={styles.value}>{(!car?.isDeleted).toString()}</span>
          </div>
          <div className={`${styles.row} ${styles["btn-row"]}`}>
            <Button className={styles.btn} color="primary">
              Edit
            </Button>
            <Button
              className={styles.btn}
              color="accent"
              onClick={car?.isDeleted ? openRestoreModal : openDeleteModal}
            >
              {car?.isDeleted ? "Restore" : "Deactivate"}
            </Button>
          </div>
          <DialogBox
            isOpen={isRestoreOpen}
            closeModal={closeRestoreModal}
            label={"Restore car?"}
            header={"Restore car?"}
          />
          <DialogBox
            isOpen={isDeleteOpen}
            closeModal={closeDeleteModal}
            label={"Delete car?"}
            header={"Delete car?"}
          />
        </div>
      ) : (
        <div className={styles.nodata}>No data found</div>
      )}
    </div>
  )
}

export default CarCard
