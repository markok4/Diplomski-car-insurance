import React, { useEffect, useState } from "react"
import { Policy } from "../../../redux/types/Policy"
import policyService from "../../../services/PolicyService"
import styles from "./PolicySearch.module.scss"
import Table from "../../../components/Table/Table"
import DatePicker from "react-datepicker"
import Button from "../../../components/Button/Button"
import {
  faCalendarAlt,
  faSearch,
  faSliders,
} from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import "react-datepicker/dist/react-datepicker-cssmodules.css"
import { Model } from "../../../redux/types/Model"
import { Brand } from "../../../redux/types/Brand"
import ModelSelectModal from "../../CarManagement/ModelSelectModal/ModelSelectModal"
import ModelService from "../../../services/ModelService"
import BrandService from "../../../services/BrandService"
import BrandSelectModal from "./BrandSelectModal/BrandSelectModal"

type Props = {}

function PolicySearch({}: Props) {
  const [policies, setPolicies] = useState<Policy[]>()
  const [pages, setPages] = useState(0)
  const [itemsPerPage, setItemsPerPage] = useState(10)
  const [page, setPage] = useState(0)
  const [nameOrEmail, setNameOrEmail] = useState("")
  const [showAdvancedSearch, setShowAdvancedSearch] = useState(false)
  const [startDate, setStartDate] = useState<Date>()
  const [showLoader, setShowLoader] = useState(false)
  const [showDatePicker, setShowDatePicker] = useState(false)
  const [selectedModel, setSelectedModel] = useState<Model>()
  const [selectedBrand, setSelectedBrand] = useState<Brand>()
  const [isModelModalOpen, setIsModelModalOpen] = useState(false)
  const [isBrandModalOpen, setIsBrandModalOpen] = useState(false)
  const [models, setModels] = useState<Model[]>([])
  const [brands, setBrands] = useState<Brand[]>([])
  const [year, setYear] = useState<number>()

  useEffect(() => {
    policyService
      .search(
        page,
        itemsPerPage,
        nameOrEmail,
        startDate,
        selectedBrand?.id,
        selectedModel?.id,
        year,
      )
      .then(result => {
        setPolicies(result.content)
        setPages(result.totalPages)
      })
  }, [page, itemsPerPage])

  useEffect(() => {
    if (selectedBrand) {
      ModelService.getAllByBrand(selectedBrand.id!).then(result =>
        setModels(result),
      )
    }
  }, [selectedBrand])

  useEffect(() => {
    BrandService.getAllBrands().then(result => {
      setBrands(result)
    })
  }, [])

  const search = () => {
    setShowLoader(true)
    policyService
      .search(
        page,
        itemsPerPage,
        nameOrEmail,
        startDate,
        selectedBrand?.id,
        selectedModel?.id,
        year,
      )
      .then(result => {
        setPolicies(result.content)
        setPages(result.totalPages)
        setShowLoader(false)
      })
  }

  const toggleAdvancedSearch = () => {
    setShowAdvancedSearch(!showAdvancedSearch)
  }

  const data = React.useMemo(() => policies, [policies])

  const columns = React.useMemo(
    () => [
      { Header: "ID", accessor: "id" },
      { Header: "Signing Date", accessor: "dateSigned" },
      { Header: "Expiry Date", accessor: "expiringDate" },
      {
        Header: "Money Received Date",
        accessor: "moneyReceivedDate",
      },
      {
        Header: "Amount",
        accessor: "amount",
      },
      {
        Header: "Active",
        accessor: "isDeleted",
        Cell: ({ value }: { value: boolean }) => (!value).toString(),
      },
    ],
    [],
  )

  return (
    <div className={styles.container}>
      <h1 className="heading">Policies</h1>
      {data ? (
        <>
          <div className={styles["input-container"]}>
            <div className={styles["search-row"]}>
              <input
                type="text"
                placeholder="Search by name or email"
                value={nameOrEmail}
                onChange={e => setNameOrEmail(e.target.value)}
                className={`${styles.input} ${styles.search}`}
              />
              <Button
                color="primary"
                onClick={search}
                className={`${styles["search-btn"]} ${showLoader ? styles.load : ""}`}
                disabled={showLoader}
              >
                <FontAwesomeIcon icon={faSearch} />
              </Button>
            </div>
            <Button
              color="primary"
              onClick={toggleAdvancedSearch}
              className={styles["advanced-btn"]}
            >
              <FontAwesomeIcon icon={faSliders} />
            </Button>
          </div>

          {showAdvancedSearch && (
            <div className={styles["advanced-search"]}>
              <div className={styles["advanced-title"]}>Advanced search:</div>
              <div className={styles["advanced-row"]}>
                <label>
                  Date:
                  <span className={styles["selected-item"]}>
                    {startDate ? startDate.toLocaleDateString() : ""}
                  </span>
                </label>
                <DatePicker
                  selected={startDate}
                  onChange={date => setStartDate(date!)}
                  className={styles.date}
                  open={showDatePicker}
                  onClickOutside={() => setShowDatePicker(false)}
                  onSelect={() => setShowDatePicker(false)}
                />
                <Button
                  color="accent"
                  onClick={() => setShowDatePicker(true)}
                  className={styles["advanced-select-btn"]}
                >
                  Select Date
                </Button>
              </div>
              <div className={styles["advanced-select-row"]}>
                <label>
                  Car Brand:
                  <span className={styles["selected-item"]}>
                    {selectedBrand ? selectedBrand.name : ""}
                  </span>
                </label>
                <Button
                  color="accent"
                  onClick={() => setIsBrandModalOpen(true)}
                  className={styles["advanced-select-btn"]}
                >
                  Select Brand
                </Button>
              </div>
              <div className={styles["advanced-row"]}>
                <label>
                  Car Model:{" "}
                  <span className={styles["selected-item"]}>
                    {selectedModel ? selectedModel.name : ""}
                  </span>
                </label>
                <Button
                  color="accent"
                  onClick={() => {
                    setIsModelModalOpen(true)
                  }}
                  disabled={selectedBrand ? false : true}
                  className={styles["advanced-select-btn"]}
                >
                  Select Model
                </Button>
              </div>
              <div className={styles["advanced-row"]}>
                <label>Car Year:</label>
                <input
                  className={styles.year}
                  type="number"
                  name="year"
                  id="year"
                  onChange={e => {
                    setYear(Number.parseInt(e.target.value))
                  }}
                />
              </div>
            </div>
          )}
          <ModelSelectModal
            isOpen={isModelModalOpen}
            models={models}
            onSelect={model => {
              setSelectedModel(model)
              setIsModelModalOpen(false)
            }}
            onClose={() => setIsModelModalOpen(false)}
          />
          <BrandSelectModal
            isOpen={isBrandModalOpen}
            brands={brands}
            onSelect={brand => {
              setSelectedBrand(brand)
              setIsBrandModalOpen(false)
            }}
            onClose={() => setIsBrandModalOpen(false)}
          />
          <Table
            entityName="policy"
            columns={columns}
            data={data}
            onDelete={policyService.delete}
            onRestore={policyService.restore}
            pages={pages}
            itemsPerPage={itemsPerPage}
            page={page}
            setItemsPerPage={setItemsPerPage}
            setPage={setPage}
          />
        </>
      ) : (
        <div className={styles.nodata}>No data found</div>
      )}
    </div>
  )
}

export default PolicySearch
