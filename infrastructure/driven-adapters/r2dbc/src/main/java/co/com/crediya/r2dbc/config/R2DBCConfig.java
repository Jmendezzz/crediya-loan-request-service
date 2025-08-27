package co.com.crediya.r2dbc.config;


import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class R2DBCConfig {

    private final String host;

    private final Integer port;

    private final String username;

    private final String password;

    private final String database;


    public R2DBCConfig(
            @Value("${database.host}") String host,
            @Value("${database.port}") Integer port,
            @Value("${database.username}") String username,
            @Value("${database.password}") String password,
            @Value("${database.name}") String database){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return MySqlConnectionFactory.from(
                MySqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .user(username)
                        .password(password)
                        .database(database)
                        .build()
        );
    }


}
