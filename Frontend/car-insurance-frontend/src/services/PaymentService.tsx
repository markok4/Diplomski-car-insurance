import React from "react"
import httpService from "../utils/httpService"

const baseUrl = "http://localhost:8080/payment-service/api/"

export const fetchAllCurrencies = async (
  page: number,
  pageSize: number,
  setData: React.Dispatch<React.SetStateAction<any[]>>,
  setTotalPages: React.Dispatch<React.SetStateAction<number>>,
) => {
  try {
    const response = await fetch(
      `${baseUrl}/currencies?page=${page}&size=${pageSize}`,
    )
    if (response.ok) {
      const currencyData = await response.json()
      setData(currencyData.content)
      const totalPages = currencyData.totalPages
      setTotalPages(totalPages)
    } else {
      console.error("Failed to fetch currency data")
    }
  } catch (error) {
    console.error("Error fetching currency data:", error)
  }
}

export const fetchCurrencyById = async (id: number) => {
  try {
    const response = await httpService.get(`${baseUrl}/currencies/${id}`)
    return response.data
  } catch (error) {
    console.error(`Error fetching currency with id ${id}:`, error)
    return null
  }
}

export const fetchBankById = async (id: number) => {
  try {
    const response = await httpService.get(`${baseUrl}/banks/${id}`)
    return response.data
  } catch (error) {
    console.error(`Error fetching bank with id ${id}:`, error)
    return null
  }
}

export const createBank = async (bank: any) => {
  return await httpService.post(`${baseUrl}banks`, bank)
}

export const updateBank = async (bank: any) => {
  return await httpService.put(`${baseUrl}banks`, bank)
}

export const fetchBanks = async (page: number, itemsPerPage: number) => {
  return await httpService.get(
    `${baseUrl}banks?page=${page}&size=${itemsPerPage}`,
  )
}

export const deleteBank = async (id: number) => {
  return await httpService.delete(`${baseUrl}banks/` + id)
}

export const restoreBank = async (id: number) => {
  return await httpService.patch(`${baseUrl}banks/` + id + `/restore`)
}

export const toggleCurrencyDeletedStatus = async (id: number) => {
  try {
    const response = await fetch(`${baseUrl}currencies/${id}/toggle-deleted`, {
      method: "PATCH",
    })
    if (!response.ok) {
      console.error(`Failed to toggle currency deleted status for id ${id}`)
    }
  } catch (error) {
    console.error(`Error toggling currency deleted status for id ${id}:`, error)
  }
}
