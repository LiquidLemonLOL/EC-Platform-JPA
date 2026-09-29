package se.lexicon.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfig {

    // enables the time or Instant to work between different time zones, since promotions can
    // possibly be inactive after/before a day in the specific time zone.
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
