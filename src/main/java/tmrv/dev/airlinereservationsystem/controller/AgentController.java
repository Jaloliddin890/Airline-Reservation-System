package tmrv.dev.airlinereservationsystem.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tmrv.dev.airlinereservationsystem.dto.FlightDto;
import tmrv.dev.airlinereservationsystem.service.FlightService;

import java.util.List;

@RestController
@RequestMapping("/agent")
@PreAuthorize("hasRole('AGENT')")
@Tag(name = "Agent Controller", description = "This controller is used to agent controller")
public class AgentController {
    private  final FlightService flightService;


    public AgentController(FlightService flightService) {
        this.flightService = flightService;
    }


    @PostMapping("/createFlight")
    public ResponseEntity<FlightDto> createFlight(@RequestBody FlightDto flightDto) {
        FlightDto newFlight = flightService.createFlight(flightDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFlight);
    }


    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDto> getFlight(@PathVariable Long flightId) {
        FlightDto flight = flightService.getFlight(flightId);
        return ResponseEntity.ok(flight);
    }


    @GetMapping("/getAllFlights")
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        List<FlightDto> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }


    @PutMapping("updateFlight/{flightId}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable Long flightId, @RequestBody FlightDto flightDto) {
        FlightDto updatedFlight = flightService.updateFlight(flightId, flightDto);
        return ResponseEntity.ok(updatedFlight);
    }


    @DeleteMapping("deleteFlight/{flightId}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload-flights")
    public ResponseEntity<String> uploadFlights(@RequestParam("file") MultipartFile file) {
        try {

            flightService.processFlightFile(file);
            return ResponseEntity.ok("Flight schedules have been uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing the file: " + e.getMessage());
        }
    }
}
