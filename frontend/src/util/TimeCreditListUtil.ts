import type { SimpleZeitgutschriftDTO } from "@/api/generated/api-spec/models";

import { toDateKey } from "@/util/formatter";

export function filterZeitgutschriftenByDate(
  zeitgutschriften: SimpleZeitgutschriftDTO[],
  selectedDate: Date
): SimpleZeitgutschriftDTO[] {
  const selectedDateKey = toDateKey(selectedDate);

  return zeitgutschriften.filter((zeitgutschrift) => {
    const tag = zeitgutschrift.tag;

    return tag !== undefined && toDateKey(tag) === selectedDateKey;
  });
}
