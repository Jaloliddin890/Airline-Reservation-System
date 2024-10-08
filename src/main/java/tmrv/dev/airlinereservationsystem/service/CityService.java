package tmrv.dev.airlinereservationsystem.service;


import org.springframework.stereotype.Service;
import tmrv.dev.airlinereservationsystem.domains.City;
import tmrv.dev.airlinereservationsystem.dto.CityDto;
import tmrv.dev.airlinereservationsystem.repository.CityRepository;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public String createCity(CityDto cityDto) {
        City city = new City();
        city.setName(cityDto.name());
        cityRepository.save(city);
        return "City Created";
    }

    public String updateCity(CityDto cityDto, Long id) {
            cityRepository.findById(id).ifPresent(city -> {
                city.setName(cityDto.name());
                cityRepository.save(city);
            });
            return "City Updated";
    }
    public String deleteCity(Long id) {
        cityRepository.findById(id).ifPresent(cityRepository::delete);
        return "City Deleted";
    }

    public String getAllCities() {
        cityRepository.findAll();
        return "Cities! ! ! ";
    }
}




