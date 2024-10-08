package tmrv.dev.airlinereservationsystem.service;
import org.springframework.stereotype.Service;
import tmrv.dev.airlinereservationsystem.repository.CityRepository;
import tmrv.dev.airlinereservationsystem.repository.UserRepository;

@Service
public class CustomerService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    public CustomerService(UserRepository userRepository, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }
}

