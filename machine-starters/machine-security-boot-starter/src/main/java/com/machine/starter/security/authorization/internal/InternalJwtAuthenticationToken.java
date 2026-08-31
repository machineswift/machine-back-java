package com.machine.starter.security.authorization.internal;

import com.machine.starter.security.service.model.MachineUserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
public class InternalJwtAuthenticationToken extends AbstractAuthenticationToken {

  private String jwtToken;
  private MachineUserDetails userDetails;

  public InternalJwtAuthenticationToken() {
    super(Collections.emptyList());
  }

  public InternalJwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
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
