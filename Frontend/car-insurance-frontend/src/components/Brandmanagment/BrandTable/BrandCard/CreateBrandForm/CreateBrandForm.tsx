import { Formik } from "formik"
import styles from "./CreateBrandForm.module.scss"
import DatePicker from "react-datepicker"
import "react-datepicker/dist/react-datepicker.css"
import moment from "moment-timezone"
import brandService from "../../../../../services/BrandService"
import { useLocation, useNavigate, useParams } from "react-router-dom"
import { useEffect, useState } from "react"

type Props = {}

type Brand = {
  id: number
  name: string
  logoImage: string
  creationDate: Date
}
function renderInputField(
  label: any,
  name: any,
  value: any,
  onChange: any,
  onBlur: any,
  error: any,
  touched: any,
  type = "text",
) {
  return (
    <div className={styles.input}>
      <label htmlFor={name}>{label}:</label>
      <input
        type={type}
        name={name}
        onChange={onChange}
        onBlur={onBlur}
        value={value}
      />
      <div className={styles.validation}>{error && touched && error}</div>
    </div>
  )
}

function renderDatePickerField(label: any, selected: any, onChange: any) {
  return (
    <div className={styles.input}>
      <label htmlFor="creationDate">{label}:</label>
      <DatePicker
        selected={selected ? new Date(selected) : null}
        onChange={date => {
          if (date) {
            const formattedDate = moment(date).format("YYYY-MM-DD")
            onChange("creationDate")(formattedDate)
          }
        }}
        dateFormat="yy/MM/dd"
      />
    </div>
  )
}

function CreateBrandForm({}: Props) {
  const { id } = useParams()
  const navigate = useNavigate()
  const location = useLocation()
  const isUpdate = location.pathname.includes("update")
  const buttonText = isUpdate ? "UPDATE" : "CREATE"
  const [initialValues, setInitialValues] = useState<Brand | null>(null)

  useEffect(() => {
    const fetchBrandData = async (id: number) => {
      try {
        const response = await brandService.getBrand(id)
        if (response) {
          const brandData = response.data
          console.log(brandData.id)
          setInitialValues({
            id: brandData.id,
            name: brandData.name,
            logoImage: brandData.logoImage,
            creationDate: new Date(brandData.creationDate),
          })
        }
      } catch (error) {
        console.error("Error fetching brand data:", error)
      }
    }

    if (isUpdate && id) {
      fetchBrandData(Number(id))
    }
  }, [isUpdate, id])

  if (!initialValues && isUpdate) {
    return <div>Loading...</div>
  }
  return (
    <Formik
      initialValues={{
        id: initialValues?.id || 1,
        name: initialValues?.name || "",
        logoImage: initialValues?.logoImage || "",
        creationDate: initialValues?.creationDate || new Date(),
      }}
      validate={values => {
        const errors: any = {}
        console.log(initialValues)

        if (!values.name) {
          errors.name = "Required"
        }
        if (!values.logoImage) {
          errors.logoImage = "Required"
        }

        return errors
      }}
      onSubmit={(values, { setSubmitting, resetForm }) => {
        if (isUpdate) {
          brandService
            .updateBrand(values)
            .then(data => {
              navigate("/brands")
              setSubmitting(false)
            })
            .catch(error => {
              resetForm()
              setSubmitting(false)
            })
        } else {
          brandService
            .saveBrand(values)
            .then(data => {
              navigate("/brands")
              setSubmitting(false)
            })
            .catch(error => {
              resetForm()
              setSubmitting(false)
            })
        }
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
          {renderInputField(
            "Name",
            "name",
            values.name,
            handleChange,
            handleBlur,
            errors.name,
            touched.name,
          )}
          {renderInputField(
            "Logo",
            "logoImage",
            values.logoImage,
            handleChange,
            handleBlur,
            errors.logoImage,
            touched.logoImage,
          )}
          <button className={styles.btn} type="submit" disabled={isSubmitting}>
            {buttonText}
          </button>
        </form>
      )}
    </Formik>
  )
}

export default CreateBrandForm
