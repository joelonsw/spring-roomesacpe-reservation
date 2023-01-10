package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.nextstep.Reservation;
import roomescape.nextstep.Theme;

import java.util.Optional;

@Repository
public class ReservationWebRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationWebRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
            resultSet.getLong("id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime(),
            resultSet.getString("name"),
            new Theme(
                    resultSet.getString("theme_name"),
                    resultSet.getString("theme_desc"),
                    resultSet.getInt("theme_price")
            )
        );
        return reservation;
    };

    @Override
    public void insertReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                reservation.getDate(),
                reservation.getTime(),
                reservation.getName(),
                reservation.getTheme().getName(),
                reservation.getTheme().getDesc(),
                reservation.getTheme().getPrice()
        );
    }

    @Override
    public Optional<Reservation> getReservation(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = (?)";
        return jdbcTemplate.query(sql, reservationRowMapper, id)
                .stream()
                .findAny();
    }

    @Override
    public int deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = (?)";
        return jdbcTemplate.update(sql, id);
    }
}