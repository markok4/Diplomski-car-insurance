import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import Button from "../../../components/Button/Button"
import styles from "./ModelView.module.scss"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faChevronLeft } from "@fortawesome/free-solid-svg-icons"
import { Model } from "../../../redux/types/Model"
import ModelCard from "./ModelCard/ModelCard"
import ModelService from "../../../services/ModelService"

type Props = {}

function ModelView({}: Props) {
  const [model, setModel] = useState<Model>()
  const { id } = useParams()
  const navigate = useNavigate()

  useEffect(() => {
    const fetchModelById = async (id: string) => {
      try {
        const data = await ModelService.getOneModel(id)
        setModel(data)
      } catch (error) {
        console.error("Error fetching model:", error)
      }
    }

    if (id) {
      fetchModelById(id)
    }
  }, [id])

  return (
    <div className={styles.container}>
      <ModelCard model={model} />
      <Button
        className={styles["btn-back"]}
        color="primary"
        onClick={() => {
          navigate("/models")
        }}
      >
        <FontAwesomeIcon className={styles.info} icon={faChevronLeft} />
      </Button>
    </div>
  )
}

export default ModelView
