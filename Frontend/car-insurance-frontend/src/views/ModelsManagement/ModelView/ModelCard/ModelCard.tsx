import React, { useState } from "react"
import { Model } from "../../../../redux/types/Model"
import styles from "./ModelCard.module.scss"
import Button from "../../../../components/Button/Button"
import DialogBox from "../../../../components/Dialog/Dialog"
import modelService from "../../../../services/ModelService"
import { Link } from "react-router-dom"

type Props = { model?: Model }

function ModelCard(props: Props) {
  let [isDeleteOpen, setIsDeleteOpen] = useState(false)
  let [isRestoreOpen, setIsRestoreOpen] = useState(false)

  async function closeDeleteModal(success: boolean) {
    if (success) {
      try {
        const deleteSuccess = await modelService.handleDelete(props.model?.id!)
        if (deleteSuccess) {
          if (props.model && !props.model.isDeleted) {
            props.model.isDeleted = true
          }
        }
      } catch (error) {}
    }
    setIsDeleteOpen(false)
  }

  function openDeleteModal() {
    setIsDeleteOpen(true)
  }

  async function closeRestoreModal(success: boolean) {
    if (success) {
      try {
        const restoreSuccess = await modelService.handleDelete(props.model?.id!)
        if (restoreSuccess) {
          if (props.model && props.model.isDeleted) {
            props.model.isDeleted = false
          }
        }
      } catch (error) {}
    }
    setIsRestoreOpen(false)
  }

  function openRestoreModal() {
    setIsRestoreOpen(true)
  }

  return (
    <div className={styles.card}>
      <h1 className={styles.title}>{props.model?.name}</h1>
      <div className={styles.row}>
        <span className={styles.label}>Id: </span>
        <span className={styles.value}>{props.model?.id}</span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Brand: </span>
        <span className={styles.value}>{props.model?.brand.name}</span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Created At: </span>
        <span className={styles.value}>
          {props.model?.createdAt.toString().split("T")[0]}
        </span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Active: </span>
        <span className={styles.value}>
          {(!props.model?.isDeleted).toString()}
        </span>
      </div>
      <div className={`${styles.row} ${styles["btn-row"]}`}>
        <Button className={styles.btn} color="primary">
          <Link
            to={`/model/${props.model?.id}/update`}
            style={{ textDecoration: "none", color: "inherit" }}
          >
            Edit
          </Link>
        </Button>
        <Button
          className={styles.btn}
          color="accent"
          onClick={props.model?.isDeleted ? openRestoreModal : openDeleteModal}
        >
          {props.model?.isDeleted ? "Restore" : "Deactivate"}
        </Button>
      </div>
      <DialogBox
        isOpen={isRestoreOpen}
        closeModal={closeRestoreModal}
        label={"Restore model?"}
        header={"Restore model?"}
      />
      <DialogBox
        isOpen={isDeleteOpen}
        closeModal={closeDeleteModal}
        label={"Delete model?"}
        header={"Delete model?"}
      />
    </div>
  )
}

export default ModelCard
