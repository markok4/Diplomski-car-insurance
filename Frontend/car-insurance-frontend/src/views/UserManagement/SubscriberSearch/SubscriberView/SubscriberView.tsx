import { Subscriber } from "../../../../redux/types/Subscriber"
import styles from "./SubscriberView.module.scss"

type Props = { subscriber: Subscriber }

function SubscriberView({ subscriber }: Props) {
  return (
    <div className={styles.subscriberCard}>
      <h3>
        {subscriber.firstName} {subscriber.lastName}
      </h3>
      <p>Email: {subscriber.email}</p>
      <p>Role: {subscriber.roleName}</p>
      <p>{subscriber.isEnabled ? "Enabled" : "Disabled"}</p>
    </div>
  )
}
export default SubscriberView
