package tmrv.dev.airlinereservationsystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tmrv.dev.airlinereservationsystem.domains.Airport;
import tmrv.dev.airlinereservationsystem.domains.Flight;
import tmrv.dev.airlinereservationsystem.dto.AirportDto;
import tmrv.dev.airlinereservationsystem.dto.CityDto;
import tmrv.dev.airlinereservationsystem.dto.FlightDto;
import tmrv.dev.airlinereservationsystem.repository.AirportRepository;
import tmrv.dev.airlinereservationsystem.repository.FlightRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {


    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final NotificationService notificationService;
    private final ObjectMapper jsonMapper;  // For JSON files
    private final ObjectMapper yamlMapper;  // For YAML files



    public FlightService(FlightRepository flightRepository, AirportRepository airportRepository, ObjectMapper jsonMapper, ObjectMapper yamlMapper, NotificationService notificationService) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
        this.notificationService = notificationService;
        this.jsonMapper = new ObjectMapper();  // JSON mapper
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
    }
    public void processFlightFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (fileName.endsWith(".json")) {
            List<FlightDto> flights = jsonMapper.readValue(file.getInputStream(), new TypeReference<List<FlightDto>>() {});
            saveFlights(flights);
        } else if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            List<FlightDto> flights = yamlMapper.readValue(file.getInputStream(), new TypeReference<List<FlightDto>>() {});
            saveFlights(flights);
        } else {
            throw new IllegalArgumentException("Unsupported file type. Only JSON and YAML are supported.");
        }
    }

    public List<FlightDto> getFlightsByAirport(Long airportId) {
        List<Flight> flights = flightRepository.findByDepartureAirport_Id(airportId);
        return flights.stream()
                .map(flight -> new FlightDto(
                        new AirportDto(flight.getDepartureAirport().getId(),flight.getDepartureAirport().getName(), new CityDto(flight.getDepartureAirport().getCity().getName())),
                        new AirportDto(flight.getArrivalAirport().getId(),flight.getArrivalAirport().getName(), new CityDto(flight.getArrivalAirport().getCity().getName())),
                        flight.getDepartureTime(),
                        flight.getArrivalTime(),
                        flight.getPrice(),
                        flight.getSeatsAvailable()))
                .collect(Collectors.toList());
    }

    public FlightDto createFlight(FlightDto flightDto) {
        Flight flight = new Flight();
        Airport departureAirport = airportRepository.findById(flightDto.departureAirport().id())
                .orElseThrow(() -> new RuntimeException("Departure airport not found"));
        Airport arrivalAirport = airportRepository.findById(flightDto.arrivalAirport().id())
                .orElseThrow(() -> new RuntimeException("Arrival airport not found"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(flightDto.departureTime());
        flight.setArrivalTime(flightDto.arrivalTime());
        flight.setPrice(flightDto.price());
        flight.setSeatsAvailable(flightDto.seatsAvailable());

        Flight savedFlight = flightRepository.save(flight);

        return mapToFlightDto(savedFlight);


    }
    public FlightDto getFlight(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return mapToFlightDto(flight);
    }

    public List<FlightDto> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(this::mapToFlightDto)
                .collect(Collectors.toList());
    }



    public FlightDto updateFlight(Long flightId, FlightDto flightDto) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        Airport departureAirport = airportRepository.findById(flightDto.departureAirport().id())
                .orElseThrow(() -> new RuntimeException("Departure airport not found"));
        Airport arrivalAirport = airportRepository.findById(flightDto.arrivalAirport().id())
                .orElseThrow(() -> new RuntimeException("Arrival airport not found"));

        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(flightDto.departureTime());
        flight.setArrivalTime(flightDto.arrivalTime());
        flight.setPrice(flightDto.price());
        flight.setSeatsAvailable(flightDto.seatsAvailable());

        Flight updatedFlight = flightRepository.save(flight);
        notificationService.notifyCustomersAboutChanges(updatedFlight);
        return mapToFlightDto(updatedFlight);
    }

    public void deleteFlight(Long flightId) {
        if (!flightRepository.existsById(flightId)) {
            throw new RuntimeException("Flight not found");
        }
        flightRepository.deleteById(flightId);
    }

    private FlightDto mapToFlightDto(Flight flight) {
        return new FlightDto(
                new AirportDto(flight.getDepartureAirport().getId(), flight.getDepartureAirport().getName(),
                        new CityDto(flight.getDepartureAirport().getCity().getName())),
                new AirportDto(flight.getArrivalAirport().getId(),flight.getArrivalAirport().getName(),
                        new CityDto(flight.getArrivalAirport().getCity().getName())),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getPrice(),
                flight.getSeatsAvailable());
    }
    public void saveFlights(List<FlightDto> flightDtos) {
        for (FlightDto flightDto : flightDtos) {
            createFlight(flightDto);  // Reuse the existing createFlight logic
        }
    }

}
