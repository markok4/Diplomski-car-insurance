import { Formik } from "formik"
import { PAYMENT_SERVICE_API_URL } from "../../../utils/consts"
import styles from "./CreateBankFrom.module.scss"
import { useNavigate } from "react-router-dom"
import { Bounce, toast } from "react-toastify"
import { createBank } from "../../../services/PaymentService"
import httpService from "../../../utils/httpService"

type Props = {}

function CreateBankForm({}: Props) {
  const navigate = useNavigate()
  const notifySuccess = () =>
    toast.success("Successfully created bank", {
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
      initialValues={{
        name: "",
        logo: "",
        address: "",
        country: "",
        city: "",
        employeeNumber: "",
      }}
      validate={values => {
        const errors: any = {}

        if (!values.name) {
          errors.name = "Required"
        } else if (!values.logo) {
          errors.logo = "Required"
        } else if (!values.address) {
          errors.address = "Required"
        } else if (!values.country) {
          errors.country = "Required"
        } else if (!values.city) {
          errors.city = "Required"
        } else if (!values.employeeNumber) {
          errors.city = "Required"
        }

        return errors
      }}
      onSubmit={(values, { setSubmitting, resetForm }) => {
        setTimeout(async () => {
          await httpService.post(PAYMENT_SERVICE_API_URL + `banks`, values)
          const response = await createBank(values)
          if (response.status != 201) {
            notifyError(response.data.error)
            resetForm()
          } else {
            notifySuccess()
            navigate("/banks")
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
            <label htmlFor="logo">Logo:</label>
            <input
              type="text"
              name="logo"
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.logo}
            />

            <div className={styles.validation}>
              {errors.logo && touched.logo && errors.logo}
            </div>
          </div>

          <div className={styles.input}>
            <label htmlFor="address">Address:</label>
            <input
              type="text"
              name="address"
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.address}
            />

            <div className={styles.validation}>
              {errors.address && touched.address && errors.address}
            </div>
          </div>

          <div className={styles.input}>
            <label htmlFor="country">Country:</label>
            <input
              type="text"
              name="country"
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.country}
            />

            <div className={styles.validation}>
              {errors.country && touched.country && errors.country}
            </div>
          </div>

          <div className={styles.input}>
            <label htmlFor="city">City:</label>
            <input
              type="text"
              name="city"
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.city}
            />

            <div className={styles.validation}>
              {errors.city && touched.city && errors.city}
            </div>
          </div>

          <div className={styles.input}>
            <label htmlFor="employeeNumber">Employee number:</label>
            <input
              type="number"
              name="employeeNumber"
              onChange={handleChange}
              onBlur={handleBlur}
              value={values.employeeNumber}
            />

            <div className={styles.validation}>
              {errors.employeeNumber &&
                touched.employeeNumber &&
                errors.employeeNumber}
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

export default CreateBankForm
