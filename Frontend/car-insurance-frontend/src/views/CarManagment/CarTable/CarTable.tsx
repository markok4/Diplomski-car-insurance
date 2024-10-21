import React, { useEffect, useState } from "react"
import BrandService from "../../../services/BrandService"
import CarService from "../../../services/CarService"
import type { Car } from "../../../redux/types/Car"
import styles from "./CarTable.module.scss"
import Table from "../../../components/Table/Table"

type Props = {}

function CarTable({}: Props) {
  const [cars, setCars] = useState<Car[]>([])
  const [pages, setPages] = useState(0)
  const [itemsPerPage, setItemsPerPage] = useState(10)
  const [page, setPage] = useState(0)

  useEffect(() => {
    fetchBrands()
  }, [page, itemsPerPage])

  async function fetchBrands() {
    try {
      const data = await CarService.getAllCars(page, itemsPerPage)
      setCars(data.content)
      setPages(data.totalPages)
    } catch (error) {
      console.log("error while getting cars")
    }
  }

  const data = React.useMemo(() => cars, [cars])
  const columns = React.useMemo(
    () => [
      { Header: "ID", accessor: "id" },
      { Header: "Year", accessor: "year" },
      {
        Header: "Image",
        accessor: "logoData",
        Cell: ({ value }: { value: string }) => (
          <div className={styles.logoCell}>
            <img
              src={`data:image/png;base64, ${value}`}
              alt="car image"
              style={{
                width: "100px",
                height: "60px",
                objectFit: "cover",
                objectPosition: "center",
                display: "inline-block",
              }}
            />
          </div>
        ), // Render the image here
      },
      { Header: "Model Name", accessor: "model.name" },
      { Header: "Brand Name", accessor: "model.brand.name" },
    ],
    [],
  )
  return (
    <div className="main-wrapper">
      <h1 className="heading">Cars</h1>
      {data && data.length > 0 ? (
        <Table
          entityName="car"
          columns={columns}
          data={data}
          onDelete={(id: number) => {
            //CarService.deleteCar(id)
          }}
          pages={pages}
          itemsPerPage={itemsPerPage}
          page={page}
          setItemsPerPage={setItemsPerPage}
          setPage={setPage}
          onRestore={function (id: number): void {
            throw new Error("Function not implemented.")
          }}
        />
      ) : (
        <div className={styles.nodata}>No data found</div>
      )}
    </div>
  )
}
export default CarTable
