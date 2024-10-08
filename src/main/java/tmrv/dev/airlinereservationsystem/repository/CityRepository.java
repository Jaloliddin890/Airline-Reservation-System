package tmrv.dev.airlinereservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tmrv.dev.airlinereservationsystem.domains.City;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

  List<City> findByName(String cityName);
}