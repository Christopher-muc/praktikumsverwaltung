export function toFirstLetterUppercase(text: string): string {
  return text ? text[0]?.toUpperCase() + text.slice(1).toLowerCase() : "";
}

export function toDateString(date: Date): string {
  return date ? date.toLocaleDateString("de-DE") : "";
}

export function toTimeString(date: Date): string {
  return date ? date.toLocaleTimeString("de-DE") : "";
}

export function toDateAndTimeString(date: Date): string {
  return date ? date.toLocaleString("de-DE") : "";
}

export function toDateKey(date: Date): string {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");

  return `${year}-${month}-${day}`;
}

export function toDateInputValue(value: Date | undefined): string {
  if (!value) {
    return "";
  }

  return toDateKey(value);
}
export function toLocalTimeString(time: string | undefined): string {
  if (!time) {
    return "-";
  }

  const parts = time.split(":");

  if (parts.length < 2 || parts.length > 3) {
    return "-";
  }

  const hours = Number(parts[0]);
  const minutes = Number(parts[1]);
  const seconds = parts[2] === undefined ? 0 : Number(parts[2]);

  if (
    !Number.isInteger(hours) ||
    !Number.isInteger(minutes) ||
    !Number.isInteger(seconds) ||
    hours < 0 ||
    hours > 23 ||
    minutes < 0 ||
    minutes > 59 ||
    seconds !== 0
  ) {
    return "-";
  }

  return `${String(hours).padStart(2, "0")}:${String(minutes).padStart(2, "0")}`;
}
