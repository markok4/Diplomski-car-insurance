import React, { useEffect, useState } from "react"
import Table from "../../../components/Table/Table"
import styles from "./CountryTable.module.scss"
import { USER_SERVICE_API_URL } from "../../../utils/consts"
import { useDispatch, useSelector } from "react-redux"
import {
  fetchCountries,
  setItemsPerPage,
  setPage,
} from "../../../redux/slices/countrySlice"
import { AppDispatch, RootState } from "../../../redux/store"
import { Bounce, toast } from "react-toastify"
import httpService from "../../../utils/httpService"

type Props = {}
function CountryTable({}: Props) {
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

  const { pages, page, itemsPerPage, countries } = useSelector(
    (state: RootState) => state.country,
  )
  const dispatch = useDispatch<AppDispatch>()

  useEffect(() => {
    dispatch(fetchCountries({ page, itemsPerPage }))
  }, [page, itemsPerPage])

  const data = React.useMemo(() => countries, [countries])

  const columns = React.useMemo(
    () => [
      { Header: "ID", accessor: "id" }, // accessor is the key in the data
      { Header: "Name", accessor: "name" },
      { Header: "Abbreviation", accessor: "abbreviation" },
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
    try {
      await httpService.delete(USER_SERVICE_API_URL + "countries/" + id)
      notifySuccess("Successfully deleted country")
      dispatch(fetchCountries({ page, itemsPerPage }))
    } catch (error: any) {
      notifyError(error.message)
    }
  }

  const handleRestore = async (id: number) => {
    try {
      await httpService.patch(USER_SERVICE_API_URL + "countries/restore/" + id)
      notifySuccess("Successfully restored country")
      dispatch(fetchCountries({ page, itemsPerPage }))
    } catch (error: any) {
      notifyError(error.message)
    }
  }

  return (
    <div className="main-wrapper">
      <h1 className="heading">Countries</h1>
      {data ? (
        <Table
          entityName="country"
          columns={columns}
          data={data}
          onDelete={handleDelete}
          onRestore={handleRestore}
          pages={pages}
          itemsPerPage={itemsPerPage}
          page={page}
          setItemsPerPage={count => dispatch(setItemsPerPage(count))}
          setPage={count => dispatch(setPage(count))}
        />
      ) : (
        <div className={styles.nodata}>No data found</div>
      )}
    </div>
  )
}

export default CountryTable
