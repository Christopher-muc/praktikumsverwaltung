import type { SimpleZeitgutschriftDTO } from "@/api/generated/api-spec/models";

import { describe, expect, it } from "vitest";

import { filterZeitgutschriftenByDate } from "@/util/TimeCreditListUtil.ts";

describe("TimeCreditUtils", () => {
  describe("filterZeitgutschriftenByDate", () => {
    it("returns Zeitgutschriften for the selected date", () => {
      const zeitgutschriften: SimpleZeitgutschriftDTO[] = [
        {
          id: 1,
          tag: new Date(2026, 8, 25),
          mengeMinuten: 60,
          grund: "Krankheit",
        },
        {
          id: 2,
          tag: new Date(2026, 8, 26),
          mengeMinuten: 30,
          grund: "Arzttermin",
        },
      ];

      const result = filterZeitgutschriftenByDate(
        zeitgutschriften,
        new Date(2026, 8, 25)
      );

      expect(result).toEqual([zeitgutschriften[0]]);
    });

    it("returns multiple Zeitgutschriften for the selected date", () => {
      const zeitgutschriften: SimpleZeitgutschriftDTO[] = [
        {
          id: 1,
          tag: new Date(2026, 8, 25),
          mengeMinuten: 60,
          grund: "Krankheit",
        },
        {
          id: 2,
          tag: new Date(2026, 8, 25),
          mengeMinuten: 30,
          grund: "Arzttermin",
        },
      ];

      const result = filterZeitgutschriftenByDate(
        zeitgutschriften,
        new Date(2026, 8, 25)
      );

      expect(result).toEqual(zeitgutschriften);
    });

    it("returns an empty array when no Zeitgutschrift matches", () => {
      const zeitgutschriften: SimpleZeitgutschriftDTO[] = [
        {
          id: 1,
          tag: new Date(2026, 8, 25),
          mengeMinuten: 60,
          grund: "Krankheit",
        },
      ];

      const result = filterZeitgutschriftenByDate(
        zeitgutschriften,
        new Date(2026, 8, 26)
      );

      expect(result).toEqual([]);
    });

    it("ignores Zeitgutschriften without a date", () => {
      const zeitgutschriften: SimpleZeitgutschriftDTO[] = [
        {
          id: 1,
          mengeMinuten: 60,
          grund: "Krankheit",
        },
      ];

      const result = filterZeitgutschriftenByDate(
        zeitgutschriften,
        new Date(2026, 8, 25)
      );

      expect(result).toEqual([]);
    });

    it("returns an empty array when the input is empty", () => {
      const result = filterZeitgutschriftenByDate([], new Date(2026, 8, 25));

      expect(result).toEqual([]);
    });
  });
});
