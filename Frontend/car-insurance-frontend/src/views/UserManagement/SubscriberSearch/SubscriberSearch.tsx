import React, { useEffect, useState } from "react"
import styles from "./SubscriberSearch.module.scss"
import { faSearch, faSliders } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import "react-datepicker/dist/react-datepicker-cssmodules.css"
import UserService from "../../../services/UserService"
import { Subscriber } from "../../../redux/types/Subscriber"
import SubscriberView from "./SubscriberView/SubscriberView"
import { Button } from "antd"

type Props = {}

function SubscriberSearch({}: Props) {
  const [subscribers, setSubscribers] = useState<Subscriber[]>()
  const [pages, setPages] = useState(0)
  const [itemsPerPage, setItemsPerPage] = useState(10)
  const [page, setPage] = useState(0)
  const [searchParams, setSearchParams] = useState("")
  const [showLoader, setShowLoader] = useState(false)

  useEffect(() => {
    UserService.getSubscribers(page, itemsPerPage).then(result => {
      setSubscribers(result.content)
      setPages(result.totalPages)
      console.log(result.content)
    })
  }, [page, itemsPerPage])

  const columns = React.useMemo(
    () => [
      { Header: "Email", accessor: "email" },
      { Header: "First name", accessor: "firstName" },
      { Header: "Last name", accessor: "lastName" },
    ],
    [],
  )

  const search = () => {
    setShowLoader(true)
    UserService.search(page, itemsPerPage, searchParams).then(result => {
      setSubscribers(result.content)
      setPages(result.totalPages)
      setShowLoader(false)
    })
  }

  return (
    <div className="main-wrapper">
      <h1 className="heading">Subscribers</h1>
      <div className={styles["search-row"]}>
        <input
          type="text"
          placeholder="Search by first name, last name or email"
          value={searchParams}
          onChange={e => setSearchParams(e.target.value)}
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
      <div className={styles.subscribersList}>
        {subscribers && subscribers.length > 0 ? (
          subscribers.map(subscriber => (
            <SubscriberView key={subscriber.id} subscriber={subscriber} />
          ))
        ) : (
          <div className={styles.nodata}>No data found</div>
        )}
      </div>
    </div>
  )
}

export default SubscriberSearch
