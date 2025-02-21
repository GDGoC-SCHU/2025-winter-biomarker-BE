package gdgoc.biomarker.users.repository;

import gdgoc.biomarker.users.entity.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthDataRepository extends JpaRepository<HealthData, Long> {

}
