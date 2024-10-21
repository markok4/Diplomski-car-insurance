import BrandTable from "../../../components/Brandmanagment/BrandTable/BrandTable"
import styles from "./Brands.module.scss"

type Props = {}

function Brands({}: Props) {
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Brands</h1>
      <BrandTable />
    </div>
  )
}

export default Brands
