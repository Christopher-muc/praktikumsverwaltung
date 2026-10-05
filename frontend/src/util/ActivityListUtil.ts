import type { TaetigkeitenblockDTO } from "@/api/generated/api-spec/models";

export function isSameDate(first: Date, second: Date): boolean {
  return (
    first.getFullYear() === second.getFullYear() &&
    first.getMonth() === second.getMonth() &&
    first.getDate() === second.getDate()
  );
}

export function filterAndSortActivities(
  taetigkeiten: TaetigkeitenblockDTO[],
  selectedDate: Date
): TaetigkeitenblockDTO[] {
  return taetigkeiten
    .filter((taetigkeit) => {
      const tag = taetigkeit.taetigkeitenblockID?.tag;

      if (!tag) {
        return false;
      }

      return isSameDate(tag, selectedDate);
    })
    .sort((a, b) => {
      const first = a.taetigkeitenblockID?.beginnZeit ?? "";

      const second = b.taetigkeitenblockID?.beginnZeit ?? "";

      return first.localeCompare(second);
    });
}
