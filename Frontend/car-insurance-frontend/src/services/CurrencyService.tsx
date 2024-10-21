import React from "react"
import { CurrencyCreate } from "../redux/types/CurrencyCreate"
import { PAYMENT_SERVICE_API_URL } from "../utils/consts"

import { AxiosResponse } from "axios"
import httpService from "../utils/httpService"

const baseUrl = PAYMENT_SERVICE_API_URL + "currencies"

export const fetchAllCurrencies = async (page: number, pageSize: number) => {
  try {
    const response: AxiosResponse = await httpService.get(
      `${baseUrl}?page=${page}&size=${pageSize}`,
    )

    if (response.status === 200 && Array.isArray(response.data.content)) {
      // Check if response.data is an array
      const currencies = response.data.content
      // Map over each currency and fetch its logo data
      const currenciesWithLogos = await Promise.all(
        currencies.map(async (currency: any) => {
          try {
            const logoResponse: AxiosResponse = await httpService.get(
              `${baseUrl}/logos/${currency.logo}`,
            )
            return { ...currency, logoData: logoResponse.data } // Include logo data in currency object
          } catch (logoError) {
            console.error(
              `Error fetching logo for currency ${currency.id}:`,
              logoError,
            )
            return currency // Return original currency object if logo fetch fails
          }
        }),
      )
      return {
        content: currenciesWithLogos, // Set the processed currency data as 'content'
        totalPages: response.data.totalPages, // Set the total pages from the original response
      }
    } else {
      console.error("Failed to fetch valid currency data")
      return { content: [], totalPages: 0 }
    }
  } catch (error) {
    console.error("Error fetching currency data:", error)
    return { content: [], totalPages: 0 }
  }
}

export const fetchCurrencyById = async (id: number) => {
  try {
    const response = await fetch(`${baseUrl}/${id}`)
    if (response.ok) {
      const currency = await response.json() // Get the fetched currency data
      const logoResponse: AxiosResponse = await httpService.get(
        `${baseUrl}/logos/${currency.logo}`,
      )
      return { ...currency, logoData: logoResponse.data } // Include logo data in the currency object and return
    } else {
      console.error(`Failed to fetch currency with id ${id}`)
      return null // Return null for error case
    }
  } catch (error) {
    console.error(`Error fetching currency with id ${id}:`, error)
    return null // Return null for error case
  }
}

export const toggleCurrencyDeletedStatus = async (id: number) => {
  try {
    const response = await fetch(`${baseUrl}/${id}/toggle-deleted`, {
      method: "PATCH",
    })
    if (!response.ok) {
      console.error(`Failed to toggle currency deleted status for id ${id}`)
    }
  } catch (error) {
    console.error(`Error toggling currency deleted status for id ${id}:`, error)
  }
}

export const createCurrency = async (formData: FormData) => {
  try {
    const response = await fetch(`${baseUrl}`, {
      method: "POST",
      body: formData,
    })
    if (response.ok) {
      const createdCurrencyDto = await response.json()
      return createdCurrencyDto
    } else {
      console.error("Failed to create currency")
      return null
    }
  } catch (error) {
    console.error("Error creating currency:", error)
    return null
  }
}

export const updateCurrency = async (id: number, formData: FormData) => {
  try {
    const response = await fetch(
      `${PAYMENT_SERVICE_API_URL}/currencies/${id}`,
      {
        method: "PATCH",
        body: formData,
      },
    )
    if (response.ok) {
      const updatedCurrencyDto = await response.json()
      return updatedCurrencyDto
    } else {
      console.error("Failed to update currency")
      return null
    }
  } catch (error) {
    console.error("Error updating currency:", error)
    return null
  }
}
