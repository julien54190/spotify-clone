package fr.julien.spotify_clone_back;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SpotifyCloneBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyCloneBackApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        Dotenv dotenv = Dotenv.configure().load();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + dotenv.get("POSTGRES_URL") + ":5432/" + dotenv.get("POSTGRES_DB"));
        config.setUsername(dotenv.get("POSTGRES_USERNAME"));
        config.setPassword(dotenv.get("POSTGRES_PASSWORD"));
        config.setPoolName("Hikari");
        config.setAutoCommit(false);
        return new HikariDataSource(config);
    }
}
