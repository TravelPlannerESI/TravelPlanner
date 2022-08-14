package com.travelplan.plan;

import com.travelplan.domain.plan.util.PlanDateUtil;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanTest {

    @Test
    void 날짜_날짜_사이_구하기() {
        LocalDateTime start = LocalDateTime.of(2022, 06, 01,0,0);
        LocalDateTime end = LocalDateTime.of(2022, 06, 03,0,0);

        long countOfBetweenDays = ChronoUnit.DAYS.between(start, end);

        List<LocalDateTime> betweenDays = IntStream.iterate(0, i -> i + 1)
                .limit(countOfBetweenDays)
                .mapToObj(i -> start.plusDays(i))
                .collect(Collectors.toList());

        System.out.println("betweenDays = " + betweenDays);

        assertThat(betweenDays.size()).isEqualTo(2);

    }

    @Test
    void 날짜_날짜_사이_구하기_Map() {
        LocalDateTime start = LocalDateTime.of(2022, 06, 01,0,0);
        LocalDateTime end = LocalDateTime.of(2022, 06, 03,0,0);
        Map<Integer, LocalDateTime> betweenDateWithDays = PlanDateUtil.getBetweenDateWithDays(start, end);

        assertThat(betweenDateWithDays.get(0)).isEqualTo(start);
        assertThat(betweenDateWithDays.get(2)).isEqualTo(end);

    }

    @Test
    void asd() {
        LocalDate start = LocalDate.of(2022, 06, 01);
        LocalDate end = LocalDate.of(2022, 06, 03);
        Map<Integer, LocalDate> betweenDateWithDays = PlanDateUtil.getBetweenDateWithDays(start, end, false);
        long betweenDateCount = PlanDateUtil.getBetweenDateCount(start, end, false);
        for (LocalDate ldt : betweenDateWithDays.values()) {
            System.out.println("ldt = " + ldt);
        }

        System.out.println("betweenDateCount = " + betweenDateCount);
    }
}
