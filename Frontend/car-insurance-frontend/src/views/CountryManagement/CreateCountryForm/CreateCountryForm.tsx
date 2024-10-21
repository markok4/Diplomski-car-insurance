import { Formik } from "formik"
import styles from "./CreateCountryForm.module.scss"
import { USER_SERVICE_API_URL } from "../../../utils/consts"
import { useNavigate } from "react-router-dom"
import { Bounce, toast } from "react-toastify"
import httpService from "../../../utils/httpService"

type Props = {}

function CreateCountryForm({}: Props) {
  const navigate = useNavigate()
  const notifySuccess = () =>
    toast.success("Successfully created country", {
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

  return (
    <Formik
      initialValues={{ name: "", abbreviation: "" }}
      validate={values => {
        const errors: any = {}

        if (!values.name) {
          errors.name = "Required"
        } else if (!values.abbreviation) {
          errors.abbreviation = "Required"
        } else if (values.abbreviation.length != 2) {
          errors.abbreviation = "Must be 2 characters long"
        }

        return errors
      }}
      onSubmit={(values, { setSubmitting, resetForm }) => {
        setTimeout(async () => {
          const response = await httpService.post(
            USER_SERVICE_API_URL + "/countries",
            {
              ...values,
            },
          )
          if (response.status != 201) {
            notifyError(response.data.error)
            resetForm()
          } else {
            notifySuccess()
            navigate("/countries")
          }

          setSubmitting(false)
        }, 400)
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
            <label htmlFor="abbreviation">Abbreviation:</label>
            <input
              type="text"
              name="abbreviation"
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.abbreviation}
            />

            <div className={styles.validation}>
              {errors.abbreviation &&
                touched.abbreviation &&
                errors.abbreviation}
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

export default CreateCountryForm
