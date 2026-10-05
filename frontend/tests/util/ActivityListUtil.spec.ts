import type { TaetigkeitenblockDTO } from "@/api/generated/api-spec/models";

import { describe, expect, it } from "vitest";

import { filterAndSortActivities, isSameDate } from "@/util/ActivityListUtil";

describe("ActivityListUtils", () => {
  describe("isSameDate", () => {
    it("returns true for the same date with different times", () => {
      const first = new Date("2026-09-24T08:00:00");
      const second = new Date("2026-09-24T15:30:00");

      expect(isSameDate(first, second)).toBe(true);
    });

    it("returns false for different days", () => {
      const first = new Date("2026-09-24T08:00:00");
      const second = new Date("2026-09-25T08:00:00");

      expect(isSameDate(first, second)).toBe(false);
    });

    it("returns false for different months", () => {
      const first = new Date("2026-09-24T08:00:00");
      const second = new Date("2026-10-24T08:00:00");

      expect(isSameDate(first, second)).toBe(false);
    });

    it("returns false for different years", () => {
      const first = new Date("2026-09-24T08:00:00");
      const second = new Date("2027-09-24T08:00:00");

      expect(isSameDate(first, second)).toBe(false);
    });
  });

  describe("filterAndSortActivities", () => {
    it("returns only activities for the selected date", () => {
      const selectedDate = new Date("2026-09-24T00:00:00");

      const activities: TaetigkeitenblockDTO[] = [
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: new Date("2026-09-24T00:00:00"),
            beginnZeit: "08:00",
            endeZeit: "10:00",
          },
          homeoffice: false,
        },
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: new Date("2026-09-25T00:00:00"),
            beginnZeit: "10:00",
            endeZeit: "12:00",
          },
          homeoffice: true,
        },
      ];

      const result = filterAndSortActivities(activities, selectedDate);

      expect(result).toHaveLength(1);
      expect(result.at(0)?.taetigkeitenblockID?.beginnZeit).toBe("08:00");
    });

    it("sorts activities by start time", () => {
      const selectedDate = new Date("2026-09-24T00:00:00");

      const activities: TaetigkeitenblockDTO[] = [
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: selectedDate,
            beginnZeit: "14:00",
            endeZeit: "16:00",
          },
          homeoffice: false,
        },
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: selectedDate,
            beginnZeit: "08:00",
            endeZeit: "10:00",
          },
          homeoffice: true,
        },
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: selectedDate,
            beginnZeit: "10:00",
            endeZeit: "12:00",
          },
          homeoffice: false,
        },
      ];

      const result = filterAndSortActivities(activities, selectedDate);

      expect(
        result.map((activity) => activity.taetigkeitenblockID?.beginnZeit)
      ).toEqual(["08:00", "10:00", "14:00"]);
    });

    it("ignores activities without a date", () => {
      const activities: TaetigkeitenblockDTO[] = [
        {
          taetigkeitenblockID: {
            studentId: 102,
            beginnZeit: "08:00",
            endeZeit: "10:00",
          },
          homeoffice: false,
        },
      ];

      const result = filterAndSortActivities(
        activities,
        new Date("2026-09-24T00:00:00")
      );

      expect(result).toEqual([]);
    });

    it("returns an empty array when no activities match", () => {
      const activities: TaetigkeitenblockDTO[] = [
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: new Date("2026-09-25T00:00:00"),
            beginnZeit: "08:00",
            endeZeit: "10:00",
          },
          homeoffice: false,
        },
      ];

      const result = filterAndSortActivities(
        activities,
        new Date("2026-09-24T00:00:00")
      );

      expect(result).toEqual([]);
    });

    it("returns an empty array for an empty activity list", () => {
      const result = filterAndSortActivities(
        [],
        new Date("2026-09-24T00:00:00")
      );

      expect(result).toEqual([]);
    });
  });
});
