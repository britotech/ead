package tech.brito.ead.notificationhex.adapters.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tech.brito.ead.notificationhex.NotificationHexApplication;
import tech.brito.ead.notificationhex.core.ports.NotificationPersistencePort;
import tech.brito.ead.notificationhex.core.services.NotificationServicePortImpl;

@Configuration
@ComponentScan(basePackageClasses = NotificationHexApplication.class)
public class BeanConfiguration {

    @Bean
    public NotificationServicePortImpl notificationServicePortImpl(NotificationPersistencePort persistencePort) {
        return new NotificationServicePortImpl(persistencePort);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
