package tmrv.dev.airlinereservationsystem.dto;

import tmrv.dev.airlinereservationsystem.domains.Role;

import java.io.Serializable;

/**
 * DTO for {@link tmrv.dev.airlinereservationsystem.domains.User}
 */
public record UserDto(String firstname, String lastname, String username, String password,
                      Role role, boolean isBlocked) implements Serializable {
}