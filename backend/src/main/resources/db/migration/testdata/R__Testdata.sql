/*truncate the_entity;*/
TRUNCATE TABLE zeitgutschrift, taetigkeitenblock, praktikum, studiengang_student, student, studiengang RESTART IDENTITY CASCADE;
-- Ein Student
insert into student (vorname, nachname) values ( 'Max', 'Mustermann');
insert into student (vorname, nachname) values ( 'Ernst', 'Huber');
insert into student (vorname, nachname) values ( 'Jonas', 'Müller');
insert into student (vorname, nachname) values ( 'Bernd', 'Baum');

insert into studiengang (studiengang_nr, name) values (100, 'Informatik');
insert into studiengang (studiengang_nr, name) values (101, 'Geschichte');
insert into studiengang (studiengang_nr, name) values (102, 'Jura');
insert into studiengang (studiengang_nr, name) values (103, 'Sportwissenschaften');

insert into studiengang_student (student_id, studiengang_nr) values (100, 100);
insert into studiengang_student (student_id, studiengang_nr) values (101, 101);
insert into studiengang_student (student_id, studiengang_nr) values (102, 102);
insert into studiengang_student (student_id, studiengang_nr) values (103, 103);


insert into praktikum (
    beginn_datum, ende_datum, student_id, wochenarbeitszeit, benoetigte_wochen
) values (
             '2024-06-01', '2024-08-31', 101, 20, 12
         );

insert into taetigkeitenblock (
    tag, beginn_Zeit, ende_Zeit, student_id, homeoffice
) values (
             '2024-06-03', '04:05', '12:10', 101, true
         );

insert into zeitgutschrift (
    id, tag, menge_minuten, grund, praktikum_student_id
) values (
             '1', '2024-06-03', 30, 'Krankheit', 101
         );

SELECT setval('student_student_id_seq', (SELECT MAX(student_id) FROM student));
SELECT setval('zeitgutschrift_id_seq', (SELECT MAX(id) FROM zeitgutschrift));
SELECT setval('studiengang_studiengang_nr_seq', (SELECT MAX(studiengang_nr) FROM studiengang));
