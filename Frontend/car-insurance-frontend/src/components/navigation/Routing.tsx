import { Route, Routes } from "react-router-dom"
import Home from "../../views/Home/Home"
import Profile from "../../views/Profile/Profile"
import Proposals from "../../views/ProposalManagement/ProposalView/ProposalView"
import Claims from "../../views/Claims/Claims"
import CountryTable from "../../views/CountryManagement/CountryTable/CountryTable"
import BrandView from "../../views/BrandManagment/Brand/BrandView/BrandView"
import ModelForm from "../../views/ModelsManagement/ModelForm/ModelForm"
import ModelView from "../../views/ModelsManagement/ModelView/ModelView"
import BankTable from "../../views/BankManagement/BankTable/BankTable"
import BankCard from "../../views/BankManagement/BankCard/BankCard"
import ModelTable from "../../views/ModelsManagement/ModelTable/ModelTable"
import Currencies from "../../views/CurrencyManagement/Currencies/Currencies"
import CurrencyDetails from "../../views/CurrencyManagement/CurrencyDetails/CurrencyDetails"
import BrandTable from "../../views/BrandManagment/BrandTable"
import BankForm from "../../views/BankManagement/BankForm/BankForm"
import CountryView from "../../views/CountryManagement/CountryView/CountryView"
import BrandForm from "../../views/BrandManagment/BrandForm/BrandForm"
import UpdateCountryForm from "../../views/CountryManagement/UpdateCountryForm/UpdateCountryForm"
import CreateCountryForm from "../../views/CountryManagement/CreateCountryForm/CreateCountryForm"
import LoginForm from "../../views/Login/LoginForm"
import RequireAuth from "../../utils/RequireAuth"
import CreateCarForm from "../../views/CarManagement/CreateCarForm/CreateCarForm"
import CarTable from "../../views/CarManagment/CarTable/CarTable"
import CarCard from "../../views/CarManagement/CarCard/CarCard"
import CreateCurrencyForm from "../../views/CurrencyManagement/CreateCurrencyForm/CreateCurrencyForm"
import UpdateCurrencyForm from "../../views/CurrencyManagement/UpdateCurrencyForm/UpdateCurrencyForm"
import Register from "../../views/UserManagement/Register/Register"
import PolicySearch from "../../views/PolicyManagement/PolicySearch/PolicySearch"
import CreateProposalForm from "../../views/ProposalManagement/CreateProposalForm/CreateProposalForm"
import RequireRole from "../../utils/RequireRole"
import PolicyView from "../../views/PolicyManagement/PolicyView/PolicyView"
import SubscriberSearch from "../../views/UserManagement/SubscriberSearch/SubscriberSearch"
import UpdateProfile from "../../views/Profile/UpdateProfile/UpdateProfile"
import CreateSubscriberForm from "../../views/SubscriberManagement/CreateSubscriberForm/CreateSubscriberForm"
import PolicyDetails from "../../views/PolicyManagement/PolicyDetails/PolicyDetails"

const Routing = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      {/* <Route path="/profile" element={<Profile />} /> */}
      {/* <Route path="/proposals" element={<Proposals />} /> */}
      {/* <Route path="/claims" element={<Claims />} /> */}
      <Route path="/login" element={<LoginForm />} />

      <Route element={<RequireAuth />}>
        <Route path="/profile" element={<Profile />} />
        <Route path="/profile-update" element={<UpdateProfile />} />

        <Route path="/subscriber/search" element={<SubscriberSearch />} />

        <Route element={<RequireRole requiredRoles={["MANAGER"]} />}>
          <Route path="/register" element={<Register />} />
        </Route>
        <Route element={<RequireRole requiredRoles={["ADMINISTRATOR"]} />}>
          <Route path="/country/create" element={<CreateCountryForm />} />
          <Route path="/country/:id/update/" element={<UpdateCountryForm />} />
          <Route path="/country/:id/" element={<CountryView />} />
          <Route path="/countries" element={<CountryTable />} />

          <Route path="/brands" element={<BrandTable />} />
          <Route path="/brand/:id" element={<BrandView />} />
          <Route path="/brand/create" element={<BrandForm />} />
          <Route path="/brand/:id/update" element={<BrandForm />} />
          <Route path="/claims" element={<Claims />} />

          <Route path="/banks" element={<BankTable />} />
          <Route path="/bank/:id" element={<BankCard />} />
          <Route path="/bank/:id/update" element={<BankForm />} />
          <Route path="/bank/create" element={<BankForm />} />

          <Route path="/models" element={<ModelTable />} />
          <Route path="/model/:id" element={<ModelView />} />
          <Route path="/model/create" element={<ModelForm />} />
          <Route path="/model/:id/update" element={<ModelForm />} />

          <Route path="/currency/create" element={<CreateCurrencyForm />} />
          <Route
            path="/currency/:id/update/"
            element={<UpdateCurrencyForm />}
          />
          <Route path="/currencies" element={<Currencies />} />
          <Route path="/currency/:id" element={<CurrencyDetails />} />

          <Route path="/cars" element={<CarTable />} />
          <Route path="/car/:id" element={<CarCard />} />
          <Route path="/car/create" element={<CreateCarForm />} />

          <Route
            path="/proposal/create/:proposalId"
            element={<CreateProposalForm />}
          />
          <Route path="/subscriber/create" element={<CreateSubscriberForm />} />
        </Route>
        <Route
          element={
            <RequireRole requiredRoles={["SALES_AGENT", "ADMINISTRATOR"]} />
          }
        >
          <Route path="/policies/search" element={<PolicySearch />} />

          <Route path="/proposals" element={<Proposals />} />
        </Route>
        <Route
          element={
            <RequireRole
            requiredRoles={["SUBSCRIBER", "SALES_AGENT", "ADMINISTRATOR"]}
            />
          }
        >
          <Route path="/policies" element={<PolicyView />} />
          <Route path="/policy/:id" element={<PolicyDetails />} />
        </Route>
      </Route>
    </Routes>
  )
}

export default Routing
