import { useEffect, useState } from "react"
import { RootState, AppDispatch } from "../../../redux/store"
import { useDispatch, useSelector } from "react-redux"
import { fetchProposalsActive } from "../../../redux/slices/proposalSlice"
import styles from "./ProposalView.module.scss"
import Button from "../../../components/Button/Button"
import Pagination from "../../../components/Pagination/Pagination"
import ProposalFlex from "../ProposalFlex/ProposalFlex"
import ProposalTable from "../ProposalTable/ProposalTable"

const Proposals = () => {
  const [cardView, setCardView] = useState(true)

  const { proposals, loading, error } = useSelector(
    (state: RootState) => state.proposal,
  )
  const dispatch: AppDispatch = useDispatch()

  const [currentPage, setCurrentPage] = useState(1)
  const [proposalsPerPage] = useState(5)

  useEffect(() => {
    dispatch(fetchProposalsActive())
  }, [])

  const indexOfLastPolicy = currentPage * proposalsPerPage
  const indexOfFirstPolicy = indexOfLastPolicy - proposalsPerPage
  const currentProposals = proposals.content.slice(
    indexOfFirstPolicy,
    indexOfLastPolicy,
  )
  const nPages = Math.ceil(proposals.content.length / proposalsPerPage)

  function toggleView() {
    setCardView(wasTableView => !wasTableView)
  }

  return (
    <main className="main-wrapper">
      <h1 className="heading">Proposals</h1>
      {loading && <span className={styles.info}>Loading...</span>}
      {!loading && error && <span className={styles.info}>Error: {error}</span>}
      {!loading && proposals.content.length ? (
        <>
          <div className={styles.header}>
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
              <ProposalFlex proposals={currentProposals} />
            ) : (
              <ProposalTable proposals={currentProposals} />
            )}
            <Pagination
              nPages={nPages}
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

export default Proposals
