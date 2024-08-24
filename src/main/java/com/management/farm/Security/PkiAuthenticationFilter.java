package com.management.farm.Security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import java.security.cert.X509Certificate;

@Component
public class PkiAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    public PkiAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("jakarta.servlet.request.X509Certificate");
        if (certs != null && certs.length > 0) {
            X509Certificate clientCert = certs[0];
            String principalName = clientCert.getSubjectX500Principal().getName();
            System.out.println("Authenticated client certificate subject: " + principalName);
            System.out.println("Client certificate issuer: " + clientCert.getIssuerX500Principal().getName());
            return principalName;
        }
        System.out.println("No client certificate found in request.");
        return null;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
