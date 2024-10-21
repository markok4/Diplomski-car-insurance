import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import styles from "./BankForm.module.scss"
import CreateBankForm from "../CreateBankForm/CreateBankForm"
import { Bank } from "../../../redux/types/Bank"
import UpdateBankForm from "../UpdateBankForm/UpdateBankForm"
import { fetchBankById } from "../../../services/PaymentService"

type Props = {}

function BankForm({}: Props) {
  const { id } = useParams()
  const [bank, setBank] = useState<Bank>()

  useEffect(() => {
    if (id) {
      fetchBankById(parseInt(id!)).then(res => {
        console.log(res)
        setBank(res)
      })
    }
  }, [id])

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>{id ? "Update Bank" : "Create Bank"}</h1>
      {id ? <UpdateBankForm bank={bank} /> : <CreateBankForm />}
    </div>
  )
}

export default BankForm
