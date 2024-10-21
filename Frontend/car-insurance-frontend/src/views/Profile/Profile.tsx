import styles from "./Profile.module.scss"
import PersonService from "../../services/PersonService"
import { useEffect, useState } from "react"
import { UserProfile } from "../../redux/types/UserProfile"
import { Gender, MaritialStatus, UserRole } from "../../redux/types/User"
import Button from "../../components/Button/Button"
import { convertDateFormat } from "../../utils/dateConverter"
import { useNavigate } from "react-router-dom"

const Profile = () => {
  const navigate = useNavigate()

  const handleEditClick = () => {
    navigate("/profile-update")
  }

  const [profile, setProfile] = useState<UserProfile>()
  const [profileImage, setProfileImage] = useState<string | undefined>()

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const userProfile = await PersonService.getProfile()
        setProfile(userProfile)
        try {
          const userProfileImage = await PersonService.getProfileImage(
            userProfile.profileImage,
          )
          setProfileImage(userProfileImage)
        } catch (error) {
          console.error("Error fetching profile image:", error)
        }
      } catch (error) {
        console.error("Error fetching profile data:", error)
      }
    }
    fetchProfile()
  }, [])

  return (
    <main className="main-wrapper">
      <h1 className="heading">Profile</h1>
      {profile && (
        <div className={styles.card}>
          <div className={styles.topData}>
            <div className={styles.profileSection}>
              <img
                src={
                  profileImage
                    ? `data:image/png;base64, ${profileImage}`
                    : "https://static-00.iconduck.com/assets.00/profile-circle-icon-2048x2048-cqe5466q.png"
                }
                className={styles.profileImage}
              />
              <div className={styles.nameSection}>
                <h2 className={styles.firstName}>{profile.firstName}</h2>
                <h2 className={styles.lastName}>{profile.lastName}</h2>
                <p className={styles.email}>{profile.email}</p>
              </div>
            </div>
            <div className={styles.roleSection}>
              <p className={styles.userRole}>{profile.userRole}</p>
              <p
                className={`${styles.isEnabled} ${profile.isEnabled ? styles.isEnabled : styles.isNotEnabled}`}
              >
                {profile.isEnabled ? "ENABLED" : "DISABLED"}
              </p>
            </div>
          </div>
          <div className={styles.line}></div>
          <div className={styles.bottomData}>
            <div className={styles.userInfoList}>
              <div className={styles.column}>
                <p>
                  <strong>JMBG: </strong>
                  {profile.jmbg}
                </p>
                <p>
                  <strong>BIRTH: </strong>
                  {convertDateFormat(profile.birth)}
                </p>
              </div>
              <div className={styles.column}>
                <p>
                  <strong>GENDER: </strong>
                  {profile.gender}
                </p>
                <p>
                  <strong>MARTIAL STATUS: </strong>
                  {profile.maritialStatus}
                </p>
              </div>
            </div>
            <Button
              className={styles.btn}
              color="primary"
              onClick={handleEditClick}
            >
              Edit
            </Button>
          </div>
        </div>
      )}
    </main>
  )
}

export default Profile
