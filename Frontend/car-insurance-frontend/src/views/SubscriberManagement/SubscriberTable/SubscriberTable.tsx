import React, { useEffect, useState } from "react"
import { Subscriber } from "../../../redux/types/Subscriber"
import subscriberService from "../../../services/SubscriberService"
import styles from "./SubscriberTable.module.scss"
import Table from "../../../components/Table/Table"

type Props = {
  selectable: boolean
  onSelect?: (id: number) => void
  selectedRow?: number
}

function SubscriberTable({ selectable, onSelect, selectedRow }: Props) {
  const [subscribers, setSubscribers] = useState<Subscriber[]>()
  const [pages, setPages] = useState(0)
  const [itemsPerPage, setItemsPerPage] = useState(10)
  const [page, setPage] = useState(0)

  useEffect(() => {
    subscriberService.getAll(page, itemsPerPage).then(result => {
      setSubscribers(result.content)
      setPages(result.totalPages)
    })
  }, [page, itemsPerPage])

  const data = React.useMemo(() => subscribers, [subscribers])

  const columns = React.useMemo(
    () => [
      { Header: "ID", accessor: "id" },
      { Header: "First Name", accessor: "firstName" },
      { Header: "Last Name", accessor: "lastName" },
      {
        Header: "Email",
        accessor: "email",
      },
      {
        Header: "Role",
        accessor: "roleName",
      },
      {
        Header: "Enabled",
        accessor: "isEnabled",
        Cell: ({ value }: { value: boolean }) => value.toString(),
      },
    ],
    [],
  )

  return (
    <div className={styles.container}>
      {data ? (
        <Table
          entityName="subscriber"
          columns={columns}
          data={data}
          onDelete={() => {}}
          onRestore={() => {}}
          pages={pages}
          itemsPerPage={itemsPerPage}
          page={page}
          setItemsPerPage={setItemsPerPage}
          setPage={setPage}
          hideActions={true}
          hideAddNewButton={true}
          selectable={selectable}
          onSelect={onSelect}
          selectedRow={selectedRow}
        />
      ) : (
        <div className={styles.nodata}>No data found</div>
      )}
    </div>
  )
}

export default SubscriberTable
