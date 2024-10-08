package tmrv.dev.airlinereservationsystem.dto;

import tmrv.dev.airlinereservationsystem.domains.Role;

import java.io.Serializable;

/**
 * DTO for {@link tmrv.dev.airlinereservationsystem.domains.User}
 */
public record UserDtoForCompany(String username, Role role) implements Serializable {
}