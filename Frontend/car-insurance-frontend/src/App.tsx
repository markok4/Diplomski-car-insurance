import { BrowserRouter as Router } from "react-router-dom"
import "./App.scss"
import Routing from "./components/navigation/Routing"
import Header from "./components/navigation/Header/Header"
import { Bounce, ToastContainer } from "react-toastify"
import "react-toastify/dist/ReactToastify.css"

const App = () => {
  return (
    <Router>
      <Header />
      <Routing />
      <ToastContainer
        position="bottom-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
        transition={Bounce}
        toastClassName="toast"
      />
    </Router>
  )
}

export default App
