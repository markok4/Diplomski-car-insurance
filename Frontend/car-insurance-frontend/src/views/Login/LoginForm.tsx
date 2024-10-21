import React, { useRef } from "react"
import { Formik, Form, Field, ErrorMessage } from "formik"
import { Link, useNavigate } from "react-router-dom"
import { useDispatch } from "react-redux"
import { setCredentials } from "../../redux/slices/authSlice"
import { login } from "../../redux/thunk/authThunk"
import { toast, Bounce } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"
import styles from "./LoginForm.module.scss" // Replace with your actual styles
import { AppDispatch } from "../../redux/store"

const notifySuccess = (message: string) => {
  toast.success(message, {
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
}

const notifyError = (errorMessage: string) => {
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
}

interface LoginResponse {
  user: string
}

interface FormValues {
  username: string
  password: string
}

const Login: React.FC = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch<AppDispatch>()
  const errRef = useRef<HTMLDivElement>(null)

  const passwordRegex =
    /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@#$%!&*?])[A-Za-z\d@#$%!&*?]{8,}$/

  const emailRegex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/

  return (
    <Formik
      initialValues={{ username: "", password: "" }}
      validate={(values: FormValues) => {
        const errors: { username?: string; password?: string } = {}
        if (!values.username) {
          errors.username = "Required"
        } else if (!emailRegex.test(values.username)) {
          errors.username = "Invalid email format"
        }
        if (!values.password) {
          errors.password = "Required"
        } else if (!passwordRegex.test(values.password)) {
          errors.password = `Password must be at least:\n- 8 characters long and contain\n- 1 uppercase letter (A-Z)\n- 1 lowercase letter (a-z)\n- 1 digit (0-9)\n- 1 special symbol (@, #, \$, %, !, ?)`
        }
        return errors
      }}
      onSubmit={async (values: FormValues, { setSubmitting }) => {
        setSubmitting(true)
        try {
          const actionResult = await dispatch(login(values))
          if (login.fulfilled.match(actionResult)) {
            const userData: LoginResponse = actionResult.payload
            dispatch(setCredentials({ ...userData, user: values.username }))
            navigate("/")
            notifySuccess("Logged in successfully!")
          } else if (login.rejected.match(actionResult)) {
            notifyError(
              "Login failed. Please check your credentials and try again.",
            )
          }
        } catch (err) {
          notifyError(
            "Login failed. Please check your credentials and try again.",
          )
          console.error("Login error", err)
        }
        setSubmitting(false)
      }}
    >
      {({ isSubmitting }) => (
        <div className={styles.container}>
          <div ref={errRef} className={styles.errmsg} aria-live="assertive" />

          <h1 className={styles.title}>Login</h1>

          <Form className={styles.form}>
            <div className={styles.input}>
              <label htmlFor="username">Email:</label>
              <Field
                id="username"
                type="email"
                name="username"
                autoComplete="off"
                className={styles.field}
              />
              <ErrorMessage
                name="username"
                component="div"
                className={styles.validation}
              />
            </div>

            <div className={styles.input}>
              <label htmlFor="password">Password:</label>
              <Field
                id="password"
                type="password"
                name="password"
                className={styles.field}
              />
              <ErrorMessage
                name="password"
                component="div"
                render={msg => (
                  <div className={styles.validation}>
                    <pre>
                      <br />
                      {msg.split("\n").map((line, index) => (
                        <React.Fragment key={index}>
                          {line}
                          <br />
                        </React.Fragment>
                      ))}
                    </pre>
                  </div>
                )}
              />
            </div>

            <button
              className={styles.btn}
              type="submit"
              disabled={isSubmitting}
            >
              Sign In
            </button>
            <div className={styles.link}>
              <Link
                to="/register"
                style={{ color: "inherit", textDecoration: "none" }}
              >
                Don't have an account? Create one now.
              </Link>
            </div>
          </Form>
        </div>
      )}
    </Formik>
  )
}

export default Login
