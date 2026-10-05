import type { PraktikumDTO } from "@/api/generated/api-spec/models";

export interface PraktikumFormData {
  wochenarbeitszeit: number;
  benoetigteWochen: number;
  beginnDatum: string;
  endeDatum: string;
}

export function hasPraktikumData(praktikum: PraktikumFormData): boolean {
  return (
    praktikum.wochenarbeitszeit !== 0 ||
    praktikum.benoetigteWochen !== 0 ||
    praktikum.beginnDatum !== "" ||
    praktikum.endeDatum !== ""
  );
}

export function hasCompletePraktikumDates(
  praktikum: PraktikumFormData
): boolean {
  return praktikum.beginnDatum !== "" && praktikum.endeDatum !== "";
}

export function createPraktikumDTO(
  studentId: number,
  praktikum: PraktikumFormData
): PraktikumDTO {
  return {
    studentId,
    beginnDatum: new Date(`${praktikum.beginnDatum}T00:00:00`),
    endeDatum: new Date(`${praktikum.endeDatum}T00:00:00`),
    benoetigteWochen: praktikum.benoetigteWochen,
    wochenarbeitszeit: praktikum.wochenarbeitszeit,
  };
}
