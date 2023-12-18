package rock.and.roll.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rock.and.roll.entities.User;
import rock.and.roll.repositories.UserRepository;
import rock.and.roll.securities.JwtUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UserRepository userRepository;
	
	

	public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		return JwtUserDetails.create(user );
	}
	
	public UserDetails loadUserById(Long id) {
	User user = userRepository.findById(id).get();
	return JwtUserDetails.create(user);
	}
}