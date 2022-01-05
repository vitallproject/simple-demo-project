package com.vital.parser;
import com.vital.util.StringUtils;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.joining;

@AllArgsConstructor
public class TimeReportGenerator implements ReportGenerator {

    private final ActivityUtil activityUtil;

    @Override
    public String generate(List<LogFileDay> logFileDays) {
        return logFileDays.stream()
                .map(this::convertDay)
                .collect(joining(System.lineSeparator()));
    }

    private String convertDay(LogFileDay logFileDay) {
        var result = new StringBuilder();
        activityUtil.consumeActivityDuration(logFileDay, (currentActivity, nextActivity) ->
                result.append(currentActivity.getTime().toString())
                        .append(StringUtils.DASH)
                        .append(nextActivity.getTime().toString())
                        .append(StringUtils.SPACE)
                        .append(currentActivity.getActivityName())
                        .append(System.lineSeparator()));

        return result.toString();
    }
}
