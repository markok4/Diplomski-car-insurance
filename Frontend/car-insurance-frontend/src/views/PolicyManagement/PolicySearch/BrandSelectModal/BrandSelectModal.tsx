import React, { useState } from "react"
import { Brand } from "../../../../redux/types/Brand"
import Modal from "react-modal"
import styles from "./BrandSelectModal.module.scss"

type Props = {
  brands?: Brand[]
  onSelect: (brand: Brand) => void
  onClose: () => void
  isOpen: boolean
}

function BrandSelectModal({ brands, onSelect, onClose, isOpen }: Props) {
  const [searchTerm, setSearchTerm] = useState("")

  if (!brands) return null

  const filteredBrands = brands
    .filter(brand => {
      return brand.name.toLowerCase().includes(searchTerm.toLowerCase())
    })
    .slice(0, 10)

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => onClose()}
      contentLabel="Car Brand"
      overlayClassName={styles.overlay}
      className={styles.container}
      ariaHideApp={false}
    >
      <div className={styles["modal-content"]}>
        <div className={styles.header}>
          <h2 className={styles.title}>Select Brand</h2>
          <span className={styles.close} onClick={onClose}>
            &times;
          </span>
        </div>
        <input
          type="text"
          placeholder="Search by name"
          className={styles.search}
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
        />
        <ul className={styles.items}>
          {filteredBrands.map(brand => (
            <li
              className={styles.row}
              key={brand.id}
              onClick={() => onSelect(brand)}
            >
              {brand.name}
            </li>
          ))}
        </ul>
      </div>
    </Modal>
  )
}

export default BrandSelectModal
