package tmrv.dev.airlinereservationsystem.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tmrv.dev.airlinereservationsystem.domains.Airport;
import tmrv.dev.airlinereservationsystem.domains.City;
import tmrv.dev.airlinereservationsystem.domains.User;
import tmrv.dev.airlinereservationsystem.dto.AirportDto;
import tmrv.dev.airlinereservationsystem.dto.CityDto;
import tmrv.dev.airlinereservationsystem.repository.AirportRepository;
import tmrv.dev.airlinereservationsystem.repository.CityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    public AirportService(AirportRepository airportRepository, CityRepository cityRepository) {
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
    }

    public String createAirport(AirportDto airportDto, Long id) {
        List<City> cities = cityRepository.findByName(airportDto.cityDto().name());
        Airport airport = new Airport();
        airport.setName(airportDto.name());
        for (City city : cities) {
            if(city.getId().equals(id)){
                airport.setCity(city);
            }
        }
        airportRepository.save(airport);
        return "Airport Created";
    }
    public String updateAirport(AirportDto airportDto, Long id, Long cityId){
        List<City> cities = cityRepository.findByName(airportDto.cityDto().name());
        airportRepository.findById(id).ifPresent(airport -> {
            for (City city : cities) {
                if(city.getId().equals(cityId)){
                    airport.setCity(city);
                }
            }
        });
        return "Airport Updated";

    }
    public String deleteAirport(Long id) {
        airportRepository.deleteById(id);
        return "Airport Deleted";
    }

    public List<Airport> getAllAirports() {
       return airportRepository.findAll();
    }

    public List<AirportDto> airportsInOneCity(String existCityName) {
        return cityRepository.findByName(existCityName).stream()
                .findFirst()
                .map(city -> city.getAirports().stream()
                        .map(this::convertToAirportDto)
                        .collect(Collectors.toList())
                )
                .orElseThrow(() -> new EntityNotFoundException("City not found"));
    }


    private AirportDto convertToAirportDto(Airport airport) {
        return new AirportDto(airport.getId(), airport.getName(), new CityDto(airport.getCity().getName()));
    }
}
