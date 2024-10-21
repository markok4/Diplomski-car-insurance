import React, { useEffect, useState } from "react"
import Table from "../../../components/Table/Table"
import styles from "./ModelTable.module.scss"
import { Model } from "../../../redux/types/Model"
import modelService from "../../../services/ModelService"

type Props = {}
function ModelTable({}: Props) {
  const [models, setModels] = useState<Model[]>()
  const [pages, setPages] = useState(0)
  const [itemsPerPage, setItemsPerPage] = useState(10)
  const [page, setPage] = useState(0)

  useEffect(() => {
    modelService.getAll(page, itemsPerPage).then(result => {
      setModels(result.content)
      setPages(result.totalPages)
    })
  }, [page, itemsPerPage])

  const data = React.useMemo(() => models, [models])

  const columns = React.useMemo(
    () => [
      { Header: "ID", accessor: "id" },
      { Header: "Name", accessor: "name" },
      { Header: "Created At", accessor: "createdAt" },
      { Header: "Brand", accessor: "brand.name" },
      {
        Header: "Active",
        accessor: "isDeleted",
        Cell: ({ value }: { value: boolean }) => (!value).toString(),
      },
    ],
    [],
  )

  const handleDelete = async (id: any) => {
    try {
      const isDeleted = await modelService.handleDelete(id)
      if (isDeleted) {
        const { content: updatedModels, totalPages } =
          await modelService.getAll(page, itemsPerPage)
        setModels(updatedModels)
        setPages(totalPages)
      }
    } catch (error) {}
  }

  const handleRestore = async (id: any) => {
    try {
      const isRestored = await modelService.handleRestore(id)
      if (isRestored) {
        const { content: updatedModels, totalPages } =
          await modelService.getAll(page, itemsPerPage)
        setModels(updatedModels)
        setPages(totalPages)
      }
    } catch (error) {}
  }

  return (
    <div className="main-wrapper">
      <h1 className="heading">Models</h1>
      {data ? (
        <Table
          entityName="model"
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
      ) : (
        <div className={styles.nodata}>No data found</div>
      )}
    </div>
  )
}

export default ModelTable
