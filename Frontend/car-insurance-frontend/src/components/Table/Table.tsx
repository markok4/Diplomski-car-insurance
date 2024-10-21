import { Fragment, useEffect, useState } from "react"
import ReactPaginate from "react-paginate"
import { useTable } from "react-table"
import styles from "./Table.module.scss"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import {
  faCircleInfo,
  faTrash,
  faRotateLeft,
  faPencil,
  faCheckCircle,
} from "@fortawesome/free-solid-svg-icons"
import { Link, useNavigate } from "react-router-dom"
import Button from "../Button/Button"
import DialogBox from "../Dialog/Dialog"

type Props = {
  columns: {
    Header: string
    accessor: string
    Cell?: any
  }[]
  data: any
  entityName: string // Entity name to be used in urls for create, read and update redirects. (e.g. "{entity}/create","{entity}/:id/update", "{entity}/:id")
  onDelete: (id: number) => void // Callback function that triggers when delete is clicked
  onRestore: (id: number) => void // Callback function that triggers when restore is clicked
  // Pagination properties used when making a pageanated request from parent component
  itemsPerPage: number
  setItemsPerPage: (items: number) => void
  page: number
  setPage: (page: number) => void
  pages: number
  hideAddNewButton?: boolean
  hideActions?: boolean
  selectable?: boolean
  onSelect?: (id: number) => void
  selectedRow?: number
}

function Table(props: Props) {
  const currentItems = props.data
  const [selectedItem, setSelectedItem] = useState(-1)
  const [selectedRow, setSelectedRow] = useState(
    props.selectedRow ? props.selectedRow : -1,
  )
  let [isDeleteOpen, setIsDeleteOpen] = useState(false)
  let [isRestoreOpen, setIsRestoreOpen] = useState(false)
  const navigate = useNavigate()

  useEffect(() => {
    if (props.selectedRow) setSelectedRow(props.selectedRow)
  }, [props.selectedRow])

  function closeDeleteModal(sucess: boolean) {
    if (sucess) {
      props.onDelete(selectedItem)
    }
    setIsDeleteOpen(false)
  }

  function openDeleteModal() {
    setIsDeleteOpen(true)
  }

  function closeRestoreModal(sucess: boolean) {
    if (sucess) {
      props.onRestore(selectedItem)
    }
    setIsRestoreOpen(false)
  }

  function openRestoreModal() {
    setIsRestoreOpen(true)
  }

  const handlePageClick = (event: { selected: number }) => {
    props.setPage(event.selected)
  }

  const handleItemsPerPageChange = (
    event: React.ChangeEvent<HTMLSelectElement>,
  ) => {
    props.setItemsPerPage(Number(event.target.value))
  }

  const handleItemSelected = (id: number) => {
    if (props.onSelect) {
      props.onSelect(id)
    }
  }

  const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } =
    useTable({ columns: props.columns, data: currentItems })

  return (
    <>
      {props.hideAddNewButton ? undefined : (
        <Button
          className={styles["create-button"]}
          color="accent"
          onClick={() => {
            navigate(`/${props.entityName}/create`)
          }}
        >
          Add new
        </Button>
      )}

      <table {...getTableProps()} className={styles.table}>
        <thead>
          {headerGroups.map(headerGroup => (
            <tr
              className={styles["header-row"]}
              {...headerGroup.getHeaderGroupProps()}
            >
              {headerGroup.headers.map(column => (
                <th
                  className={styles["header-cell"]}
                  {...column.getHeaderProps()}
                >
                  {column.render("Header")}
                </th>
              ))}
              {props.hideActions ? undefined : (
                <th className={styles["header-cell"]}>Actions</th>
              )}
            </tr>
          ))}
        </thead>
        <tbody {...getTableBodyProps()}>
          {rows.map(row => {
            prepareRow(row)
            return (
              <tr
                className={`${styles.row} ${row.original.isDeleted ? styles.deleted : ""} ${props.selectable ? styles.selectable : ""} ${selectedRow == row.original.id ? styles.selected : ""}`}
                onClick={() => handleItemSelected(row.original.id as number)}
                {...row.getRowProps()}
              >
                {row.cells.map(cell => (
                  <td className={styles.cell} {...cell.getCellProps()}>
                    {cell.render("Cell")}
                  </td>
                ))}
                {props.hideActions ? undefined : (
                  <td className={`${styles.cell} ${styles["actions-cell"]}`}>
                    <Link to={`/${props.entityName}/${row.original.id}`}>
                      <FontAwesomeIcon
                        className={styles.info}
                        icon={faCircleInfo}
                      />
                    </Link>
                    <Link to={`/${props.entityName}/${row.original.id}/update`}>
                      <FontAwesomeIcon
                        className={styles.info}
                        icon={faPencil}
                      />
                    </Link>
                    <FontAwesomeIcon
                      className={styles["delete-btn"]}
                      icon={row.original.isDeleted ? faRotateLeft : faTrash}
                      onClick={() => {
                        setSelectedItem(row.original.id as number)
                        row.original.isDeleted
                          ? openRestoreModal()
                          : openDeleteModal()
                      }}
                    />
                  </td>
                )}
              </tr>
            )
          })}
        </tbody>
      </table>
      {props.data.length < 1 ? (
        <div className={styles.nodata}>No data found.</div>
      ) : undefined}
      <div className={styles["item-count"]}>
        <span className={styles.text}>Items per page: </span>
        <select
          className={styles.dropdown}
          value={props.itemsPerPage}
          onChange={handleItemsPerPageChange}
        >
          {[5, 10, 15, 20].map(value => (
            <option key={value} value={value}>
              {value}
            </option>
          ))}
        </select>
      </div>
      <ReactPaginate
        activeLinkClassName={styles.active}
        containerClassName={styles.pagination}
        disabledLinkClassName={styles.disabled}
        pageLinkClassName={styles.page}
        breakLabel="..."
        nextLabel=">"
        onPageChange={handlePageClick}
        pageRangeDisplayed={5}
        pageCount={props.pages}
        previousLabel="<"
        previousLinkClassName={styles["pagination-button"]}
        nextLinkClassName={styles["pagination-button"]}
        renderOnZeroPageCount={null}
      />
      <DialogBox
        isOpen={isRestoreOpen}
        closeModal={closeRestoreModal}
        label={`Restore ${props.entityName}?`}
        header={`Restore ${props.entityName}?`}
      />
      <DialogBox
        isOpen={isDeleteOpen}
        closeModal={closeDeleteModal}
        label={`Delete ${props.entityName}?`}
        header={`Delete ${props.entityName}?`}
      />
    </>
  )
}

export default Table
