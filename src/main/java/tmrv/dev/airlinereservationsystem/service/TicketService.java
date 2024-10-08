package tmrv.dev.airlinereservationsystem.service;


import org.springframework.stereotype.Service;
import tmrv.dev.airlinereservationsystem.domains.Flight;
import tmrv.dev.airlinereservationsystem.domains.Status;
import tmrv.dev.airlinereservationsystem.domains.Ticket;
import tmrv.dev.airlinereservationsystem.domains.User;
import tmrv.dev.airlinereservationsystem.dto.*;
import tmrv.dev.airlinereservationsystem.repository.FlightRepository;
import tmrv.dev.airlinereservationsystem.repository.TicketRepository;
import tmrv.dev.airlinereservationsystem.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;


    public TicketService(TicketRepository ticketRepository, FlightRepository flightRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    public TicketDto purchaseTicket(Long flightId, Long userId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new RuntimeException("Flight not found"));
        User customer = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (flight.getSeatsAvailable() <= 0) {
            throw new RuntimeException("Seats not available");
        }

        Ticket ticket = new Ticket();
        ticket.setFlight(flight);
        ticket.setCustomer(customer);
        ticket.setPurchaseDate(LocalDateTime.now());
        ticket.setStatus(Status.BOOKED);

        flight.setSeatsAvailable(flight.getSeatsAvailable() - 1);
        flightRepository.save(flight);

        Ticket saveDTicket = ticketRepository.save(ticket);

        return new TicketDto(
                new UserDtoForTicket(customer.getFirstname(), customer.getLastname(), customer.getUsername()),
                new FlightDto(
                        new AirportDto(flight.getDepartureAirport().getId(),flight.getDepartureAirport().getName(), new CityDto(flight.getDepartureAirport().getCity().getName())),
                        new AirportDto(flight.getArrivalAirport().getId(),flight.getArrivalAirport().getName(),
                                new CityDto(flight.getArrivalAirport().getCity().getName())),
                        flight.getDepartureTime(),
                        flight.getArrivalTime(),
                        flight.getPrice(),
                        flight.getSeatsAvailable()),
                saveDTicket.getPurchaseDate(),
                saveDTicket.getStatus());
    }

    public void cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getStatus() == Status.CANCELED) {
            throw new RuntimeException("Ticket is already cancelled");
        }

        ticket.setStatus(Status.CANCELED);
        ticketRepository.save(ticket);

        Flight flight = ticket.getFlight();
        flight.setSeatsAvailable(flight.getSeatsAvailable() + 1);
        flightRepository.save(flight);
    }
}
