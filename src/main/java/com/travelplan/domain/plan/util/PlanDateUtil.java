package com.travelplan.domain.plan.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlanDateUtil {

    /**
     * [TYPE : LocalDateTime]
     * 두 날짜 사이의 날짜들을 반환하는 메서드
     * @param startDate
     * @param endDate
     * @return startDate >= between > endDate ( ex : 2022-01-01 / 2022-01-03 = [2022-01-01, 2022-01-02] )
     */
    public static List<LocalDateTime> getBetweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        long countOfBetweenDays = ChronoUnit.DAYS.between(startDate, endDate);

        return IntStream.iterate(0, i -> i + 1)
                .limit(countOfBetweenDays)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    /**
     * [TYPE : LocalDate]
     * 두 날짜 사이의 날짜들을 반환하는 메서드
     * @param startDate
     * @param endDate
     * @return startDate >= between > endDate ( ex : 2022-01-01 / 2022-01-03 = [2022-01-01, 2022-01-02] )
     */
    public static List<LocalDate> getBetweenDate(LocalDate startDate, LocalDate endDate) {
        long countOfBetweenDays = ChronoUnit.DAYS.between(startDate, endDate);

        return IntStream.iterate(0, i -> i + 1)
                .limit(countOfBetweenDays)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    /**
     * [TYPE : LocalDate]
     * 두 날짜 사이의 날짜 수 ( 마지막 날짜 미포함 )
     * @param startDate
     * @param endDate
     * @return ex : 2022-01-01 / 2022-01-03 = [2022-01-01, 2022-01-02, 2022-01-03] => 3
     */
    public static long getBetweenDateCount(LocalDateTime startDate, LocalDateTime endDate) {
        long countOfBetweenDays = ChronoUnit.DAYS.between(startDate, endDate);

        return countOfBetweenDays;
    }

    /**
     * [TYPE : LocalDateTime]
     * 두 날짜 사이의 날짜 수 ( param includeEndDate 값 true 로 넘길 시 마지막 날짜 포함 )
     * @param startDate
     * @param endDate
     * @param includeEndDate
     * @return count [ if(includeEndDate) : 2022-01-01 / 2022-01-03 = [2022-01-01, 2022-01-02, 2022-01-03] => 3 ]
     * @return count [ if(!includeEndDate) : 2022-01-01 / 2022-01-03 = [2022-01-01, 2022-01-02] => 2 ]
     */
    public static long getBetweenDateCount(LocalDateTime startDate, LocalDateTime endDate, boolean includeEndDate) {
        long countOfBetweenDays = ChronoUnit.DAYS.between(startDate, endDate);

        if (includeEndDate) countOfBetweenDays ++;

        return countOfBetweenDays;
    }

    /**
     * [TYPE : LocalDate]
     * 두 날짜 사이의 날짜 수 ( param includeEndDate 값 true 로 넘길 시 마지막 날짜 포함 )
     * @param startDate
     * @param endDate
     * @param includeEndDate
     * @return count [ if(includeEndDate) : 2022-01-01 / 2022-01-03 = [2022-01-01, 2022-01-02, 2022-01-03] => 3 ]
     * @return count [ if(!includeEndDate) : 2022-01-01 / 2022-01-03 = [2022-01-01, 2022-01-02] => 2 ]
     */
    public static long getBetweenDateCount(LocalDate startDate, LocalDate endDate, boolean includeEndDate) {
        long countOfBetweenDays = ChronoUnit.DAYS.between(startDate, endDate);

        if (includeEndDate) countOfBetweenDays ++;

        return countOfBetweenDays;
    }

    /**
     * [TYPE : LocalDateTime]
     * 두 날짜 사이의 날짜들을 반환하는 메서드
     * @param startDate
     * @param endDate
     * @return Map<Integer Days, LocalDateTime date>
     */
    public static Map<Integer, LocalDateTime> getBetweenDateWithDays(LocalDateTime startDate, LocalDateTime endDate) {
        List<LocalDateTime> betweenDate = getBetweenDate(startDate, endDate);

        AtomicInteger index = new AtomicInteger();

        Map<Integer, LocalDateTime> result = betweenDate.stream()
                .collect(Collectors.toMap(o -> index.getAndIncrement(), o -> o));

        // 마지막 날짜는 포함이 안되므로 추가
        result.put(index.getAndIncrement(), endDate);

        return result;

    }

    /**
     * [TYPE : LocalDate]
     * 두 날짜 사이의 날짜들을 반환하는 메서드
     * @param startDate
     * @param endDate
     * @return Map<Integer Days, LocalDateTime date>
     */
    public static Map<Integer, LocalDate> getBetweenDateWithDays(LocalDate startDate, LocalDate endDate, boolean includeLastDay) {
        List<LocalDate> betweenDate = getBetweenDate(startDate, endDate);

        AtomicInteger index = new AtomicInteger();

        Map<Integer, LocalDate> result = betweenDate.stream()
                .collect(Collectors.toMap(o -> index.getAndIncrement(), o -> o));

        // 마지막 날짜는 포함이 안되므로 추가
        if (includeLastDay) result.put(index.getAndIncrement(), endDate);

        return result;

    }

}
