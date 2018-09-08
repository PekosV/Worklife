package com.linkedin.service;

import com.linkedin.constants.Role;
import com.linkedin.entities.database.Login;
import com.linkedin.entities.database.User;
import com.linkedin.entities.database.repo.LoginRepository;
import com.linkedin.entities.database.repo.UserRepository;
import com.linkedin.entities.model.RegisterRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final LoginRepository loginRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(LoginRepository loginRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.loginRepository = loginRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User getUser(String username) {
		return userRepository.findByUsernameIgnoreCase(username);
	}

	public User getUser(Long id) {
		return userRepository.getOne(id);
	}

	public boolean usernameTaken(String username) {
		return userRepository.existsByEmailIgnoreCase(username);
	}

	public boolean emailExists(String email) {
		return loginRepository.existsByUsernameIgnoreCase(email);
	}

	private Role getUserRole() {
		return Role.ROLE_USER;
	}

	public void register(RegisterRequestDto registerRequestDto) {
		Login login = createLogin(registerRequestDto);
		createUser(login.getUserId(), registerRequestDto);
	}

	private Login createLogin(RegisterRequestDto registerRequestDto) {

		Login login = new Login(registerRequestDto.getUsername(), registerRequestDto.getPassword());
		login.setPassword(passwordEncoder.encode(login.getPassword()));
		login.setRole(getUserRole());

		return loginRepository.save(login);
	}

	private void createUser(Long userId, RegisterRequestDto registerRequestDto) {
		User user = new User();
		user.setId(userId);
		user.setEmail(registerRequestDto.getEmail());
		user.setUsername(registerRequestDto.getUsername());
		user.setName(registerRequestDto.getName());
		user.setSurname(registerRequestDto.getSurname());
		user.setBirthdate(registerRequestDto.getBirthdate());
		user.setAddress(registerRequestDto.getAddress());
		user.setImgPath(registerRequestDto.getImgPath());
		user.setPhoneNumber(registerRequestDto.getPhoneNumber());
		userRepository.save(user);
	}

	public User save(User user) {
		return userRepository.save(user);
	}
}