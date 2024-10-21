import React, { useEffect, useState } from "react"
import styles from "./Header.module.scss"
import MenuIcon from "../menu-icon/MenuIcon"
import { isMobileUseEffect } from "../../../utils/isMobileUseEffect"
import Logo from "../../Logo/Logo"
import Navigation from "../Navigation/Navigation"

const Header = () => {
  const isMobile = isMobileUseEffect()
  const [isOpenMenu, setOpenMenu] = useState(false)

  function toggleMenu() {
    if (isMobile) {
      setOpenMenu(wasOpenMenu => !wasOpenMenu)
    }
  }

  return (
    <header className={styles.header}>
      <div className={`main-wrapper ${styles["header-content"]}`}>
        <Logo />
        <MenuIcon onToggleMenu={toggleMenu} isOpenMenu={isOpenMenu} />
        <Navigation
          onToggleMenu={toggleMenu}
          isHidden={isMobile && !isOpenMenu}
        />
      </div>
    </header>
  )
}

export default Header
