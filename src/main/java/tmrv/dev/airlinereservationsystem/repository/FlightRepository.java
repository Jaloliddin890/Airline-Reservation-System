package tmrv.dev.airlinereservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmrv.dev.airlinereservationsystem.domains.Flight;

import java.util.List;


@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureAirport_Id(Long airportId);
}
