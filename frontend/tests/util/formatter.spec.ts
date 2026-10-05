import { describe, expect, it } from "vitest";

import {
  toDateAndTimeString,
  toDateKey,
  toDateString,
  toFirstLetterUppercase,
  toTimeString,
} from "@/util/formatter";

describe("formatter", () => {
  describe("toFirstLetterUppercase", () => {
    it("converts the first letter to uppercase", () => {
      expect(toFirstLetterUppercase("informatik")).toBe("Informatik");
    });

    it("converts remaining letters to lowercase", () => {
      expect(toFirstLetterUppercase("iNFORMATIK")).toBe("Informatik");
    });

    it("returns an empty string for an empty string", () => {
      expect(toFirstLetterUppercase("")).toBe("");
    });

    it("handles a single character", () => {
      expect(toFirstLetterUppercase("a")).toBe("A");
    });
  });

  describe("toDateString", () => {
    it("formats a date using the German locale", () => {
      const date = new Date(2026, 8, 24);

      expect(toDateString(date)).toBe("24.9.2026");
    });
  });

  describe("toTimeString", () => {
    it("formats a time using the German locale", () => {
      const date = new Date(2026, 8, 24, 14, 30, 0);

      expect(toTimeString(date)).toBe("14:30:00");
    });
  });

  describe("toDateAndTimeString", () => {
    it("formats date and time using the German locale", () => {
      const date = new Date(2026, 8, 24, 14, 30, 0);

      expect(toDateAndTimeString(date)).toBe("24.9.2026, 14:30:00");
    });
  });

  describe("toDateKey", () => {
    it("formats a date as YYYY-MM-DD", () => {
      const date = new Date(2026, 8, 24);

      expect(toDateKey(date)).toBe("2026-09-24");
    });

    it("adds leading zeros to month and day", () => {
      const date = new Date(2026, 0, 5);

      expect(toDateKey(date)).toBe("2026-01-05");
    });
  });
});
