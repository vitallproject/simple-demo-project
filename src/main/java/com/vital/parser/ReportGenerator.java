package com.vital.parser;

import java.util.List;

public interface ReportGenerator {

    String generate(List<LogFileDay> logFileDays);
}
