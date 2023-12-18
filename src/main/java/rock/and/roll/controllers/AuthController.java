package rock.and.roll.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import rock.and.roll.entities.User;
import rock.and.roll.requests.UserRequest;
import rock.and.roll.responses.AuthResponse;
import rock.and.roll.securities.JwtTokenProvider;
import rock.and.roll.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	
	
	
	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
			UserService userService, PasswordEncoder passwordEncoder) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody UserRequest loginRequest) {
	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
	        Authentication auth = authenticationManager.authenticate(authToken);
	        SecurityContextHolder.getContext().setAuthentication(auth);
	        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
	        User user = userService.getOneUserByUserName(loginRequest.getUserName());
	        AuthResponse authResponse = new AuthResponse();
	        authResponse.setMessage( "Bearer "+jwtToken);
	        authResponse.setUserId(user.getId());
	        return authResponse;
	  
	}

	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody UserRequest  registerRequest){
		AuthResponse authResponse = new AuthResponse();
		if(userService.getOneUserByUserName(registerRequest.getUserName()) !=null) {
			authResponse.setMessage("Username already in use");
		return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
	}
		User user = new User();
		user.setUserName((registerRequest.getUserName()));
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		userService.saveOneUser(user);
		authResponse.setMessage("User successfully registered");
		return new ResponseEntity<> (authResponse, HttpStatus.CREATED);
	
	}
}
