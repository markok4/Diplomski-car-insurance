import styles from "./Pagination.module.scss"

const Pagination = ({ nPages, currentPage, setCurrentPage }: any) => {
  const pageNumbers = [...Array(nPages + 1).keys()].slice(1)

  const goToNextPage = () => {
    if (currentPage !== nPages) setCurrentPage(currentPage + 1)
  }
  const goToPrevPage = () => {
    if (currentPage !== 1) setCurrentPage(currentPage - 1)
  }
  return (
    <nav>
      <ul className={styles.pagination}>
        <li>
          <a
            className={styles["page-link"]}
            onClick={goToPrevPage}
            href="#"
            data-testid="pagination-prev"
          >
            Previous
          </a>
        </li>
        {pageNumbers.map(pgNumber => (
          <li
            key={pgNumber}
            className={`${currentPage == pgNumber && styles.active} `}
          >
            <a
              onClick={() => setCurrentPage(pgNumber)}
              className={styles["page-link"]}
              href="#"
            >
              {pgNumber}
            </a>
          </li>
        ))}
        <li>
          <a
            className={styles["page-link"]}
            onClick={goToNextPage}
            href="#"
            data-testid="pagination-next"
          >
            Next
          </a>
        </li>
      </ul>
    </nav>
  )
}

export default Pagination
