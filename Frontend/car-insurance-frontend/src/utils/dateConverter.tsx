export function convertDateFormat(dateString: string) {
  const date = new Date(dateString)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, "0")
  const day = String(date.getDate()).padStart(2, "0")
  const formattedDate = `${year}-${month}-${day}`
  return formattedDate
}

export function convertToDateObject(dateString: string): string {
  const [datePart] = dateString.split("T")
  return datePart + "T00:00:00"
}
