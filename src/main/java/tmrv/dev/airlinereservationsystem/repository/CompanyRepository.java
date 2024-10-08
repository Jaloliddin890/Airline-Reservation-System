package tmrv.dev.airlinereservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmrv.dev.airlinereservationsystem.domains.Company;

import java.util.Optional;

@Repository

public interface CompanyRepository extends JpaRepository<Company, Long> {


    Optional<Company> findCompanyById(Long id);
}