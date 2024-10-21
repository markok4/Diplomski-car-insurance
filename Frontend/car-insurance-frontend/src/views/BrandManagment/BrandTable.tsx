import React, { useState, useEffect } from "react"

import styles from "./BrandTable.module.scss"
import Table from "../../components/Table/Table"
import type { Brand } from "../../redux/types/Brand"
import httpService from "../../utils/httpService"
import BrandService from "../../services/BrandService"
import { useNavigate } from "react-router-dom"

type Props = {}
function BrandTable({}: Props) {
  const [brands, setBrands] = useState<Brand[]>()
  const [pages, setPages] = useState(0)
  const [itemsPerPage, setItemsPerPage] = useState(10)
  const [page, setPage] = useState(0)
  const navigate = useNavigate()
  useEffect(() => {
    fetchBrands()
      .then(res => {
        return res.data
      })
      .then(data => {
        setBrands(data.content)
        setPages(data.totalPages)
      })
  }, [page, itemsPerPage])

  async function fetchBrands() {
    return await httpService.get(
      `http://localhost:8080/car-service/api/brands?page=${page}&size=${itemsPerPage}`,
    )
  }

  const data = React.useMemo(() => brands, [brands])
  const columns = React.useMemo(
    () => [
      { Header: "ID", accessor: "id" },
      { Header: "Name", accessor: "name" },
      { Header: "Logo", accessor: "logoImage" },
      { Header: "Creation Date", accessor: "creationDate" },
      {
        Header: "Active",
        accessor: "isDeleted",
        Cell: ({ value }: { value: boolean }) => (!value).toString(),
      },
    ],
    [],
  )
  return (
    <div className="main-wrapper">
      <h1 className="heading">Brands</h1>
      {data ? (
        <Table
          entityName="brand"
          columns={columns}
          data={data}
          onDelete={(id: number) => {
            BrandService.deactivateBrand(id).then(() => {
              fetchBrands()
                .then(res => {
                  return res.data
                })
                .then(data => {
                  setBrands(data.content)
                  setPages(data.totalPages)
                })
            })
          }}
          onRestore={(id: number) => {
            BrandService.deactivateBrand(id).then(() => {
              fetchBrands()
                .then(res => {
                  return res.data
                })
                .then(data => {
                  setBrands(data.content)
                  setPages(data.totalPages)
                })
            })
          }}
          pages={pages}
          itemsPerPage={itemsPerPage}
          page={page}
          setItemsPerPage={setItemsPerPage}
          setPage={setPage}
        />
      ) : (
        <div className={styles.nodata}>No data found</div>
      )}
    </div>
  )
}

export default BrandTable
