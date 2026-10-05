package de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("PMD.DataClass")
public final class ExceptionMessageConstants {
    public static final String STUDENT_NOT_FOUND = "Could not find student with id %s";
    public static final String PRAKTIKUM_NOT_FOUND = "Could not find praktikum with id %s";
    public static final String ZEITGUTSCHRIFT_NOT_FOUND = "Could not find Zeitgutschrift with id %s";
    public static final String TAETIGKEIT_NOT_FOUND = "Could not find any Taetigkeit with id %s and Date %s";
    public static final String STUDIENGANG_NOT_FOUND = "Could not find Studiengang with id %s";
    public static final String STUDIENGANG_NOT_FOUND_WITH_STUDENT = "Student with id %s does not have Studiengang with id %s";

    public static final String PRAKTIKUM_ALREADY_EXISTS = "A Praktikum for the student with id %s already exists";
    public static final String STUDIENGANG_ALREADY_EXISTS = "Studiengang with id %s for the student with id %s already exists";

    public static final String STUDIENGANG_NOT_EMPTY = "There are still students with Studiengang %s";

    public static final String ENDEZEIT_NOT_AFTER_ANFANGSZEIT = "Endzeit muss nach der Anfangszeit liegen";
    public static final String ENDEDATUM_NOT_AFTER_ANFANGSDATUM = "Enddatum muss nach dem Anfangsdatum liegen";
    public static final String DATE_NOT_DURING_INTERNSHIP = "Das Datum liegt außerhalb des Praktikumszeitraums";
    public static final String CHECK_FORMAT = "Die Anfrage konnte nicht verarbeitet werden. Bitte Format prüfen";
}
