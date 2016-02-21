package ioffice.br.persistance.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import ioffice.br.persistance.dao.administration.UserDao;
import ioffice.br.persistance.model.administration.Role;
import ioffice.br.persistance.model.administration.RolePriviledge;
import ioffice.br.persistance.model.administration.User;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
@Scope("singleton")
public class HibernateUserDetailsService implements UserDetailsService {

	@Inject
	private UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String requestUsername) throws UsernameNotFoundException {
		User userEntity = userDao.findByUsername(requestUsername);

		if (userEntity == null) {
			throw new UsernameNotFoundException("Username not found");
		}

		String username = userEntity.getEmail();
		String password = userEntity.getPassword();
		boolean enabled = userEntity.isActive();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		Collection<GrantedAuthority> authorities = getAuthorities(userEntity.getRoles());

		return new org.springframework.security.core.userdetails.User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
				authorities);

	}

	private Collection<GrantedAuthority> getAuthorities(Collection<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : roles) {
			for (RolePriviledge priviledge : role.getRolePriviledges()) {
				if(priviledge.isActive()){
				 authorities.add(new SimpleGrantedAuthority(priviledge.getDomainObject().getCode().name()+":"+priviledge.getPriviledgeTypeCode().name()));
				}
			}
		}

		return authorities;
	}
}
