package tmrv.dev.airlinereservationsystem.dto;

import tmrv.dev.airlinereservationsystem.domains.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link tmrv.dev.airlinereservationsystem.domains.Ticket}
 */
public record TicketDto(UserDtoForTicket customer, FlightDto flight, LocalDateTime purchaseDate,
                        Status status) implements Serializable {
}