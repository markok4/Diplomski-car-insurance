import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import { Bounce, toast } from "react-toastify"
import { Currency } from "../../../redux/types/Currency"
import { PAYMENT_SERVICE_API_URL } from "../../../utils/consts"
import { Formik } from "formik"
import styles from "./UpdateCurrencyForm.module.scss"
import { createCurrency, updateCurrency } from "../../../services/CurrencyService"
import { fetchCurrencyById } from "../../../services/CurrencyService"
import { ToastService } from "../../../services/ToastService"
import * as yup from 'yup';

type Props = {}

function UpdateCurrencyForm({}: Props) {
  const navigate = useNavigate()

  const { id } = useParams()
  const [currency, setCurrency] = useState<Currency>()
  const [initialName, setInitialName] = useState<string>("")
  const [initialCode, setInitialCode] = useState<string>("")
  const [currentLogo, setCurrentLogo] = useState<string>("")

  const validationSchema = yup.object().shape({
    name: yup
      .string()
      .required('Required'), 
    code: yup
      .string()
      .length(3, 'Must be 3 characters long') 
      .required('Required'),
  });

  useEffect(() => {
    if (id) {
      const getCurrency = async () => {
        const currencyData = await fetchCurrencyById(Number(id));
        if(currencyData){
            setInitialName(currencyData.name);
            setInitialCode(currencyData.code);
            setCurrentLogo(currencyData.logoData);
            setCurrency(currencyData); 
        }
      }
      getCurrency()
    }
  }, [id])

  return (
    <>
      {currency ? (
        <Formik
          initialValues={{ name: initialName, code: initialCode, logo: null}}
          validationSchema={validationSchema}
          onSubmit={async (values, { setSubmitting, resetForm  }) => {

            try {
                const formData = new FormData();
                formData.append("name", values.name);
                formData.append("code", values.code);
                if (values.logo) {
                    formData.append("logo", values.logo);
                  }
      
                const updatedCurrency = await updateCurrency(currency.id, formData);
      
                if (updatedCurrency) {
                    ToastService.notifySuccess('Successfully updated the currency');
                    navigate('/currencies');
                  } else {
                    ToastService.notifyError('Failed to update currency');
                    resetForm();
                }
              } catch (error) {
                console.error('Error updating currency:', error);
                ToastService.notifyError('An error occurred while updating the currency');
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
                Update currency
              </h1>
              <form onSubmit={handleSubmit} className={styles.form}>
                  <div className={styles.currentLogo}>
                      <img src={`data:image/png;base64, ${currentLogo}`} alt={values.name} />
                  </div>
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
                      <label htmlFor="logo">Change logo:</label>
                      <input
                          type="file"
                          name="logo"
                          onChange={(event) => {
                              if (event.currentTarget.files && event.currentTarget.files.length > 0) {
                              setFieldValue("logo", event.currentTarget.files[0])
                              }
                          }}
                          onBlur={handleBlur}
                      />
                      <div className={styles.validation}>
                          {errors.logo && touched.logo && errors.logo}
                      </div>
                  </div>
                  <button className={styles.btn} type="submit" disabled={isSubmitting}>
                      Update
                  </button>
              </form>
            </div>
          )}
        </Formik>
      ) : (
        <></>
      )}
    </>
  )
}

export default UpdateCurrencyForm