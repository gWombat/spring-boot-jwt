package fr.gwombat.springbootjwt.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;
    private final PasswordEncoder        passwordEncoder;

    public ApplicationUserController(ApplicationUserService applicationUserService, PasswordEncoder passwordEncoder) {
        this.applicationUserService = applicationUserService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Store a new user in the application
     *
     * @param user The user information
     */
    @PostMapping("/sign-up")
    public void signUp(@RequestBody @Valid ApplicationUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        applicationUserService.save(user);
    }
}
