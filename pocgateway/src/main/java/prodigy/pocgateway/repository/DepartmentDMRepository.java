package prodigy.pocgateway.repository;

import prodigy.pocgateway.domain.DepartmentDM;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DepartmentDM entity.
 */
public interface DepartmentDMRepository extends JpaRepository<DepartmentDM,Long> {

}
