import React, { useEffect, useState } from "react"
import { CarPart } from "../../../redux/types/CarPart"
import { CarPartService } from "../../../services/CarPartService"
import Modal from "react-modal"
import styles from "./CarPartsSelectModal.module.scss"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faCheckCircle } from "@fortawesome/free-solid-svg-icons"

type Props = {
  selectedCarParts: CarPart[]
  onUpdate: (carParts: CarPart[]) => void
  onClose: () => void
  isOpen: boolean
}

const CarPartSelectModal = ({
  selectedCarParts,
  onUpdate,
  onClose,
  isOpen,
}: Props) => {
  const [carParts, setCarParts] = useState<CarPart[]>()
  const [searchTerm, setSearchTerm] = useState("")

  const handleCheckboxChange = (carPart: CarPart, isChecked: boolean) => {
    if (isChecked) {
      onUpdate([...selectedCarParts, carPart])
    } else {
      onUpdate(selectedCarParts.filter(part => part.id !== carPart.id))
    }
  }

  useEffect(() => {
    CarPartService.getAll().then(result => {
      setCarParts(result.content)
    })
  }, [])

  if (!carParts) return null

  const filteredCarParts = carParts
    .filter(carPart => {
      return carPart.description
        .toLowerCase()
        .includes(searchTerm.toLowerCase())
    })
    .slice(0, 10)

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => onClose()}
      contentLabel="Car Parts"
      overlayClassName={styles.overlay}
      className={styles.container}
      ariaHideApp={false}
    >
      <div className={styles["modal-content"]}>
        <div className={styles.header}>
          <h2 className={styles.title}>Select Car Parts</h2>
          <span className={styles.close} onClick={onClose}>
            &times;
          </span>
        </div>
        <input
          type="text"
          placeholder="Search by description"
          className={styles.search}
          value={searchTerm}
          onChange={e => setSearchTerm(e.target.value)}
        />
        <ul className={styles.items}>
          {filteredCarParts.map((carPart: CarPart) => (
            <li key={carPart.id}>
              <label className={styles.row}>
                <input
                  hidden
                  type="checkbox"
                  checked={selectedCarParts.some(
                    part => part.id === carPart.id,
                  )}
                  onChange={e =>
                    handleCheckboxChange(carPart, e.target.checked)
                  }
                />
                {carPart.description}
                <FontAwesomeIcon
                  className={styles.checked}
                  icon={faCheckCircle}
                />
              </label>
            </li>
          ))}
        </ul>
      </div>
    </Modal>
  )
}

export default CarPartSelectModal
