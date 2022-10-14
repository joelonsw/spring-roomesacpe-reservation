package nextstep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Main implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        createReservationTable();
        createThemesTable();
        createSchedulesTable();
    }

    private void createReservationTable() {
        String ddl = "create table reservations (id bigint auto_increment primary key, date date, name varchar(255), time time)";

        jdbcTemplate.execute("DROP TABLE reservations IF EXISTS");
        jdbcTemplate.execute(ddl);
    }

    private void createThemesTable() {
        String ddl = "create table themes (id bigint auto_increment primary key, name varchar(255), desc varchar(255), price bigint)";

        jdbcTemplate.execute("DROP TABLE themes IF EXISTS");
        jdbcTemplate.execute(ddl);
    }

    private void createSchedulesTable() {
        String ddl = "create table schedules (id bigint auto_increment primary key, themes_id bigint, date date, time time)";

        jdbcTemplate.execute("DROP TABLE schedules IF EXISTS");
        jdbcTemplate.execute(ddl);
    }
}