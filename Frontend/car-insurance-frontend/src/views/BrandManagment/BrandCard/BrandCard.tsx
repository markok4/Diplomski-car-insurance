import React, { useState } from "react"
import styles from "./BrandCard.module.scss"
import DialogBox from "../../../components/Dialog/Dialog"
import Button from "../../../components/Button/Button"
import type { Brand } from "../../../redux/types/Brand"
import { Link, useNavigate } from "react-router-dom"
import brandService from "../../../services/BrandService"

type Props = { brand?: Brand }

function BrandCard(props: Props) {
  const navigate = useNavigate()
  let [isDeleteOpen, setIsDeleteOpen] = useState(false)
  let [isRestoreOpen, setIsRestoreOpen] = useState(false)

  async function closeDeleteModal(sucess: boolean) {
    if (sucess) {
      const brandId = props.brand?.id
      const deleteSuccess = await brandService.deactivateBrand(brandId!)
      if (deleteSuccess) {
        if (props.brand && !props.brand.isDeleted) {
          props.brand.isDeleted = true
        }
      }
    }
    setIsDeleteOpen(false)
  }

  function openDeleteModal() {
    setIsDeleteOpen(true)
  }

  async function closeRestoreModal(sucess: boolean) {
    if (sucess) {
      const brandId = props.brand?.id
      const restoreSuccess = await brandService.deactivateBrand(brandId!)
      if (restoreSuccess) {
        if (props.brand && props.brand.isDeleted) {
          props.brand.isDeleted = false
        }
      }
    }
    setIsRestoreOpen(false)
  }

  function openRestoreModal() {
    setIsRestoreOpen(true)
  }
  function handleEdit() {
    if (props.brand) {
      const id = props.brand.id
      navigate(`/brand/${id}/update`)
    }
  }
  return (
    <div className={styles.card}>
      <h1 className={styles.title}>{props.brand?.name}</h1>
      <div className={styles.row}>
        <span className={styles.label}>Id: </span>
        <span className={styles.value}>{props.brand?.id}</span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Logo image: </span>
        <span className={styles.value}>{props.brand?.logoImage}</span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Created At: </span>
        <span className={styles.value}>
          {props.brand?.creationDate.toDateString()}
        </span>
      </div>
      <div className={styles.row}>
        <span className={styles.label}>Active: </span>
        <span className={styles.value}>
          {(!props.brand?.isDeleted).toString()}
        </span>
      </div>
      <div className={`${styles.row} ${styles["btn-row"]}`}>
        <Button className={styles.btn} color="primary" onClick={handleEdit}>
          Edit
        </Button>
        <Button
          className={styles.btn}
          color="accent"
          onClick={props.brand?.isDeleted ? openRestoreModal : openDeleteModal}
        >
          {props.brand?.isDeleted ? "Restore" : "Deactivate"}
        </Button>
      </div>
      <DialogBox
        isOpen={isRestoreOpen}
        closeModal={closeRestoreModal}
        label={"Restore brand?"}
        header={"Restore brand?"}
      />
      <DialogBox
        isOpen={isDeleteOpen}
        closeModal={closeDeleteModal}
        label={"Delete brand?"}
        header={"Delete brand?"}
      />
    </div>
  )
}

export default BrandCard
