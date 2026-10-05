import type { SimpleStudentDTO } from "@/api/generated/api-spec/models";

import { describe, expect, it } from "vitest";

import {
  filterStudents,
  getStudentIdFromUsername,
  getStudiengangId,
} from "@/util/StudentUtil";

describe("StudentUtils", () => {
  describe("filterStudents", () => {
    const students: SimpleStudentDTO[] = [
      {
        studentId: 1,
        vorname: "Max",
        nachname: "Mustermann",
      },
      {
        studentId: 2,
        vorname: "Anna",
        nachname: "Schmidt",
      },
      {
        studentId: 3,
        vorname: "Peter",
        nachname: "Müller",
      },
    ];

    it("returns all students when search query is empty", () => {
      expect(filterStudents(students, "")).toEqual(students);
    });

    it("returns all students when search query is undefined", () => {
      expect(filterStudents(students, undefined)).toEqual(students);
    });

    it("filters students by first name", () => {
      const result = filterStudents(students, "Anna");

      expect(result).toEqual([students[1]]);
    });

    it("filters students by last name", () => {
      const result = filterStudents(students, "Müller");

      expect(result).toEqual([students[2]]);
    });

    it("filters students by full name", () => {
      const result = filterStudents(students, "Max Mustermann");

      expect(result).toEqual([students[0]]);
    });

    it("ignores upper and lower case", () => {
      const result = filterStudents(students, "mAx MuStErMaNn");

      expect(result).toEqual([students[0]]);
    });

    it("ignores whitespace around the search query", () => {
      const result = filterStudents(students, "  Anna  ");

      expect(result).toEqual([students[1]]);
    });

    it("returns an empty array when no student matches", () => {
      const result = filterStudents(students, "NichtVorhanden");

      expect(result).toEqual([]);
    });
  });

  describe("getStudiengangId", () => {
    it("converts a string to a number", () => {
      expect(getStudiengangId("42")).toBe(42);
    });

    it("returns a number unchanged", () => {
      expect(getStudiengangId(42)).toBe(42);
    });

    it("returns undefined for undefined", () => {
      expect(getStudiengangId(undefined)).toBeUndefined();
    });

    it("returns undefined for null", () => {
      expect(getStudiengangId(null)).toBeUndefined();
    });

    it("returns undefined for an empty string", () => {
      expect(getStudiengangId("")).toBeUndefined();
    });

    it("returns undefined for an invalid number", () => {
      expect(getStudiengangId("abc")).toBeUndefined();
    });
  });

  describe("getStudentIdFromUsername", () => {
    it("converts a valid username to a student id", () => {
      expect(getStudentIdFromUsername("102")).toBe(102);
    });

    it("returns undefined when username is undefined", () => {
      expect(getStudentIdFromUsername(undefined)).toBeUndefined();
    });

    it("returns undefined for an empty username", () => {
      expect(getStudentIdFromUsername("")).toBeUndefined();
    });

    it("returns undefined when username is not a number", () => {
      expect(getStudentIdFromUsername("student102")).toBeUndefined();
    });

    it("returns undefined for a decimal number", () => {
      expect(getStudentIdFromUsername("10.5")).toBeUndefined();
    });

    it("returns undefined for zero", () => {
      expect(getStudentIdFromUsername("0")).toBeUndefined();
    });

    it("returns undefined for a negative number", () => {
      expect(getStudentIdFromUsername("-10")).toBeUndefined();
    });
  });
});
