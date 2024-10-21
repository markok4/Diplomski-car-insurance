import { Formik } from "formik"
import React, { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { toast, Bounce } from "react-toastify"
import { CAR_SERVICE_API_URL } from "../../../utils/consts"
import styles from "./CreateCarForm.module.scss"
import ModelSelectModal from "../ModelSelectModal/ModelSelectModal"
import { Model } from "../../../redux/types/Model"
import ModelService from "../../../services/ModelService"
import CarPartSelectModal from "../CarPartsSelectModal/CarPartsSelectModal"
import { CarPart } from "../../../redux/types/CarPart"
import Button from "../../../components/Button/Button"
import httpService from "../../../utils/httpService"

type Props = {}

function CreateCarForm({}: Props) {
  const navigate = useNavigate()

  const notifySuccess = () =>
    toast.success("Successfully created car", {
      position: "bottom-right",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "light",
      transition: Bounce,
    })

  const notifyError = (errorMessage: string) =>
    toast.error(errorMessage, {
      position: "bottom-right",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "light",
      transition: Bounce,
    })

  const [isModelModalOpen, setModelModalOpen] = useState(false)
  const [selectedModel, setSelectedModel] = useState<Model>()
  const [models, setModels] = useState<Model[]>([])

  const [isCarPartsModalOpen, setCarPartsModalOpen] = useState(false)
  const [selectedCarParts, setSelectedCarParts] = useState<CarPart[]>([])

  const [imagePreview, setImagePreview] = useState<string>()

  useEffect(() => {
    ModelService.getAll(0, 10).then(result => setModels(result.content))
  }, [])

  const handleImageChange = (event: any, setFieldValue: any) => {
    const file = event.currentTarget.files[0]
    if (file) {
      setFieldValue("image", file)
      const reader = new FileReader()
      reader.onloadend = () => {
        setImagePreview(reader.result as string)
      }
      reader.readAsDataURL(file)
    }
  }

  return (
    <>
      <h1 className="heading">Create Car</h1>
      <Formik
        initialValues={{ year: null, image: null, model: 0, carParts: [] }}
        validate={values => {
          const errors: any = {}
          if (!values.image) {
            errors.image = "Image is required"
          } else if (!values.year) {
            errors.year = "Year is required"
          } else if (!values.model) {
            errors.model = "Model is required"
          }

          return errors
        }}
        onSubmit={async (
          values,
          { setSubmitting, resetForm, validateForm, setFieldValue },
        ) => {
          try {
            const formData = new FormData()
            formData.append("year", values.year || "")
            formData.append("modelId", values.model.toString())
            formData.append("carPartIds", values.carParts.toString())
            formData.append("image", values.image || "")

            const response = await httpService.post(
              CAR_SERVICE_API_URL + "cars",
              formData,
            )

            notifySuccess()
            setTimeout(() => {
              navigate("/cars")
            }, 400)
          } catch (error: any) {
            notifyError(error.message)
            setSelectedModel(undefined)
            setSelectedCarParts([])
            setImagePreview("")
            resetForm()
          }
          setSubmitting(false)
        }}
      >
        {({
          values,

          errors,

          touched,

          handleChange,

          handleBlur,

          handleSubmit,

          isSubmitting,

          setFieldValue,
        }) => (
          <form onSubmit={handleSubmit} className={styles.form}>
            <div
              className={styles["image-preview"]}
              style={{
                backgroundImage: `url('${
                  imagePreview ||
                  "https://storage.googleapis.com/proudcity/mebanenc/uploads/2021/03/placeholder-image.png"
                }')`,
              }}
            />
            <div className={styles["input-container"]}>
              <div className={styles.input}>
                <input
                  type="file"
                  id="imageUpload"
                  name="image"
                  accept="image/*"
                  onChange={event => handleImageChange(event, setFieldValue)}
                  style={{ display: "none" }}
                />
                <Button
                  color="accent"
                  onClick={() =>
                    document.getElementById("imageUpload")!.click()
                  }
                >
                  Upload Image
                </Button>
                <div className={styles.validation}>
                  {errors.image && touched.image && errors.image}
                </div>
              </div>

              <div className={styles.input}>
                <label htmlFor="year" className={styles.year}>
                  Year:
                  <input
                    className={styles["year-input"]}
                    type="number"
                    name="year"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.year ?? ""}
                  />
                  <div className={styles.validation}>
                    {errors.year && touched.year && errors.year}
                  </div>
                </label>
              </div>

              <div className={styles.input}>
                <div>
                  Model:
                  <span className={styles["selected-model"]}>
                    {selectedModel ? selectedModel.name : ""}
                  </span>
                  <span className={styles.validation}>
                    {errors.model && touched.model && errors.model}
                  </span>
                </div>
                <Button color="accent" onClick={() => setModelModalOpen(true)}>
                  Select model
                </Button>
              </div>

              {isModelModalOpen && (
                <ModelSelectModal
                  isOpen={isModelModalOpen}
                  models={models}
                  onSelect={model => {
                    setSelectedModel(model)
                    setFieldValue("model", model.id)
                    setModelModalOpen(false)
                  }}
                  onClose={() => setModelModalOpen(false)}
                />
              )}

              <div className={styles.input}>
                <label htmlFor="carParts">Car Parts:</label>
                <ul className={styles.parts}>
                  {selectedCarParts.length ? "" : "No car parts selected."}
                  {selectedCarParts.map((part: CarPart) => (
                    <li key={part.id}>{part.description}</li>
                  ))}
                </ul>
                <Button
                  onClick={() => setCarPartsModalOpen(true)}
                  color="accent"
                >
                  {" "}
                  Select parts
                </Button>
              </div>

              {isCarPartsModalOpen && (
                <CarPartSelectModal
                  isOpen={isCarPartsModalOpen}
                  selectedCarParts={selectedCarParts}
                  onUpdate={(parts: CarPart[]) => {
                    setSelectedCarParts(parts)
                    setFieldValue(
                      "carParts",
                      parts.map(part => part.id),
                    )
                  }}
                  onClose={() => setCarPartsModalOpen(false)}
                />
              )}

              <button
                className={styles.btn}
                type="submit"
                disabled={isSubmitting}
              >
                Create
              </button>
            </div>
          </form>
        )}
      </Formik>
    </>
  )
}

export default CreateCarForm
