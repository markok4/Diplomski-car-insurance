import { Dispatch, SetStateAction, useEffect, useState } from "react";
import styles from "./Currencies.module.scss"
import React from "react";
import Table from "../../../components/Table/Table";
import { Alert } from 'antd';
import { fetchAllCurrencies, toggleCurrencyDeletedStatus } from "../../../services/CurrencyService";

type Props = {}

function Currencies({}: Props) {

  const [data, setData]: [any[], Dispatch<SetStateAction<any[]>>] = useState<any[]>([]);  
    const [page, setPage] = useState(0); 
    const [pageSize, setPageSize] = useState(10);  
    const [totalPages, setTotalPages] = useState(0);
    const [deleteRestoreTrigger, setDeleteRestoreTrigger] = useState(false);
    const [alertInfo, setAlertInfo] = useState({ visible: false, type: '', message: '' });
    const handlePageSizeChange = (newPageSize : number) => {
        setPageSize(newPageSize);  
        setPage(0);
    };
    const handlePageChange = (newPageNumber: number) => {
        setPage(newPageNumber);  
    };

    const setAlertWithTimeout = (type : string, message : string, duration = 5000) => {
      setAlertInfo({ visible: true, type, message });
      setTimeout(() => {
        setAlertInfo({ visible: false, type: '', message: '' });
      }, duration);
    };

    const handleDelete = async (id: number) => {
      try {
        await toggleCurrencyDeletedStatus(id);
        setDeleteRestoreTrigger(prev => !prev);
        setAlertWithTimeout('success', 'Currency deleted successfully!');
      } catch (error) {
        console.error('Error deleting currency:', error);
        setAlertWithTimeout('error', 'Failed to delete currency.');
      }
    };

    const handleRestore = async (id : number) => {
      try {
        await toggleCurrencyDeletedStatus(id);
        setDeleteRestoreTrigger(prev => !prev);
        setAlertWithTimeout('success', 'Currency restored successfully!');
      } catch (error) {
        console.error('Error restoring currency:', error);
        setAlertWithTimeout('error', 'Failed to restore currency.');
      }
    };

    useEffect(() => {
        fetchAllCurrencies(page, pageSize)
        .then((currencyData) => {
          setData(currencyData.content);
          setTotalPages(currencyData.totalPages);
        });;
      }, [page, pageSize, deleteRestoreTrigger]);

    const columns = React.useMemo(
      () => [
      { Header: "ID", accessor: "id" }, 
      {
        Header: "Logo",
        accessor: "logoData",
        Cell: ({ value }: { value: string }) => 
              <div className={styles.logoCell}>
                <img src={`data:image/png;base64, ${value}`} alt="currency logo" style={{ width: '50px', display: 'inline-block' }} />
              </div>, // Render the image here
      },
      { Header: "Name", accessor: "name" },
      { Header: "Code", accessor: "code"},
      { Header: "Creation Date", accessor: "creationDate"},
      {
        Header: "Active",
        accessor: "isDeleted",
        Cell: ({ value }: { value: boolean }) => value ? "Inactive" : "Active",
      },
      ],
      [],
  )

  return (
    <div className="main-wrapper">
      <h1 className="heading">Currencies</h1>
      
      <div className={styles.table}>
        <Table
          entityName="currency"
          columns={columns}
          data={data}
          pages={totalPages}
          setItemsPerPage={handlePageSizeChange}
          setPage={handlePageChange}
          onDelete={handleDelete}
          onRestore={handleRestore} 
          itemsPerPage={pageSize} page={page}/>
      </div>
      {
        alertInfo.visible && (
          <div className={`${styles.alertContainer} ${alertInfo.visible ? '' : styles.hidden}`}>
            <div className={styles.alertItem}>
              <Alert
                message={alertInfo.message}
                type={alertInfo.type}
                showIcon
                closable
                onClose={() => setAlertInfo({ ...alertInfo, visible: false })}
                afterClose={() => setAlertInfo({ visible: false, type: '', message: '' })}
              />
            </div>
          </div>
        )
      }
    </div>

  )
}

export default Currencies
