package tmrv.dev.airlinereservationsystem.dto;

import tmrv.dev.airlinereservationsystem.domains.User;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link tmrv.dev.airlinereservationsystem.domains.Company}
 */
public record CompanyDtoForGet(String name, List<UserDtoForCompany> agents) implements Serializable {
  }