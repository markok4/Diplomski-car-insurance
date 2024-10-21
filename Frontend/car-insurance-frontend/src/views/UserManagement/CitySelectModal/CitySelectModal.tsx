import { useState } from "react"
import Modal from "react-modal"
import styles from "./CitySelectModal.module.scss"
import { City } from "../../../redux/types/City"

type Props = {
  cities?: City[]
  onSelect: (model: City) => void
  onClose: () => void
  isOpen: boolean
}

function CitySelectModal({ cities, onSelect, onClose, isOpen }: Props) {
  const [searchTerm, setSearchTerm] = useState("")

  if (!cities) return null

  const filteredCities = cities
    .filter(city => {
      return city.name.toLowerCase().includes(searchTerm.toLowerCase())
    })
    .slice(0, 10)

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => onClose()}
      contentLabel="City Model"
      overlayClassName={styles.overlay}
      className={styles.container}
      ariaHideApp={false}
    >
      <div className={styles["modal-content"]}>
        <div className={styles.header}>
          <h2 className={styles.title}>Select City</h2>
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
          {filteredCities.map(city => (
            <li
              className={styles.row}
              key={city.id}
              onClick={() => onSelect(city)}
            >
              {city.name}
            </li>
          ))}
        </ul>
      </div>
    </Modal>
  )
}

export default CitySelectModal
