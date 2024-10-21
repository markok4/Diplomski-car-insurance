import { Formik } from "formik"
import React, { useEffect, useState } from "react"
import styles from "./UpdateCountryForm.module.scss"
import { Country } from "../../../redux/types/Country"
import { useNavigate, useParams } from "react-router-dom"
import { Bounce, toast } from "react-toastify"
import { USER_SERVICE_API_URL } from "../../../utils/consts"
import httpService from "../../../utils/httpService"

type Props = {}

function UpdateCountryForm({}: Props) {
  const navigate = useNavigate()
  const notifySuccess = () =>
    toast.success("Successfully updated country", {
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

  function resetForm() {
    throw new Error("Function not implemented.")
  }

  const { id } = useParams()
  const [country, setCountry] = useState<Country>()

  useEffect(() => {
    if (id) {
      const getCountry = async () => {
        const { data } = await httpService.get(
          USER_SERVICE_API_URL + `countries/${id}`,
        )
        setCountry({
          ...data,
        })
      }
      getCountry()
    }
  }, [id])

  return (
    <>
      {country ? (
        <Formik
          initialValues={{ ...country }}
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
          onSubmit={(values, { setSubmitting }) => {
            setTimeout(async () => {
              const response = await httpService.patch(
                USER_SERVICE_API_URL + "/countries/" + country.id,
                {
                  ...values,
                },
              )
              if (response.status != 200) {
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

export default UpdateCountryForm
