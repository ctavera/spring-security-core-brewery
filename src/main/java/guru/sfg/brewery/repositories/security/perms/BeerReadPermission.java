package guru.sfg.brewery.repositories.security.perms;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) //tells the java compiler to retain at runtime for reflection and see this annotation
@PreAuthorize("hasAuthority('beer.read')") //hasRole seach for prefix ROLE_, hasAuthority doesn't
public @interface BeerReadPermission {
}
