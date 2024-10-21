import React from "react"
import styles from "./Button.module.scss"

type Props = {
  children: React.ReactNode
  color: string
  onClick?: () => void
  className?: string
  disabled?: boolean
  "data-testid"?: string
}

function Button(props: Props) {
  return (
    <button
      type="button"
      className={`${styles.btn} ${styles[props.color]} ${props.className} ${props.disabled ? styles.disabled : ""}`}
      onClick={props.onClick}
      disabled={props.disabled}
      data-testid={props["data-testid"]}
    >
      {props.children}
    </button>
  )
}

export default Button
