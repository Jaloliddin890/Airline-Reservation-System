package tmrv.dev.airlinereservationsystem.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tmrv.dev.airlinereservationsystem.dto.UserDto;
import tmrv.dev.airlinereservationsystem.dto.UserDtoForLogin;
import tmrv.dev.airlinereservationsystem.service.AuthenticationService;

@RestController
@Tag(name = "Auth Controller", description = "This Controller's api is based on Security")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody UserDto userDto){
        return ResponseEntity.ok(authenticationService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody UserDtoForLogin dto){
        return ResponseEntity.ok(authenticationService.login(dto));
    }

}