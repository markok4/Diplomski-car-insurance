import { useEffect, useState } from "react"

import debounce from "lodash.debounce"

const useWindowWidth = () => {
  const [width, setWidth] = useState<number>(window.innerWidth)

  useEffect(() => {
    const handleWindowSizeChange = () => {
      setWidth(window.innerWidth)
    }

    const debouncedHandleWindowSizeChange = debounce(handleWindowSizeChange, 10) // Debounce the event handler

    window.addEventListener("resize", debouncedHandleWindowSizeChange)

    return () => {
      window.removeEventListener("resize", debouncedHandleWindowSizeChange)
      debouncedHandleWindowSizeChange.cancel() // Cancel the debounced handler on cleanup
    }
  }, [])

  return width
}

// vraca true ukoliko je sirina ekrana manja od 768px, a false u suprotnom
export const isMobileUseEffect = () => {
  const width = useWindowWidth()
  return width <= 768
}
