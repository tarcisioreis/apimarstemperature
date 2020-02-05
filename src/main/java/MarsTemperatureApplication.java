import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.apimars.apimarstemperature", "controller", "service"})
@SpringBootApplication
@SuppressWarnings({"squid:S1220", "squid:S4602"})
public class MarsTemperatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarsTemperatureApplication.class, args);
	}

}
