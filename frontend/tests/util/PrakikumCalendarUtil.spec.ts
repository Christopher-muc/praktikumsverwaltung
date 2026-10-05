import type { FullPraktikumDTO } from "@/api/generated/api-spec/models";

import { describe, expect, it } from "vitest";

import { getCalendarEvents } from "@/util/PraktikumCalendarUtil";

describe("getCalendarEvents", () => {
  it("returns false when no Praktikum exists", () => {
    expect(getCalendarEvents(undefined, "2026-09-24")).toBe(false);
  });

  it("returns false when there are no events on the selected date", () => {
    const praktikum: FullPraktikumDTO = {
      taetigkeiten: [],
      zeitgutschriften: [],
    };

    expect(getCalendarEvents(praktikum, "2026-09-24")).toBe(false);
  });

  it("returns black when an activity exists on the selected date", () => {
    const praktikum: FullPraktikumDTO = {
      taetigkeiten: [
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: new Date(2026, 8, 24),
            beginnZeit: "08:00",
            endeZeit: "12:00",
          },
          homeoffice: false,
        },
      ],
      zeitgutschriften: [],
    };

    expect(getCalendarEvents(praktikum, "2026-09-24")).toEqual(["black"]);
  });

  it("returns yellow when a time credit exists on the selected date", () => {
    const praktikum: FullPraktikumDTO = {
      taetigkeiten: [],
      zeitgutschriften: [
        {
          id: 1,
          tag: new Date(2026, 8, 24),
          mengeMinuten: 60,
          grund: "Feiertag",
        },
      ],
    };

    expect(getCalendarEvents(praktikum, "2026-09-24")).toEqual(["yellow"]);
  });

  it("returns black and yellow when both event types exist", () => {
    const praktikum: FullPraktikumDTO = {
      taetigkeiten: [
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: new Date(2026, 8, 24),
            beginnZeit: "08:00",
            endeZeit: "12:00",
          },
          homeoffice: false,
        },
      ],
      zeitgutschriften: [
        {
          id: 1,
          tag: new Date(2026, 8, 24),
          mengeMinuten: 60,
          grund: "Feiertag",
        },
      ],
    };

    expect(getCalendarEvents(praktikum, "2026-09-24")).toEqual([
      "black",
      "yellow",
    ]);
  });

  it("ignores activities from another date", () => {
    const praktikum: FullPraktikumDTO = {
      taetigkeiten: [
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: new Date(2026, 8, 25),
            beginnZeit: "08:00",
            endeZeit: "12:00",
          },
          homeoffice: false,
        },
      ],
      zeitgutschriften: [],
    };

    expect(getCalendarEvents(praktikum, "2026-09-24")).toBe(false);
  });

  it("ignores activities without a date", () => {
    const praktikum: FullPraktikumDTO = {
      taetigkeiten: [
        {
          taetigkeitenblockID: {
            studentId: 102,
            beginnZeit: "08:00",
            endeZeit: "12:00",
          },
          homeoffice: false,
        },
      ],
      zeitgutschriften: [],
    };

    expect(getCalendarEvents(praktikum, "2026-09-24")).toBe(false);
  });

  it("ignores time credits without a date", () => {
    const praktikum: FullPraktikumDTO = {
      taetigkeiten: [],
      zeitgutschriften: [
        {
          id: 1,
          mengeMinuten: 60,
          grund: "Feiertag",
        },
      ],
    };

    expect(getCalendarEvents(praktikum, "2026-09-24")).toBe(false);
  });
});
