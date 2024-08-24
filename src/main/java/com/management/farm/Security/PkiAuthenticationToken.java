package com.management.farm.Security;

import org.springframework.security.authentication.AbstractAuthenticationToken;


public class PkiAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;

    public PkiAuthenticationToken(String principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
