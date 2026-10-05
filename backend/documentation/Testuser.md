## Testbenutzer und Student-IDs

Für die lokale Entwicklung und die Testumgebung werden feste ID-Bereiche verwendet, um die unterschiedlichen Benutzerrollen eindeutig voneinander zu trennen.

- Max Mustermann ID 100 -> "preferred_username" : 100 | Role -> admin
- Ernst Huber ID 101 -> "preferred_username" : 101 | Role -> fachstudent
- Jonas Müller ID 102 -> "preferred_username" : 102 | Role -> student


Beispiel:

- Keycloak-Benutzer `100` → Student mit `student_id = 100`
