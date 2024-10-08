package tmrv.dev.airlinereservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmrv.dev.airlinereservationsystem.domains.Airport;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    Optional<Airport> findAirportById(Long id);
    List<Airport> findAirportByName(String city);
}