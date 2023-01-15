package pl.markowski.edoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.edoctor.model.entity.Visit;

import java.sql.Date;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findAllByDateAndDoctor_Id(Date day, Long doctorId);

    List<Visit> findAllByPatient_Id(Long id);
}
