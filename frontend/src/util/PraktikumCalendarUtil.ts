import type { FullPraktikumDTO } from "@/api/generated/api-spec/models";

import { toDateKey } from "@/util/formatter";

export function getCalendarEvents(
  praktikum: FullPraktikumDTO | undefined,
  date: string
): string[] | false {
  if (!praktikum) {
    return false;
  }

  const colors: string[] = [];

  const hasTaetigkeit =
    praktikum.taetigkeiten?.some((taetigkeit) => {
      const tag = taetigkeit.taetigkeitenblockID?.tag;

      if (!tag) {
        return false;
      }

      return toDateKey(tag) === date;
    }) ?? false;

  const hasZeitgutschrift =
    praktikum.zeitgutschriften?.some((zeitgutschrift) => {
      if (!zeitgutschrift.tag) {
        return false;
      }

      return toDateKey(zeitgutschrift.tag) === date;
    }) ?? false;

  if (hasTaetigkeit) {
    colors.push("black");
  }

  if (hasZeitgutschrift) {
    colors.push("yellow");
  }

  return colors.length > 0 ? colors : false;
}
