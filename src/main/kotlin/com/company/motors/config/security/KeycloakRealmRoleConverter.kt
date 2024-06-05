package com.company.motors.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import java.util.stream.Collectors

class KeycloakRealmRoleConverter :
    org.springframework.core.convert.converter.Converter<Jwt, Collection<GrantedAuthority>> {
    override fun convert(jwt: Jwt): Collection<GrantedAuthority> {
        val realmAccess = jwt.claims[REALM_ACCESS] as Map<String, Any>?
        return (realmAccess!![ROLES] as List<String>?)!!.stream()
            .map { str: String -> ROLE_PREFIX + str }
            .map { role: String? ->
                SimpleGrantedAuthority(
                    role
                )
            }
            .collect(Collectors.toList())
    }

    companion object {
        private const val REALM_ACCESS = "realm_access"
        private const val ROLES = "roles"
        private const val ROLE_PREFIX = "ROLE_"
    }
}
