import React, { useState } from "react"
import { Model } from "../../../redux/types/Model"
import Modal from "react-modal"
import styles from "./ModelSelectModal.module.scss"

type Props = {
  models?: Model[]
  onSelect: (model: Model) => void
  onClose: () => void
  isOpen: boolean
}

function ModelSelectModal({ models, onSelect, onClose, isOpen }: Props) {
  const [searchTerm, setSearchTerm] = useState("")

  if (!models) return null

  const filteredModels = models
    .filter(model => {
      return (
        model.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        model.brand.name.toLowerCase().includes(searchTerm.toLowerCase())
      )
    })
    .slice(0, 10)

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => onClose()}
      contentLabel="Car Model"
      overlayClassName={styles.overlay}
      className={styles.container}
      ariaHideApp={false}
    >
      <div className={styles["modal-content"]}>
        <div className={styles.header}>
          <h2 className={styles.title}>Select Model</h2>
          <span className={styles.close} onClick={onClose}>
            &times;
          </span>
        </div>
        <input
          type="text"
          placeholder="Search by model or brand"
          className={styles.search}
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
        />
        <ul className={styles.items}>
          {filteredModels.map(model => (
            <li
              className={styles.row}
              key={model.id}
              onClick={() => onSelect(model)}
            >
              {model.name} - {model.brand.name}
            </li>
          ))}
        </ul>
      </div>
    </Modal>
  )
}

export default ModelSelectModal
