import type {
  Studiengang,
  StudiengangDTO,
} from "@/api/generated/api-spec/models";

export function getStudiengangRoute(studiengangNr?: number) {
  if (studiengangNr === undefined) {
    return undefined;
  }

  return {
    path: "/students",
    query: {
      studiengang: studiengangNr,
    },
  };
}

export function getStudiengangId(
  studiengang: StudiengangDTO
): number | undefined {
  return studiengang.studiengangNr;
}

export function getAddedStudiengangIds(
  current: Studiengang[],
  original: Studiengang[]
): number[] {
  return current.flatMap((studiengang) => {
    const id = studiengang.studiengangNr;

    if (id === undefined) {
      return [];
    }

    const alreadyExists = original.some(
      (originalStudiengang) => originalStudiengang.studiengangNr === id
    );

    return alreadyExists ? [] : [id];
  });
}

export function getRemovedStudiengangIds(
  current: Studiengang[],
  original: Studiengang[]
): number[] {
  return original.flatMap((studiengang) => {
    const id = studiengang.studiengangNr;

    if (id === undefined) {
      return [];
    }

    const stillExists = current.some(
      (currentStudiengang) => currentStudiengang.studiengangNr === id
    );

    return stillExists ? [] : [id];
  });
}
