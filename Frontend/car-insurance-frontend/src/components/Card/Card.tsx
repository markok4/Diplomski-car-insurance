import styles from "./Card.module.scss"

type CardProps = {
  style?: string
  "data-testid"?: string
  children: any
}

const Card = ({ children, style, "data-testid": dataTestId }: CardProps) => {
  return (
    <div className={`${styles.card} ${style}`} data-testid={dataTestId}>
      {children}
    </div>
  )
}

export default Card
