import { Formik } from "formik"
import styles from "./UpdateProfile.module.scss"
import { useNavigate } from "react-router-dom"
import { useState, useEffect } from "react"
import PersonService from "../../../services/PersonService"
import { UserProfile } from "../../../redux/types/UserProfile"
import {
  convertDateFormat,
  convertToDateObject,
} from "../../../utils/dateConverter"
import Button from "../../../components/Button/Button"

function UpdateProfile() {
  const navigate = useNavigate()
  const [profile, setProfile] = useState<UserProfile>()
  const [imagePreview, setImagePreview] = useState<string>()

  const [profileImage, setProfileImage] = useState<string | undefined>()

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const userProfile = await PersonService.getProfile()
        setProfile(userProfile)
        try {
          const userProfileImage = await PersonService.getProfileImage(
            userProfile.profileImage,
          )
          setProfileImage(userProfileImage)
        } catch (error) {
          console.error("Error fetching profile image:", error)
        }
      } catch (error) {
        console.error("Error fetching profile data:", error)
      }
    }
    fetchProfile()
  }, [])

  const handleImageChange = (event: any, setFieldValue: any) => {
    const file = event.currentTarget.files[0]
    if (file) {
      setFieldValue("profileImage", file)
      const reader = new FileReader()
      reader.onloadend = () => {
        setImagePreview(reader.result as string)
      }
      reader.readAsDataURL(file)
    }
  }

  return (
    <>
      <div className={styles.container}>
        <h1>Update Profile</h1>
        {profile ? (
          <Formik
            initialValues={{ ...profile }}
            validate={values => {
              const errors: any = {}

              if (!values.firstName) {
                errors.firstName = "Required"
              } else if (values.firstName.length > 15) {
                errors.firstName = "Must be 15 characters or less"
              } else if (!/^[A-Za-z]+$/.test(values.firstName)) {
                errors.firstName = "Must contain only letters"
              }

              if (!values.lastName) {
                errors.lastName = "Required"
              } else if (values.lastName.length > 15) {
                errors.lastName = "Must be 15 characters or less"
              } else if (!/^[A-Za-z]+$/.test(values.lastName)) {
                errors.lastName = "Must contain only letters"
              }

              if (!/^[0-9]{11}$/.test(values.jmbg)) {
                errors.jmbg = "Must be exactly 11 digits"
              }

              const birthDate = new Date(values.birth)
              const eighteenYearsAgo = new Date()
              eighteenYearsAgo.setFullYear(eighteenYearsAgo.getFullYear() - 18)
              if (birthDate > eighteenYearsAgo) {
                errors.birth = "Must be at least 18 years old"
              }

              if (!values.gender) {
                errors.gender = "Required"
              }

              if (!values.maritialStatus) {
                errors.maritialStatus = "Required"
              }

              if (!values.profileImage) {
                errors.profileImage = "Required"
              }

              return errors
            }}
            onSubmit={async (values, { setSubmitting, resetForm }) => {
              const formData = new FormData()
              formData.append("firstName", values.firstName)
              formData.append("lastName", values.lastName)
              formData.append("jmbg", values.jmbg)
              formData.append("birth", convertToDateObject(values.birth))
              formData.append("gender", values.gender.toString())
              formData.append(
                "maritialStatus",
                values.maritialStatus.toString(),
              )
              formData.append("profileImage", values.profileImage)

              try {
                await PersonService.updateProfile(profile.id, formData)
                navigate("/profile")
              } catch (error) {
                console.error("Error updating profile:", error)
              } finally {
                setSubmitting(false)
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
              setFieldValue,
            }) => (
              <form onSubmit={handleSubmit} className={styles.form}>
                <div
                  className={styles["image-preview"]}
                  style={{
                    backgroundImage: `url('${imagePreview || "https://static-00.iconduck.com/assets.00/profile-circle-icon-2048x2048-cqe5466q.png"}')`,
                  }}
                />
                <div className={styles.input}>
                  <label htmlFor="firstName">First Name:</label>
                  <input
                    type="text"
                    name="firstName"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.firstName}
                  />
                  <div className={styles.validation}>
                    {errors.firstName && touched.firstName && errors.firstName}
                  </div>
                </div>

                <div className={styles.input}>
                  <label htmlFor="lastName">Last Name:</label>
                  <input
                    type="text"
                    name="lastName"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.lastName}
                  />
                  <div className={styles.validation}>
                    {errors.lastName && touched.lastName && errors.lastName}
                  </div>
                </div>

                <div className={styles.input}>
                  <label htmlFor="jmbg">JMBG:</label>
                  <input
                    type="text"
                    name="jmbg"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.jmbg}
                  />
                  <div className={styles.validation}>
                    {errors.jmbg && touched.jmbg && errors.jmbg}
                  </div>
                </div>

                <div className={styles.input}>
                  <label htmlFor="gender">Gender:</label>
                  <select
                    name="gender"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.gender}
                  >
                    <option value="MALE">MALE</option>
                    <option value="FEMALE">FEMALE</option>
                    <option value="OTHER">OTHER</option>
                  </select>
                  <div className={styles.validation}>
                    {errors.gender && touched.gender && errors.gender}
                  </div>
                </div>

                <div className={styles.input}>
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

                <div className={styles.input}>
                  Date of birth:<p></p>
                  <input
                    type="date"
                    name="birth"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={convertDateFormat(values.birth)}
                  />
                  <div className={styles.validation}>
                    {errors.birth && touched.birth && errors.birth}
                  </div>
                </div>

                {/* 
                <div className={styles.input}>
                  <label htmlFor="profileImage">Profile Image:</label>
                  <input
                    type="text"
                    name="profileImage"
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.profileImage}
                  />
                  <div className={styles.validation}>
                    {errors.profileImage &&
                      touched.profileImage &&
                      errors.profileImage}
                  </div>
                </div> */}

                <div className={styles.input}>
                  <input
                    type="file"
                    id="imageUpload"
                    name="profileImage"
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
                    {errors.profileImage &&
                      touched.profileImage &&
                      errors.profileImage}
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
      </div>
    </>
  )
}

export default UpdateProfile
