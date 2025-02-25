export const getCurrentTime = () => {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, "0"); // Months are 0-based
  const day = String(now.getDate()).padStart(2, "0");
  const hours = String(now.getHours()).padStart(2, "0");
  const minutes = String(now.getMinutes()).padStart(2, "0");

  const minDateTime = `${year}-${month}-${day}T${hours}:${minutes}`;
  return minDateTime;
};

export const formatDate = (date) => {
  const dat = new Date(date);
  return new Intl.DateTimeFormat("en-GB", {
    day: "2-digit",
    month: "short",
    year: "numeric",
  }).format(dat);
};

export const isPast = (time) => {
  const appointmentTime = new Date(time);
  const currentTime = new Date();
  return currentTime > appointmentTime;
};
