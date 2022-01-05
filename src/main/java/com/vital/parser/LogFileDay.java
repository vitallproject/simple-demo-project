package com.vital.parser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class LogFileDay {

    List<LogFileRow> rows;

    public void add(LogFileRow row) {
        rows.add(row);
    }

    public List<LogFileRow> getRows() {
        return rows;
    }
}
