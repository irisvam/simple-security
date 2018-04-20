package com.pattern.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pattern.spring.model.UserApp;
import com.pattern.spring.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository repUser;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		UserApp user = Optional.ofNullable(repUser.findByUserLogin(username))
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		List<GrantedAuthority> authorities = null;

		if (user.getPerfil().equals("ADMIN")) {
			authorities = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		} else {
			authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		}

		return new User(user.getUserLogin(), user.getPassword(), authorities);
	}

}
