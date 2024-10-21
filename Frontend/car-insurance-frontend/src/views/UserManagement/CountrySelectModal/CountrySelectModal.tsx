import { useState } from "react"
import Modal from "react-modal"
import styles from "./CountrySelectModal.module.scss"
import { Country } from "../../../redux/types/Country"

type Props = {
  countries?: Country[]
  onSelect: (model: Country) => void
  onClose: () => void
  isOpen: boolean
}

function CountrySelectModal({ countries, onSelect, onClose, isOpen }: Props) {
  const [searchTerm, setSearchTerm] = useState("")

  if (!countries) return null

  const filteredCountries = countries
    .filter(country => {
      return country.name.toLowerCase().includes(searchTerm.toLowerCase())
    })
    .slice(0, 10)

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => onClose()}
      contentLabel="Country Model"
      overlayClassName={styles.overlay}
      className={styles.container}
      ariaHideApp={false}
    >
      <div className={styles["modal-content"]}>
        <div className={styles.header}>
          <h2 className={styles.title}>Select Country</h2>
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
          {filteredCountries.map(country => (
            <li
              className={styles.row}
              key={country.id}
              onClick={() => onSelect(country)}
            >
              {country.name}
            </li>
          ))}
        </ul>
      </div>
    </Modal>
  )
}

export default CountrySelectModal
