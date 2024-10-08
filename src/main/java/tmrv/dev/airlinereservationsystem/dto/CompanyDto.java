package tmrv.dev.airlinereservationsystem.dto;

import tmrv.dev.airlinereservationsystem.domains.User;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link tmrv.dev.airlinereservationsystem.domains.Company}
 */
public record CompanyDto(String name, List<UserDto> agents) implements Serializable {
  }