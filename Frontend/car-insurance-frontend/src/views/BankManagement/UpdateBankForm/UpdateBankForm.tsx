import { Formik } from "formik"
import styles from "./UpdateBankForm.module.scss"
import { Bank } from "../../../redux/types/Bank"
import { useNavigate } from "react-router-dom"
import { Bounce, toast } from "react-toastify"
import { updateBank } from "../../../services/PaymentService"

type Props = { bank?: Bank }

function UpdateBankForm({ bank }: Props) {
  const navigate = useNavigate()
  const notifySuccess = () =>
    toast.success("Successfully updated bank", {
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
    <>
      {bank ? (
        <Formik
          initialValues={{ ...bank }}
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
              errors.employeeNumber = "Required"
            }

            return errors
          }}
          onSubmit={(values, { setSubmitting }) => {
            setTimeout(async () => {
              const response = await updateBank(values)
              if (response.status != 200) {
                notifyError(response.data.error)
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
                  type="text"
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

              <button
                className={styles.btn}
                type="submit"
                disabled={isSubmitting}
              >
                Update
              </button>
            </form>
          )}
        </Formik>
      ) : (
        <></>
      )}
    </>
  )
}

export default UpdateBankForm
