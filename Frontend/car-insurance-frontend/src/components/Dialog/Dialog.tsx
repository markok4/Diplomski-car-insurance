import React, { Fragment, useState } from "react"
import styles from "./Dialog.module.scss"
import Modal from "react-modal"
import Button from "../Button/Button"

type Props = {
  isOpen: boolean
  closeModal: (sucess: boolean) => void
  label: string
  header: string
}

function DialogBox({ isOpen, closeModal, label, header }: Props) {
  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => closeModal(false)}
      contentLabel={label}
      overlayClassName={styles.overlay}
      className={styles.container}
      ariaHideApp={false}
    >
      <h2>{header}</h2>
      <div className={styles.row}>
        <Button
          className={styles.btn}
          color="primary"
          onClick={() => closeModal(true)}
        >
          Yes
        </Button>
        <Button
          className={styles.btn}
          color="accent"
          onClick={() => closeModal(false)}
        >
          Cancel
        </Button>
      </div>
    </Modal>
  )
}

export default DialogBox
