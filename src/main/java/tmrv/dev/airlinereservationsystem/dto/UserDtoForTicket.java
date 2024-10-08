package tmrv.dev.airlinereservationsystem.dto;

import java.io.Serializable;

/**
 * DTO for {@link tmrv.dev.airlinereservationsystem.domains.User}
 */
public record UserDtoForTicket(String firstname, String lastname, String username) implements Serializable {
}