package com.machine.starter.security.resource.self;

import com.machine.starter.security.CustomerUserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter
public class SelfJwtAuthenticationToken extends AbstractAuthenticationToken {

  private String jwtToken;
  private CustomerUserDetails userDetails; 

  public SelfJwtAuthenticationToken() {
    super(null);
  }

  public SelfJwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
  }

  @Override
  public Object getCredentials() {
    return isAuthenticated() ? null : jwtToken;
  }

  @Override
  public Object getPrincipal() {
    return isAuthenticated() ? userDetails : jwtToken;
  }

}
