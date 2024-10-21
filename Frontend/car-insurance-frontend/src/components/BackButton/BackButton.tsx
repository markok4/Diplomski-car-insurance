import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useNavigate } from 'react-router-dom';
import styles from "./BackButton.module.scss"

const BackButton = () => {
  const navigate = useNavigate();

  return (
    <button className={styles.btn} onClick={() => navigate(-1)}>
      <FontAwesomeIcon icon={faArrowLeft} size='xl' />
    </button>
  );
};

export default BackButton;