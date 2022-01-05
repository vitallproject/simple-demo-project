package com.vital.service;

import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.vital.parser.LogFileParser;
import com.vital.parser.StatisticReportGenerator;
import com.vital.parser.TimeReportGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class LogFileService {

    private final LogFileParser logFileParser;
    private final TimeReportGenerator timeReportGenerator;
    private final StatisticReportGenerator statisticReportGenerator;

    @SneakyThrows
    public List<List<String>> process(Part logFile) {
        var strings = new ArrayList<List<String>>();
        try (var inputStream = logFile.getInputStream()) {
            var logs = new String(inputStream.readAllBytes());
            var logFileDays = logFileParser.parse(logs);
            var timeReport = timeReportGenerator.generate(logFileDays);
            var statisticReport = statisticReportGenerator.generate(logFileDays);
            var timeRep = Arrays.stream(timeReport.split(System.lineSeparator())).toList();
            var statRep = Arrays.stream(statisticReport.split(System.lineSeparator())).toList();
            strings.add(timeRep);
            strings.add(statRep);
        }
        return strings;
    }
}

