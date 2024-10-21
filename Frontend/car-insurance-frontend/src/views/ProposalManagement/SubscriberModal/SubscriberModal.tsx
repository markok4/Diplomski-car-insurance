import React, { useEffect, useState } from "react"
import Modal from "react-modal"
import styles from "./SubscriberModal.module.scss"
import SubscriberService from "../../../services/SubscriberService"
import { Subscriber } from "../../../redux/types/Subscriber"
import Button from "../../../components/Button/Button"
import SubscriberView from "../../UserManagement/SubscriberSearch/SubscriberView/SubscriberView"

type Props = {
  subscriberId: number
  isOpen: boolean
  onClose: () => void
  onSelect: () => void
}

function SubscriberModal({ subscriberId, isOpen, onClose, onSelect }: Props) {
  const [subscriber, setSubscriber] = useState<Subscriber>()

  useEffect(() => {
    if (subscriberId) {
      SubscriberService.getById(subscriberId).then(res => {
        setSubscriber(res)
      })
    }
  }, [subscriberId])
  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => onClose()}
      contentLabel="Subscriber"
      overlayClassName={styles.overlay}
      className={styles.container}
      ariaHideApp={false}
    >
      {subscriber ? <SubscriberView subscriber={subscriber} /> : undefined}

      <Button
        color="primary"
        onClick={() => {
          onSelect()
        }}
      >
        Select
      </Button>
    </Modal>
  )
}

export default SubscriberModal
