package pl.markowski.edoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.edoctor.model.entity.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
}
