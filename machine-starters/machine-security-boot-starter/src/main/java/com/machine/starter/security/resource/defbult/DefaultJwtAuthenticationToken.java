package com.machine.starter.security.resource.defbult;

import com.machine.starter.security.CustomerUserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter
public class DefaultJwtAuthenticationToken extends AbstractAuthenticationToken {

    private String jwtToken;
    private CustomerUserDetails userDetails;

    public DefaultJwtAuthenticationToken() {
        super(null);
    }

    public DefaultJwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
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
