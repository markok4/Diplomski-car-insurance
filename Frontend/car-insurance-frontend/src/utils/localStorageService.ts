class LocalStorageService {
  setItem(key: string, value: string) {
    localStorage.setItem(key, value)
  }

  getItem(key: string) {
    return localStorage.getItem(key)
  }

  removeItem(key: string) {
    localStorage.removeItem(key)
  }

  setUser(user: string) {
    this.setItem("user", user)
  }

  getUser() {
    return this.getItem("user")
  }

  removeUser() {
    this.removeItem("user")
  }

  setToken(token: string) {
    this.setItem("token", token)
  }

  getToken() {
    return this.getItem("token")
  }

  removeToken() {
    this.removeItem("token")
  }

  setRole(role: any) {
    this.setItem("role", role)
  }

  getRole() {
    return this.getItem("role")
  }

  removeRole() {
    this.removeItem("role")
  }
}

export default new LocalStorageService()
