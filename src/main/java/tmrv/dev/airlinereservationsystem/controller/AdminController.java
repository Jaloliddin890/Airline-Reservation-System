package tmrv.dev.airlinereservationsystem.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tmrv.dev.airlinereservationsystem.domains.Airport;
import tmrv.dev.airlinereservationsystem.domains.User;
import tmrv.dev.airlinereservationsystem.dto.*;
import tmrv.dev.airlinereservationsystem.repository.CityRepository;
import tmrv.dev.airlinereservationsystem.service.AdminService;
import tmrv.dev.airlinereservationsystem.service.AirportService;
import tmrv.dev.airlinereservationsystem.service.CityService;
import tmrv.dev.airlinereservationsystem.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Controller", description = "This controller is used to admin controller")
public class AdminController {

    private final AdminService adminService;
    private final CompanyService companyService;
    private final AirportService airportService;
    private final CityRepository cityRepository;
    private final CityService cityService;

    public AdminController(AdminService adminService, CompanyService companyService, AirportService airportService, CityRepository cityRepository, CityService cityService) {
        this.adminService = adminService;
        this.companyService = companyService;
        this.airportService = airportService;
        this.cityRepository = cityRepository;
        this.cityService = cityService;
    }
    // CRUD FOR AGENT
    @PostMapping("/createAgent")
    public String createAgent(@RequestBody UserDto agentDto){
        adminService.createAgent(agentDto);
        return "Agent Created";
    }

    @PutMapping("/updateAgent/{userId}")
    public String updateAgent(@PathVariable Integer userId, @RequestBody UserDto agentDto){
        adminService.updateAgent(agentDto,userId);
        return "Agent Updated";
    }
    @DeleteMapping("/deleteAgent/{userId}")
    public String deleteAgent(@PathVariable Integer userId){
        adminService.deleteAgent(userId);
        return "Agent Deleted";
    }

    @GetMapping("/getAllAgents")
    public List<User> getAllAgents(){
        return adminService.getAllAgents();
    }

    @PostMapping("/blockAgent/{id}")
    public String blockAgent(@PathVariable Integer id){
        adminService.blockAgent(id);
        return "Agent Blocked";
    }
    @PostMapping("/unBlockAgent/{id}")
    public String unBlockAgent(@PathVariable Integer id){
        adminService.unBlockAgent(id);
        return "Agent UnBlocked";
    }

    // CRUD FOR Company

    @PostMapping("/createCompany/{userId}")
    public String createCompany(@RequestBody CompanyDto companyDto, @PathVariable Integer userId){
        companyService.createCompany(companyDto, userId);
        return "Company Created";
    }

    @PutMapping("/updateCompany/{id}/{userId}")
    public String updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto, @PathVariable Integer userId){
        companyService.updateCompany(companyDto, id, userId);
        return "Company Updated";
    }
    @DeleteMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable Long id){
        companyService.deleteCompany( id);
        return "Company Updated";
    }

    @GetMapping("/getAllCompanies")
    public List<CompanyDtoForGet> getCompany() {
        return companyService.getAllCompanies();
    }

    // CRUD FOR City
    @PostMapping("/createCity")
    public String createCity(@RequestBody CityDto cityDto){
        cityService.createCity(cityDto);
        return "City Created";
    }

    @PutMapping("/updateCity/{id}")
    public String updateCity(CityDto cityDto, @PathVariable Long id){
        cityService.updateCity(cityDto,id);
        return "City Updated";
    }

    @DeleteMapping("/deleteCity/{id}")
    public String deleteCity( @PathVariable Long id){
        cityService.deleteCity(id);
        return "City Deleted";
    }
    @GetMapping("/getAllCities")
    public String getAllCities() {
        return cityService.getAllCities();
    }

    // CRUD FOR Airport

    @PostMapping("/createAirport/{id}")
    public String createAirport(@RequestBody AirportDto airportDto, @PathVariable long id){
                airportService.createAirport(airportDto, id);
        return "Airport Created";
    }

    @PutMapping("/updateAirport/{id}/{cityId}")
    public String updateAirport(AirportDto airportDto, @PathVariable Long id, @PathVariable Long cityId){
       airportService.updateAirport(airportDto, id, cityId);
       return "Airport Updated";
    }

    @DeleteMapping("/deleteAirport/{id}")
    public String deleteAirport( @PathVariable Long id){
        airportService.deleteAirport(id);
        return "Airport Deleted";
    }

    @GetMapping("/getAllAirports")
    public List<Airport> getAllAirports(){
        return airportService.getAllAirports();
    }




}
