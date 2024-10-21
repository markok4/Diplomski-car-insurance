import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faBars, faClose } from '@fortawesome/free-solid-svg-icons';
import styles from './MenuIcon.module.scss'

const MenuIcon = ({ onToggleMenu, isOpenMenu }: any) => {
  return (
    <button className={styles.hamburger} onClick={onToggleMenu}>
        <FontAwesomeIcon icon={isOpenMenu ? faClose : faBars} size="2x" />
    </button>
  )
}

export default MenuIcon