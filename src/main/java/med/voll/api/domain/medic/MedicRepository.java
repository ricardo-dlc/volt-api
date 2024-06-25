package med.voll.api.domain.medic;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
            LIMIT 1
            """)
    Medic getMedicBySpecialityAndNotInDate(Speciality speciality, LocalDateTime date);

    Boolean existsByIdAndActiveTrue(Long medicId);

}
