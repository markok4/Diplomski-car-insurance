import { useState } from "react"
import Modal from "react-modal"
import styles from "./AddressSelectModal.module.scss"
import { Address } from "../../../redux/types/Address"

type Props = {
  addresses?: Address[]
  onSelect: (model: Address) => void
  onClose: () => void
  isOpen: boolean
}

function CitySelectModal({ addresses, onSelect, onClose, isOpen }: Props) {
  const [searchTerm, setSearchTerm] = useState("")

  if (!addresses) return null

  const filteredAddresses = addresses
    .filter(address => {
      return address.street.toLowerCase().includes(searchTerm.toLowerCase())
    })
    .slice(0, 10)

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => onClose()}
      contentLabel="Address Model"
      overlayClassName={styles.overlay}
      className={styles.container}
      ariaHideApp={false}
    >
      <div className={styles["modal-content"]}>
        <div className={styles.header}>
          <h2 className={styles.title}>Select address</h2>
          <span className={styles.close} onClick={onClose}>
            &times;
          </span>
        </div>
        <input
          type="text"
          placeholder="Search by street"
          className={styles.search}
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
        />
        <ul className={styles.items}>
          {filteredAddresses.map(address => (
            <li
              className={styles.row}
              key={address.id}
              onClick={() => onSelect(address)}
            >
              {address.street} {address.streetNumber}
            </li>
          ))}
        </ul>
      </div>
    </Modal>
  )
}

export default CitySelectModal
