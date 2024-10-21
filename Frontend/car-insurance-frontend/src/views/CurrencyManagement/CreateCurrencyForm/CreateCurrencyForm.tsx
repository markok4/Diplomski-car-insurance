import { Formik } from "formik"
import styles from "./CreateCurrencyForm.module.scss"
import { useNavigate } from "react-router-dom"
import { Bounce, toast } from "react-toastify"
import { createCurrency } from "../../../services/CurrencyService"

type Props = {}

function CreateCurrencyForm({}: Props) {
  const navigate = useNavigate()
  const notifySuccess = () =>
    toast.success("Successfully created a currency", {
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
      initialValues={{ name: "", code: "", logo: null }}
      validate={values => {
        const errors: any = {}

        if (!values.name) {
          errors.name = "Required"
        } else if (!values.code) {
          errors.code = "Required"
        } else if (values.code.length != 3) {
          errors.code = "Must be 3 characters long"
        } else if (!values.logo) {
          errors.logo = "Required"
        }

        return errors
      }}
      onSubmit={async (values, { setSubmitting, resetForm }) => {
        try {
          const formData = new FormData();
          formData.append("name", values.name);
          formData.append("code", values.code);
          formData.append("logo", values.logo || '');

          const createdCurrency = await createCurrency(formData);

          if (createdCurrency) {
            notifySuccess();
            navigate('/currencies');
          } else {
            notifyError('Failed to create currency');
            resetForm();
          }
        } catch (error) {
          console.error('Error creating currency:', error);
          notifyError('An error occurred while creating the currency');
        } finally {
          setSubmitting(false);
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

        setFieldValue,

        isSubmitting,
      }) => (
        <div className={styles.container}>
          <h1 className={styles.title}>
              Add new currency
          </h1>
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
              <label htmlFor="code">Code:</label>
              <input
                type="text"
                name="code"
                onChange={handleChange}
                onBlur={handleBlur}
                value={values.code}
              />

              <div className={styles.validation}>
                {errors.code &&
                  touched.code &&
                  errors.code}
              </div>
            </div>

            <div className={styles.input}>
              <label htmlFor="logo">Logo:</label>
              <input
                type="file" // Use file input type
                name="logo"
                onChange={(event) => { 
                  if (event.currentTarget.files && event.currentTarget.files.length > 0) {
                    setFieldValue("logo", event.currentTarget.files[0]);
                  }} // Update the logo value with the selected file
                }
                onBlur={handleBlur}
              />

              <div className={styles.validation}>
                {errors.logo &&
                  touched.logo &&
                  errors.logo}
              </div>
            </div>

            <button className={styles.btn} type="submit" disabled={isSubmitting}>
              Create
            </button>
          </form>
        </div>
      )}
    </Formik>
  )
}

export default CreateCurrencyForm
