import styles from "./CardFlex.module.scss"

type Props = {
  "data-testid"?: string
  children: any
}

const CardFlex = ({ children, 'data-testid': dataTestId }: Props) => {
	return <div className={styles["card-container"]} data-testid={dataTestId}>{children}</div>
}

export default CardFlex
