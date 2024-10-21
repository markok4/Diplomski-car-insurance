import { toast, ToastOptions, Bounce } from 'react-toastify';

const toastOptions: ToastOptions = {
  position: "bottom-right",
  autoClose: 5000,
  hideProgressBar: false,
  closeOnClick: true,
  pauseOnHover: true,
  draggable: true,
  progress: undefined,
  theme: "light",
  transition: Bounce,
};

const notifySuccess = (message: string) => {
  toast.success(message, toastOptions);
};

const notifyError = (message: string) => {
  toast.error(message, toastOptions);
};

export const ToastService = {
  notifySuccess,
  notifyError
};