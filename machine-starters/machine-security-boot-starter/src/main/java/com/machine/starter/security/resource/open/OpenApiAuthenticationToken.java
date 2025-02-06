package com.machine.starter.security.resource.open;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Setter
@Getter
public class OpenApiAuthenticationToken extends AbstractAuthenticationToken {

  private String clientId;
  private String clientSecret;
  private OpenApiUserDetails userDetails;

  public OpenApiAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
  }

  @Override
  public Object getCredentials() {
    return isAuthenticated() ? null : clientSecret;
  }

  @Override
  public Object getPrincipal() {
    return isAuthenticated() ? userDetails : clientId;
  }

}
