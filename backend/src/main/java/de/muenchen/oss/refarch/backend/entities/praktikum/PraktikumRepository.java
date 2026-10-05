package de.muenchen.oss.refarch.backend.entities.praktikum;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PraktikumRepository extends JpaRepository<Praktikum, Integer> {
    Praktikum getPraktikumByStudentId(int i);
}
