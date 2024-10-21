import { Formik } from "formik"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { toast, Bounce } from "react-toastify"
import styles from "./Register.module.scss"
import CountrySelectModal from "../CountrySelectModal/CountrySelectModal"
import Button from "../../../components/Button/Button"
import { Country } from "../../../redux/types/Country"
import UserService from "../../../services/UserService"
import { City } from "../../../redux/types/City"
import CitySelectModal from "../CitySelectModal/CitySelectModal"
import { Address } from "../../../redux/types/Address"
import AddressSelectModal from "../AddressSelectModal/AddressSelectModal"
import { Zip } from "../../../redux/types/Zip"
import ZipSelectModal from "../ZipSelectModal/ZipSelectModal"
import { Contact } from "../../../redux/types/Contact"
import { User } from "../../../redux/types/User"

type Props = {}

function CreateCarForm({}: Props) {
  const navigate = useNavigate()

  const notifySuccess = () =>
    toast.success("Successfully registered user", {
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

  const [isCountryModalOpen, setCountryModalOpen] = useState(false)
  const [selectedCountry, setSelectedCountry] = useState<Country>()
  const [countries, setCountries] = useState<Country[]>([])

  const [isCityModalOpen, setCityModalOpen] = useState(false)
  const [selectedCity, setSelectedCity] = useState<City>()
  const [cities, setCities] = useState<City[]>([])

  const [isAddressModalOpen, setAddressModalOpen] = useState(false)
  const [selectedAddress, setSelectedAddress] = useState<Address>()
  const [addresses, setAddresses] = useState<Address[]>([])

  const [isZipModalOpen, setZipModalOpen] = useState(false)
  const [selectedZip, setSelectedZip] = useState<Zip>()
  const [zips, setZips] = useState<Zip[]>([])

  useEffect(() => {
    UserService.getAllCountries(0, 10).then(result =>
      setCountries(result.content),
    )
  }, [])

  return (
    <main className="main-wrapper">
      <h1 className="heading">Register</h1>
      <Formik
        initialValues={{
          firstName: null,
          lastName: null,
          jmbg: null,
          birth: null,
          gender: "MALE",
          maritialStatus: "SINGLE",
          role: "SUBSCRIBER",
          homePhone: null,
          mobilePhone: null,
          contactEmail: null,
          email: null,
          country: null,
          city: null,
          address: null,
          zip: null,
          isEnabled: true,
          password: null,
        }}
        validate={values => {
          const errors: any = {}
          if (!values.firstName) {
            errors.firstName = "First name is required"
          } else if (!values.lastName) {
            errors.lastName = "Last name is required"
          } else if (!values.jmbg) {
            errors.jmbg = "JMBG is required"
          } else if (!values.password) {
            errors.password = "Password is required"
          } else if (!values.birth) {
            errors.birth = "Birth is required"
          } else if (!values.homePhone) {
            errors.homePhone = "Home phone status is required"
          } else if (!values.mobilePhone) {
            errors.mobilePhone = "Mobile phone status is required"
          } else if (!values.contactEmail) {
            errors.contactEmail = "Contact email status is required"
          } else if (!values.email) {
            errors.email = "Email status is required"
          } else if (!values.country) {
            errors.country = "Country status is required"
          } else if (!values.city) {
            errors.city = "City status is required"
          } else if (!values.address) {
            errors.address = "Address status is required"
          } else if (!values.zip) {
            errors.zip = "Zip status is required"
          }

          return errors
        }}
        onSubmit={async (
          values,
          { setSubmitting, resetForm, validateForm, setFieldValue },
        ) => {
          try {
            console.log("DESILO SE")
            const formData = new FormData()
            const contact: Contact = {
              id: 0,
              homePhone: values.homePhone || "",
              mobilePhone: values.mobilePhone || "",
              email: values.contactEmail || "",
              isDeleted: false,
            }

            const formattedDate = new Date(Date.parse(values.birth!))

            const dataJson = {
              ...values,
              contact: contact,
              birth: formattedDate.toISOString(),
            }

            const response = await UserService.register(dataJson)

            notifySuccess()
          } catch (error: any) {
            notifyError(error.message)
            setSelectedAddress(undefined)
            setSelectedCity(undefined)
            setSelectedCountry(undefined)
            setSelectedZip(undefined)
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
            <div className={styles["input-container"]}>
              <div className={styles.input}>
                <div className={styles.data}>
                  First Name:
                  <input
                    className={styles["data-input"]}
                    type="text"
                    name="firstName"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.firstName ?? ""}
                  />
                  <div className={styles.validation}>
                    {errors.firstName && touched.firstName && errors.firstName}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.data}>
                  Last Name:
                  <input
                    className={styles["data-input"]}
                    type="text"
                    name="lastName"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.lastName ?? ""}
                  />
                  <div className={styles.validation}>
                    {errors.lastName && touched.lastName && errors.lastName}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.password}>
                  Password:
                  <input
                    className={styles["password-input"]}
                    type="password"
                    name="password"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.password ?? ""}
                  />
                  <div className={styles.validation}>
                    {errors.password && touched.password && errors.password}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.data}>
                  JMBG:
                  <input
                    className={styles["data-input"]}
                    type="text"
                    name="jmbg"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.jmbg ?? ""}
                  />
                  <div className={styles.validation}>
                    {errors.jmbg && touched.jmbg && errors.jmbg}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.data}>
                  Date of birth:
                  <input
                    className={styles["data-input"]}
                    type="date"
                    name="birth"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.birth ?? ""}
                  />
                  <div className={styles.validation}>
                    {errors.birth && touched.birth && errors.birth}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.data}>
                  Gender:
                  <select
                    className={styles.gender}
                    name="gender"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.gender ?? ""}
                  >
                    <option value="MALE">MALE</option>
                    <option value="FEMALE">FEMALE</option>
                    <option value="OTHER">OTHER</option>
                  </select>
                  <div className={styles.validation}>
                    {errors.gender && touched.gender && errors.gender}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.data}>
                  Maritial status:
                  <select
                    className={styles.maritialStatus}
                    name="maritialStatus"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.maritialStatus ?? ""}
                  >
                    <option value="SINGLE">SINGLE</option>
                    <option value="TAKEN">TAKEN</option>
                    <option value="DIVORCED">DIVORCED</option>
                    <option value="WIDOWED">WIDOWED</option>
                    <option value="OTHER">OTHER</option>
                  </select>
                  <div className={styles.validation}>
                    {errors.maritialStatus &&
                      touched.maritialStatus &&
                      errors.maritialStatus}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.data}>
                  Role:
                  <select
                    className={styles.role}
                    name="role"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.role ?? ""}
                  >
                    <option value="SUBSCRIBER">SUBSCRIBER</option>
                    <option value="DRIVER">DRIVER</option>
                    <option value="MANAGER">MANAGER</option>
                    <option value="CLAIMS_ADJUSTER">CLAIMS_ADJUSTER</option>
                    <option value="SALES_AGENT">SALES_AGENT</option>
                    <option value="CUSTOMER_SERVICE_REPRESENTATIVE">
                      CUSTOMER_SERVICE_REPRESENTATIVE
                    </option>
                    <option value="ADMINISTRATOR">ADMINISTRATOR</option>
                    <option value="CLAIM_HANDLER">CLAIM_HANDLER</option>
                  </select>
                  <div className={styles.validation}>
                    {errors.role && touched.role && errors.role}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.data}>
                  Home phone:
                  <input
                    className={styles["data-input"]}
                    type="text"
                    name="homePhone"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.homePhone ?? ""}
                  />
                  <div className={styles.validation}>
                    {errors.homePhone && touched.homePhone && errors.homePhone}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.data}>
                  Mobile phone:
                  <input
                    className={styles["data-input"]}
                    type="text"
                    name="mobilePhone"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.mobilePhone ?? ""}
                  />
                  <div className={styles.validation}>
                    {errors.mobilePhone &&
                      touched.mobilePhone &&
                      errors.mobilePhone}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.data}>
                  Contact email:
                  <input
                    className={styles["data-input"]}
                    type="text"
                    name="contactEmail"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.contactEmail ?? ""}
                  />
                  <div className={styles.validation}>
                    {errors.contactEmail &&
                      touched.contactEmail &&
                      errors.contactEmail}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div className={styles.data}>
                  Email:
                  <input
                    className={styles["data-input"]}
                    type="text"
                    name="email"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.email ?? ""}
                  />
                  <div className={styles.validation}>
                    {errors.email && touched.email && errors.email}
                  </div>
                </div>
              </div>

              <div className={styles.input}>
                <div>
                  Country:
                  <span className={styles["selected"]}>
                    {selectedCountry ? selectedCountry.name : ""}
                  </span>
                  <span className={styles.validation}>
                    {errors.country && touched.country && errors.country}
                  </span>
                </div>
                <Button
                  color="accent"
                  onClick={() => setCountryModalOpen(true)}
                >
                  Select country
                </Button>
              </div>

              {isCountryModalOpen && (
                <CountrySelectModal
                  isOpen={isCountryModalOpen}
                  countries={countries}
                  onSelect={country => {
                    setSelectedCountry(country)
                    setSelectedCity(undefined)
                    setFieldValue("country", country.id)
                    setCountryModalOpen(false)
                    UserService.getCitiesByCountry(0, 10, country.id).then(
                      result => setCities(result.content),
                    )
                  }}
                  onClose={() => setCountryModalOpen(false)}
                />
              )}

              <div className={styles.input}>
                <div>
                  City:
                  <span className={styles["selected"]}>
                    {selectedCity ? selectedCity.name : ""}
                  </span>
                  <span className={styles.validation}>
                    {errors.city && touched.city && errors.city}
                  </span>
                </div>
                <Button onClick={() => setCityModalOpen(true)} color="accent">
                  {" "}
                  Select city
                </Button>
              </div>

              {isCityModalOpen && (
                <CitySelectModal
                  isOpen={isCityModalOpen}
                  cities={cities}
                  onSelect={city => {
                    setSelectedCity(city)
                    setSelectedAddress(undefined)
                    setSelectedZip(undefined)
                    setFieldValue("city", city.id)
                    setCityModalOpen(false)
                    UserService.getAddressesByCity(0, 10, city.id).then(
                      result => setAddresses(result.content),
                    )
                    UserService.getZipsByCity(0, 10, city.id).then(result =>
                      setZips(result.content),
                    )
                  }}
                  onClose={() => setCityModalOpen(false)}
                />
              )}

              <div className={styles.input}>
                <div>
                  Address:
                  <span className={styles["selected"]}>
                    {selectedAddress ? selectedAddress.street : ""}{" "}
                    {selectedAddress ? selectedAddress.streetNumber : ""}
                  </span>
                  <span className={styles.validation}>
                    {errors.address && touched.address && errors.address}
                  </span>
                </div>
                <Button
                  onClick={() => setAddressModalOpen(true)}
                  color="accent"
                >
                  {" "}
                  Select address
                </Button>
              </div>

              {isAddressModalOpen && (
                <AddressSelectModal
                  isOpen={isAddressModalOpen}
                  addresses={addresses}
                  onSelect={address => {
                    console.log(address)
                    setSelectedAddress(address)
                    setFieldValue("address", address.id)
                    setAddressModalOpen(false)
                  }}
                  onClose={() => setAddressModalOpen(false)}
                />
              )}

              <div className={styles.input}>
                <div>
                  Zip:
                  <span className={styles["selected"]}>
                    {selectedZip ? selectedZip.zipNumber : ""}{" "}
                  </span>
                  <span className={styles.validation}>
                    {errors.zip && touched.zip && errors.zip}
                  </span>
                </div>
                <Button onClick={() => setZipModalOpen(true)} color="accent">
                  {" "}
                  Select zip
                </Button>
              </div>

              {isZipModalOpen && (
                <ZipSelectModal
                  isOpen={isZipModalOpen}
                  zips={zips}
                  onSelect={zip => {
                    setSelectedZip(zip)
                    setFieldValue("zip", zip.id)
                    setZipModalOpen(false)
                  }}
                  onClose={() => setZipModalOpen(false)}
                />
              )}

              <div className={styles.input}>
                <div className={styles.data}>
                  <div className={styles.active}>
                    Is Active:
                    <label>
                      <input
                        type="radio"
                        name="isEnabled"
                        value="yes"
                        checked={values.isEnabled}
                        onChange={() => setFieldValue("isEnabled", true)}
                      />
                      Yes
                    </label>
                    <label>
                      <input
                        type="radio"
                        name="isEnabled"
                        value="no"
                        checked={!values.isEnabled}
                        onChange={() => setFieldValue("isEnabled", false)}
                      />
                      No
                    </label>
                  </div>
                  <div className={styles.validation}>
                    {errors.isEnabled && touched.isEnabled && errors.isEnabled}
                  </div>
                </div>
              </div>

              <button
                className={styles.btn}
                type="submit"
                disabled={isSubmitting}
              >
                Register
              </button>
            </div>
          </form>
        )}
      </Formik>
    </main>
  )
}

export default CreateCarForm
