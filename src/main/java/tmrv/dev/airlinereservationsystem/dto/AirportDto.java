package tmrv.dev.airlinereservationsystem.dto;

import tmrv.dev.airlinereservationsystem.domains.City;

import java.io.Serializable;

/**
 * DTO for {@link tmrv.dev.airlinereservationsystem.domains.Airport}
 */
public record AirportDto(Long id, String name, CityDto cityDto) implements Serializable {
}