import { useState } from "react"
import Modal from "react-modal"
import styles from "./ZipSelectModal.module.scss"
import { Zip } from "../../../redux/types/Zip"

type Props = {
  zips?: Zip[]
  onSelect: (model: Zip) => void
  onClose: () => void
  isOpen: boolean
}

function CitySelectModal({ zips, onSelect, onClose, isOpen }: Props) {
  const [searchTerm, setSearchTerm] = useState("")

  if (!zips) return null

  const filteredZips = zips
    .filter(zip => {
      return zip.zipNumber
    })
    .slice(0, 10)

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => onClose()}
      contentLabel="Zip Model"
      overlayClassName={styles.overlay}
      className={styles.container}
      ariaHideApp={false}
    >
      <div className={styles["modal-content"]}>
        <div className={styles.header}>
          <h2 className={styles.title}>Select zip</h2>
          <span className={styles.close} onClick={onClose}>
            &times;
          </span>
        </div>
        <input
          type="text"
          placeholder="Search by code"
          className={styles.search}
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
        />
        <ul className={styles.items}>
          {filteredZips.map(zip => (
            <li
              className={styles.row}
              key={zip.id}
              onClick={() => onSelect(zip)}
            >
              {zip.zipNumber}
            </li>
          ))}
        </ul>
      </div>
    </Modal>
  )
}

export default CitySelectModal
