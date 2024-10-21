import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import Button from "../../../../components/Button/Button"
import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import styles from "./BrandView.module.scss"
import type { Brand } from "../../../../redux/types/Brand"
import { faChevronLeft } from "@fortawesome/free-solid-svg-icons"
import brandService from "../../../../services/BrandService"
import BrandCard from "../../BrandCard/BrandCard"

type Props = {}
function BrandView({}: Props) {
  const [brand, setBrand] = useState<Brand>()
  const { id } = useParams()
  const navigate = useNavigate()

  useEffect(() => {
    console.log("Fetching brand data for id:", id)
    const fetchBrandData = async () => {
      try {
        const response = await brandService.getBrand(Number(id))
        if (response) {
          const data = response.data
          data.creationDate = new Date(data.creationDate)
          console.log("Dobijeni podaci o brendu:", data)
          setBrand(data)
        } else {
          throw new Error("No response from server")
        }
      } catch (error) {
        console.error("Problem with the fetch operation:", error)
      }
    }

    fetchBrandData()
  }, [id])

  if (brand === null) {
    return <div>loading...</div>
  }
  return (
    <div className={styles.container}>
      <BrandCard brand={brand} />
      <Button
        className={styles["btn-back"]}
        color="primary"
        onClick={() => {
          navigate("/brands")
        }}
      >
        <FontAwesomeIcon className={styles.info} icon={faChevronLeft} />
      </Button>
    </div>
  )
}

export default BrandView
