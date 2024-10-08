package tmrv.dev.airlinereservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tmrv.dev.airlinereservationsystem.domains.Flight;
import tmrv.dev.airlinereservationsystem.domains.Ticket;

import java.util.List;

@Repository

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCustomer_Id(Long id);
    List<Ticket> findByFlight(Flight flight);
}