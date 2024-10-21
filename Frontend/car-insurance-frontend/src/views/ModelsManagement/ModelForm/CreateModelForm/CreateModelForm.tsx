import { Formik } from "formik"
import styles from "./CreateModelForm.module.scss"
import { useEffect, useState } from "react"
import { Brand } from "../../../../redux/types/Brand"
import { useNavigate } from "react-router-dom"
import modelService from "../../../../services/ModelService"
import BrandService from "../../../../services/BrandService"

type Props = {}

function CreateModelForm({}: Props) {
  const navigate = useNavigate()
  const [brands, setBrands] = useState<Brand[]>([])

  useEffect(() => {
    const fetchBrands = async () => {
      try {
        const data = await BrandService.getAllBrands()
        setBrands(data)
      } catch (error) {
        console.error("Error fetching brands:", error)
      }
    }
    fetchBrands()
  }, [])

  return (
    <Formik
      initialValues={{ name: "", brandId: undefined }}
      validate={values => {
        const errors: any = {}

        if (!values.name) {
          errors.name = "Required"
        } else if (!values.brandId) {
          errors.brandId = "Required"
        }
        return errors
      }}
      onSubmit={(values, { setSubmitting, resetForm }) => {
        modelService
          .createModel(values.name, values.brandId!)
          .then(data => {
            navigate("/models")
            setSubmitting(false)
          })
          .catch(error => {
            resetForm()
            setSubmitting(false)
          })
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
      }) => (
        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.input}>
            <label htmlFor="name">Name:</label>
            <input
              type="text"
              name="name"
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.name}
            />
            <div className={styles.validation}>
              {errors.name && touched.name && errors.name}
            </div>
          </div>

          <div className={styles.input}>
            <label htmlFor="brandId">Brand:</label>
            <select
              name="brandId"
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.brandId}
            >
              <option value="">Select a brand</option>
              {brands?.map(brand => (
                <option key={brand.id} value={brand.id}>
                  {brand.name}
                </option>
              ))}
            </select>
            <div className={styles.validation}>
              {errors.brandId && touched.brandId && errors.brandId}
            </div>
          </div>

          <button className={styles.btn} type="submit" disabled={isSubmitting}>
            Create
          </button>
        </form>
      )}
    </Formik>
  )
}

export default CreateModelForm
