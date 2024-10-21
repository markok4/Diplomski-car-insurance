import { useEffect, useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import {
  fetchPolicies,
  fetchPoliciesBySalesAgent,
  fetchPoliciesBySubscriber,
} from "../../../redux/slices/policySlice"
import { AppDispatch } from "../../../redux/store"
import Button from "../../../components/Button/Button"
import Pagination from "../../../components/Pagination/Pagination"
import PolicyFlex from "../PolicyFlex/PolicyFlex"
import PolicyTable from "../PolicyTable/PolicyTable"
import styles from "./PolicyView.module.scss"
import { RootState } from "../../../redux/store"

const PolicyView = () => {
  const [cardView, setCardView] = useState(true)
  const [salesAgentMode, setSalesAgentMode] = useState(false) // New state for sales agent mode

  const { policies, loading, error, pages } = useSelector(
    (state: RootState) => state.policy,
  )
  const dispatch: AppDispatch = useDispatch()

  const [currentPage, setCurrentPage] = useState(1)
  const [policiesPerPage, setPoliciesPerPage] = useState(4)

  const [sortBy, setSortBy] = useState("")
  const userEmail = localStorage.getItem("user")
  const userRole = localStorage.getItem("role")

  useEffect(() => {
    if (userRole === "SALES_AGENT" && salesAgentMode && userEmail) {
      dispatch(
        fetchPoliciesBySalesAgent({
          page: currentPage,
          itemsPerPage: policiesPerPage,
          salesAgentEmail: userEmail,
          sortBy: sortBy,
        }),
      )
    } else if (userRole === "SUBSCRIBER" && userEmail) {
      dispatch(
        fetchPoliciesBySubscriber({
          page: currentPage,
          itemsPerPage: policiesPerPage,
        }),
      )
    } else {
      dispatch(
        fetchPolicies({
          page: currentPage,
          itemsPerPage: policiesPerPage,
          sortBy: sortBy,
        }),
      )
    }
  }, [
    dispatch,
    currentPage,
    policiesPerPage,
    salesAgentMode,
    userEmail,
    userRole,
    sortBy,
  ])

  function toggleView() {
    setCardView(wasTableView => !wasTableView)
  }

  function handleChangeSortBy(value: string) {
    setSortBy(value)
    setCurrentPage(1)
  }

  function handlePoliciesPerPageChange(
    event: React.ChangeEvent<HTMLSelectElement>,
  ) {
    const newPoliciesPerPage = parseInt(event.target.value, 10)
    setPoliciesPerPage(newPoliciesPerPage)
    setCurrentPage(1)
  }

  const toggleSalesAgentMode = () => {
    setCurrentPage(1)
    setSalesAgentMode(currentMode => !currentMode)
  }

  const salesAgentButton = userRole === "SALES_AGENT" && (
    <Button
      color={"gray"}
      className={styles["view-btn"]}
      onClick={toggleSalesAgentMode}
      data-testid="sales-agent-view-toggle"
    >
      {salesAgentMode ? "View All Policies" : "View My Policies"}
    </Button>
  )

  return (
    <main className="main-wrapper">
      <h1 className="heading">Policies</h1>
      {loading && <span className={styles.info}>Loading...</span>}
      {!loading && error && <span className={styles.info}>Error: {error}</span>}
      {!loading && policies.length ? (
        <>
          <div className={styles.header}>
            {salesAgentButton}
            {userRole !== "SUBSCRIBER" && (
              <>
                <span>Sort by: </span>
                <select
                  value={sortBy}
                  onChange={event => handleChangeSortBy(event.target.value)}
                  className={styles.sort}
                >
                  <option value="">--unsorted--</option>
                  <option value="date">Date signed</option>
                  <option value="plates">Car plates</option>
                </select>
                <select
                  value={policiesPerPage}
                  onChange={handlePoliciesPerPageChange}
                  className={styles["policies-per-page"]}
                >
                  {[4, 8, 12, 20].map(size => (
                    <option key={size} value={size}>
                      {size} per page
                    </option>
                  ))}
                </select>
              </>
            )}

            <Button
              color={"gray"}
              className={styles["view-btn"]}
              onClick={toggleView}
              data-testid="view-toggle"
            >
              {cardView ? <>Table view</> : <>Card view</>}
            </Button>
          </div>
          <section>
            {cardView ? (
              <PolicyFlex policies={policies} />
            ) : (
              <PolicyTable policies={policies} />
            )}
            <Pagination
              nPages={pages}
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
            />
          </section>
        </>
      ) : (
        ""
      )}
    </main>
  )
}
export default PolicyView
