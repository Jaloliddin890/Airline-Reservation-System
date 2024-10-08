package tmrv.dev.airlinereservationsystem.service;



import org.springframework.stereotype.Service;
import tmrv.dev.airlinereservationsystem.domains.Company;
import tmrv.dev.airlinereservationsystem.domains.Role;
import tmrv.dev.airlinereservationsystem.domains.User;
import tmrv.dev.airlinereservationsystem.dto.CompanyDto;
import tmrv.dev.airlinereservationsystem.dto.CompanyDtoForGet;

import tmrv.dev.airlinereservationsystem.dto.UserDtoForCompany;
import tmrv.dev.airlinereservationsystem.repository.CompanyRepository;
import tmrv.dev.airlinereservationsystem.repository.UserRepository;


import java.util.List;

import java.util.stream.Collectors;


@Service
public class CompanyService {


    private final UserRepository userRepository;



    private final CompanyRepository companyRepository;

    public CompanyService(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;

        this.companyRepository = companyRepository;
    }

    public String createCompany(CompanyDto companyDto,Integer userId){
        Company company = new Company();
        company.setName(companyDto.name());

        List<User> agents = userRepository.findByRole(Role.AGENT);
        for (User agent : agents) {
            if(agent.getId().equals(userId)){
                agent.setCompany(company);
            }
        }
        company.setAgents(agents);

        companyRepository.save(company);
        userRepository.saveAll(agents);
        return company.getName();
    }

    public String updateCompany(CompanyDto companyDto, Long id, Integer userId){
        List<User> agents = userRepository.findByRole(Role.AGENT);
            companyRepository.findCompanyById(id).ifPresent(company -> {
                company.setName(companyDto.name());
                for (User agent : agents) {
                    if(agent.getId().equals(userId)){
                        agent.setCompany(company);
                    }
                }
                companyRepository.save(company);

            });
            return "Updated Company";
    }

    public String deleteCompany(Long id){
        companyRepository.findCompanyById(id).ifPresent(companyRepository::delete);
        return "Deleted Company";
    }

    public List<CompanyDtoForGet> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(company -> new CompanyDtoForGet(
                        company.getName(),
                        company.getAgents().stream()
                                .map(user -> new UserDtoForCompany(user.getUsername(), user.getRole()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

}
