package med.voll.api.domain.medic;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public interface MedicRepository extends JpaRepository<Medic, Long> {

    Page<Medic> findByActiveTrue(Pageable page);

    @Query("""
            SELECT m
            FROM Medic m
            LEFT JOIN Appointment a ON a.medic = m AND a.date = :date
            WHERE
                m.active = true
                AND m.speciality = :speciality
                AND a.id IS NULL
            ORDER BY FUNCTION('RAND')
            """)
    Medic getMedicBySpecialityAndNotInDate(Speciality speciality, LocalDateTime date);

    Boolean existsByIdAndActiveTrue(Long medicId);

}
