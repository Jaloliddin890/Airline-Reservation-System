package tmrv.dev.airlinereservationsystem.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tmrv.dev.airlinereservationsystem.dto.AirportDto;
import tmrv.dev.airlinereservationsystem.dto.FlightDto;
import tmrv.dev.airlinereservationsystem.dto.TicketDto;
import tmrv.dev.airlinereservationsystem.service.AirportService;
import tmrv.dev.airlinereservationsystem.service.FlightService;
import tmrv.dev.airlinereservationsystem.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
@Tag(name = "Customer Controller", description = "This controller is used to customer controller")
public class CustomerController {
    private final AirportService airportService;
    private final FlightService flightService;
    private final TicketService  ticketService;

    public CustomerController(AirportService airportService, FlightService flightService, TicketService ticketService) {
        this.airportService = airportService;
        this.flightService = flightService;
        this.ticketService = ticketService;
    }

    @PostMapping("/getAllAirportsInCity/{cityName}")
    public List<AirportDto> airportsInOneCity(@PathVariable String cityName){
       return airportService.airportsInOneCity( cityName);
    }

    @GetMapping("/{airportId}/flights")
    public ResponseEntity<List<FlightDto>> getFlightsByAirport(@PathVariable Long airportId) {
        List<FlightDto> flights = flightService.getFlightsByAirport(airportId);
        if (flights.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(flights);
    }

    @PostMapping("/purchaseTicket")
    public ResponseEntity<TicketDto> purchaseTicket(@RequestParam Long flightId, @RequestParam Long customerId) {
        TicketDto ticketDto = ticketService.purchaseTicket(flightId, customerId);
        return ResponseEntity.ok(ticketDto);
    }
    @PostMapping("/{ticketId}/cancel")
    public ResponseEntity<String> cancelTicket(@PathVariable Long ticketId) {
            ticketService.cancelTicket(ticketId);
            return ResponseEntity.ok("Ticket canceled successfully");

    }


}
