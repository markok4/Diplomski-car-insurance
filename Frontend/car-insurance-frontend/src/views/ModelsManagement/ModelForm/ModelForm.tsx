import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import styles from "./ModelForm.module.scss"
import { Model } from "../../../redux/types/Model"
import CreateModelForm from "./CreateModelForm/CreateModelForm"
import UpdateModelForm from "./UpdateModelForm/UpdateModelForm"
import ModelService from "../../../services/ModelService"

type Props = {}

function ModelForm({}: Props) {
  const { id } = useParams()
  const [model, setModel] = useState<Model>()

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
      <h1 className={styles.title}>{id ? "Update Model" : "Create Model"}</h1>
      {id ? <UpdateModelForm model={model} /> : <CreateModelForm />}
    </div>
  )
}

export default ModelForm
