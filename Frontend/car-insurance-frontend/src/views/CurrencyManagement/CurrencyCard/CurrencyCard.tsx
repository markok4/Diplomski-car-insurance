import React, { useState } from "react"
import { Currency } from "../../../redux/types/Currency"
import styles from "./CurrencyCard.module.scss"
import Button from "../../../components/Button/Button"
import DialogBox from "../../../components/Dialog/Dialog"
import { Link, useNavigate } from "react-router-dom"

type Props = { 
  currency?: Currency
  handleDelete: (id: number) => void;
  handleRestore: (id: number) => void;
 }

function CurrencyCard(props: Props) {
  let [isDeleteOpen, setIsDeleteOpen] = useState(false)
  let [isRestoreOpen, setIsRestoreOpen] = useState(false)
  const navigate = useNavigate();

  function closeDeleteModal(sucess: boolean) {
    if (sucess) {
      // api call ovde
      //TODO deleteCurrency
      props.handleDelete(props.currency?.id! );
    }
    setIsDeleteOpen(false)
  }

  function openDeleteModal() {
    setIsDeleteOpen(true)
  }

  function closeRestoreModal(sucess: boolean) {
    if (sucess) {
      // api call ovde
      //todo restore currency
      props.handleRestore(props.currency?.id!);
    }
    setIsRestoreOpen(false)
  }

  function openRestoreModal() {
    setIsRestoreOpen(true)
  }

  return (
    <div className={styles.card}>
      <h1 className={styles.title}>{props.currency?.name}</h1>
      <div className={styles.logo}>
          {props.currency?.logoData ? ( // Check if logoData is available
          <img
            src={`data:image/png;base64, ${props.currency?.logoData}`}
            alt={props.currency?.name}
            className={styles.logoImage}
          />
         ) : (
          <div>No Logo Available</div>
        )}
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Id: </span>
        <span className={styles.value}>{props.currency?.id}</span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Code: </span>
        <span className={styles.value}>{props.currency?.code}</span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Created At: </span>
        <span className={styles.value}>{props.currency?.creationDate}</span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Active: </span>
        <span className={styles.value}>
          {props.currency?.isDeleted ? "Inactive" : "Active"}
        </span>
      </div>
      <div className={`${styles.row} ${styles["btn-row"]}`}>
        <Button 
          className={styles.btn} 
          color="primary" 
          onClick={() => {
            if (props.currency) {
              // Assuming you're using useNavigate hook from React Router v6+
              navigate(`/currency/${props.currency.id}/update`);
            }
          }}>
          Edit
        </Button>
        <Button
          className={styles.btn}
          color="accent"
          onClick={
            props.currency?.isDeleted ? openRestoreModal : openDeleteModal
          }
        >
          {props.currency?.isDeleted ? "Restore" : "Deactivate"}
        </Button>
      </div>
      <DialogBox
        isOpen={isRestoreOpen}
        closeModal={closeRestoreModal}
        label={"Restore currency?"}
        header={"Restore currency?"}
      />
      <DialogBox
        isOpen={isDeleteOpen}
        closeModal={closeDeleteModal}
        label={"Delete currency?"}
        header={"Delete currency?"}
      />
    </div>
  )
}

export default CurrencyCard
