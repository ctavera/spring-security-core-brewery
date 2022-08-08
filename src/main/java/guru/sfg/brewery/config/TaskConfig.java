package guru.sfg.brewery.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.ICredentialRepository;
import guru.sfg.brewery.security.CustomPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@EnableScheduling
@EnableAsync
@Configuration
public class TaskConfig {

    @Bean
    TaskExecutor taskExecutor () {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean //to delete
    PasswordEncoder passwordEncoder(){
        return CustomPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean //to delete
    public GoogleAuthenticator googleAuthenticator(ICredentialRepository iCredentialRepository){

        GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder configBuilder = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder();
        configBuilder.setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(60))
                .setWindowSize(10)
                .setNumberOfScratchCodes(0);

        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator(configBuilder.build());
        googleAuthenticator.setCredentialRepository(iCredentialRepository);

        return googleAuthenticator;
    }

}
