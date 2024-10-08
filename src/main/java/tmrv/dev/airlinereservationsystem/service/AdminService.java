package tmrv.dev.airlinereservationsystem.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tmrv.dev.airlinereservationsystem.domains.Airport;
import tmrv.dev.airlinereservationsystem.domains.Company;
import tmrv.dev.airlinereservationsystem.domains.Role;
import tmrv.dev.airlinereservationsystem.domains.User;
import tmrv.dev.airlinereservationsystem.dto.UserDto;
import tmrv.dev.airlinereservationsystem.repository.AirportRepository;
import tmrv.dev.airlinereservationsystem.repository.CompanyRepository;
import tmrv.dev.airlinereservationsystem.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
public class AdminService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AdminService(PasswordEncoder passwordEncoder, JwtService jwtService, UserRepository userRepository, CompanyRepository companyRepository, AirportRepository airportRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public String createAgent(UserDto agentDto) {

        User agent = new User();
        agent.setFirstname(agentDto.firstname());
        agent.setLastname(agentDto.lastname());
        agent.setUsername(agentDto.username());
        agent.setPassword(passwordEncoder.encode(agentDto.password()));
        agent.setRole(Role.AGENT);
        agent.setBlocked(false);
        userRepository.save(agent);

        jwtService.generateToken(agent);


        return "Agent Created";
    }

    public String updateAgent(UserDto agentDto, Integer userId) {
        List<User> users = userRepository.findByRole(Role.AGENT);
        for (User user : users) {
            if(user.getId().equals(userId)) {
                user.setFirstname(agentDto.firstname());
                user.setLastname(agentDto.lastname());
                user.setUsername(agentDto.username());
                user.setPassword(passwordEncoder.encode(agentDto.password()));
                user.setRole(Role.AGENT);
                userRepository.save(user);
            }
        }
        return "Agent Updated";
    }
    public String deleteAgent(Integer userId){
        List<User> users = userRepository.findByRole(Role.AGENT);
        for (User user : users) {
            if(user.getId().equals(userId)) {
                userRepository.delete(user);
            }
        }
        return "Agent Deleted";
    }

    public List<User> getAllAgents() {
        return userRepository.findByRole(Role.AGENT);

    }

    public String blockAgent(Integer id ) {
        List<User> users = userRepository.findByRole(Role.AGENT);
        for (User user : users) {
            if (user.getId().equals(id) && !user.isBlocked()) {
                user.setBlocked(true);
                userRepository.save(user);
            }
        }

        return "Agent Blocked";
    }
    public String unBlockAgent(Integer id) {
        List<User> users = userRepository.findByRole(Role.AGENT);
        for (User user : users) {
            if (user.getId().equals(id) && user.isBlocked()) {
                user.setBlocked(false);
                userRepository.save(user);
            }
        }

        return "Agent UnBlocked";
    }

}
