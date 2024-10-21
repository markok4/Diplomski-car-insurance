import React, { useEffect, useState } from "react"
import Table from "../../../components/Table/Table"
import styles from "./BankTable.module.scss"
import { Bank } from "../../../redux/types/Bank"
import {
  deleteBank,
  fetchBanks,
  restoreBank,
} from "../../../services/PaymentService"
import { Bounce, toast } from "react-toastify"

type Props = {}
function BankTable({}: Props) {
  const [banks, setBanks] = useState<Bank[]>()
  const [pages, setPages] = useState(0)
  const [itemsPerPage, setItemsPerPage] = useState(10)
  const [page, setPage] = useState(0)

  useEffect(() => {
    fetchBanks(page, itemsPerPage).then(res => {
      setBanks(res?.data.content)
      setPages(res?.data.totalPages)
      return res
    })
  }, [page, itemsPerPage])

  const notifySuccess = (message: string) =>
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
  const notifyError = (errorMessage: string) =>
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

  const data = React.useMemo(() => banks, [banks])

  const columns = React.useMemo(
    () => [
      { Header: "ID", accessor: "id" },
      { Header: "Name", accessor: "name" },
      {
        Header: "Logo",
        accessor: "logo",
        Cell: ({ value }: { value: string }) => (
          <div
            className={styles.logo}
            style={{ backgroundImage: `url(${value})` }}
          />
        ),
      },
      { Header: "Created At", accessor: "createdAt" },
      {
        Header: "Active",
        accessor: "isDeleted",
        Cell: ({ value }: { value: boolean }) => (!value).toString(),
      },
    ],
    [],
  )

  const handleDelete = async (id: number) => {
    const response = await deleteBank(id)
    if (response.status === 200) {
      notifySuccess("Successfully deleted country")
      fetchBanks(page, itemsPerPage).then(res => {
        setBanks(res?.data.content)
        setPages(res?.data.totalPages)
        return res
      })
    } else {
      notifyError(response.data.error.message)
    }
  }

  const handleRestore = async (id: number) => {
    const response = await restoreBank(id)
    if (response.status === 200) {
      notifySuccess("Successfully restored country")
      fetchBanks(page, itemsPerPage).then(res => {
        setBanks(res?.data.content)
        setPages(res?.data.totalPages)
        return res
      })
    } else {
      notifyError(response.data.error.message)
    }
  }

  return (
    <div className="main-wrapper">
      <h1 className="heading">Banks</h1>
      {data ? (
        <div className={styles.container}>
          <Table
            entityName="bank"
            columns={columns}
            data={data}
            onDelete={handleDelete}
            onRestore={handleRestore}
            pages={pages}
            itemsPerPage={itemsPerPage}
            page={page}
            setItemsPerPage={setItemsPerPage}
            setPage={setPage}
          />
        </div>
      ) : (
        <div className={styles.nodata}>No data found</div>
      )}
    </div>
  )
}

export default BankTable

function dispatch(arg0: Promise<import("axios").AxiosResponse<any, any>>) {
  throw new Error("Function not implemented.")
}
