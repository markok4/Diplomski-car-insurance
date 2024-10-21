import { useEffect } from "react"
import styles from "./BrandForm.module.scss"
import CreateBrandForm from "../../../components/Brandmanagment/BrandTable/BrandCard/CreateBrandForm/CreateBrandForm"
import brandService from "../../../services/BrandService"
import { useLocation } from "react-router-dom"

type Props = {}
function BrandForm({ brandData }: any) {
  const location = useLocation()
  const isUpdate = location.pathname.includes("update")
  const titleText = isUpdate ? "Update" : "Create"
  useEffect(() => {
    const saveBrandToDatabase = async () => {
      try {
        const response = await brandService.saveBrand(brandData)
        console.log("Brand successfully saved:", response.data)
      } catch (error) {
        console.error("Error saving brand:", error)
      }
    }

    if (brandData) {
      saveBrandToDatabase()
    }
  }, [brandData, isUpdate])

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>{titleText} Brand</h1>
      <CreateBrandForm />
    </div>
  )
}

export default BrandForm
