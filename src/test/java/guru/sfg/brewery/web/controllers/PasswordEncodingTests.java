package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncodingTests {

    static final String PASSWORD = "password";

    @Test
    void testBcrypt() { //default implementation of Spring Security

        PasswordEncoder bcrypt = new BCryptPasswordEncoder(); //default 10, higher, the longer it takes

        System.out.println(bcrypt.encode(PASSWORD));
        System.out.println(bcrypt.encode(PASSWORD));
        System.out.println(bcrypt.encode("kahlua"));
    }

    @Test
    void testSha256() {

        PasswordEncoder sha256 = new StandardPasswordEncoder();

        System.out.println(sha256.encode(PASSWORD));
        System.out.println(sha256.encode(PASSWORD));
    }

    @Test
    void testLdap() {

        PasswordEncoder ldap = new LdapShaPasswordEncoder(); //Not recommended, only for legacy systems

        System.out.println(ldap.encode(PASSWORD));
        System.out.println(ldap.encode(PASSWORD));
        System.out.println(ldap.encode("tiger"));

        String encodedPwd = ldap.encode(PASSWORD);

        assertTrue(ldap.matches(PASSWORD, encodedPwd));
    }

    @Test
    void testNoOp() {

        PasswordEncoder noOpEncoder = NoOpPasswordEncoder.getInstance();

        System.out.println(noOpEncoder.encode(PASSWORD));
    }

    @Test
    void hashingExample() {

        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

        String salted = PASSWORD + "ThisIsMySALTVALUE";
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }
}
