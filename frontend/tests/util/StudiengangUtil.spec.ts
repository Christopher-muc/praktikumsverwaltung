import { describe, expect, it } from "vitest";

import {
  getAddedStudiengangIds,
  getRemovedStudiengangIds,
  getStudiengangId,
  getStudiengangRoute,
} from "@/util/StudiengangUtil";

describe("StudiengangUtil", () => {
  describe("getStudiengangRoute", () => {
    it("returns undefined if no Studiengang number is provided", () => {
      expect(getStudiengangRoute()).toBeUndefined();
    });

    it("returns the student route with Studiengang query", () => {
      expect(getStudiengangRoute(5)).toEqual({
        path: "/students",
        query: {
          studiengang: 5,
        },
      });
    });
  });

  describe("getStudiengangId", () => {
    it("returns the Studiengang number", () => {
      const studiengang = {
        studiengangNr: 5,
      };

      expect(getStudiengangId(studiengang)).toBe(5);
    });
  });

  describe("getAddedStudiengangIds", () => {
    it("returns newly added Studiengang IDs", () => {
      const original = [{ studiengangNr: 1 }];
      const current = [{ studiengangNr: 1 }, { studiengangNr: 2 }];

      expect(getAddedStudiengangIds(current, original)).toEqual([2]);
    });

    it("returns an empty array if nothing was added", () => {
      const original = [{ studiengangNr: 1 }];
      const current = [{ studiengangNr: 1 }];

      expect(getAddedStudiengangIds(current, original)).toEqual([]);
    });
  });

  describe("getRemovedStudiengangIds", () => {
    it("returns removed Studiengang IDs", () => {
      const original = [{ studiengangNr: 1 }, { studiengangNr: 2 }];
      const current = [{ studiengangNr: 1 }];

      expect(getRemovedStudiengangIds(current, original)).toEqual([2]);
    });

    it("returns an empty array if nothing was removed", () => {
      const original = [{ studiengangNr: 1 }];
      const current = [{ studiengangNr: 1 }];

      expect(getRemovedStudiengangIds(current, original)).toEqual([]);
    });
  });
});