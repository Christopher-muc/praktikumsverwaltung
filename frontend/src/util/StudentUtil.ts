import type { SimpleStudentDTO } from "@/api/generated/api-spec/models";





export function filterStudents(
  students: SimpleStudentDTO[],
  searchQuery: unknown
): SimpleStudentDTO[] {
  const search = String(searchQuery ?? "")
    .trim()
    .toLowerCase();

  if (!search) {
    return students;
  }

  return students.filter((student) => {
    const fullName = `${student.vorname ?? ""} ${student.nachname ?? ""}`
      .trim()
      .toLowerCase();

    return fullName.includes(search);
  });
}

export function getStudiengangId(
  studiengangQuery: unknown
): number | undefined {
  if (
    studiengangQuery === undefined ||
    studiengangQuery === null ||
    studiengangQuery === ""
  ) {
    return undefined;
  }

  const studiengangId = Number(studiengangQuery);

  if (!Number.isFinite(studiengangId)) {
    return undefined;
  }

  return studiengangId;
}

export function getStudentIdFromUsername(
  preferredUsername: string | undefined
): number | undefined {
  if (!preferredUsername) {
    return undefined;
  }

  const studentId = Number(preferredUsername);

  if (!Number.isInteger(studentId) || studentId <= 0) {
    return undefined;
  }

  return studentId;
}
