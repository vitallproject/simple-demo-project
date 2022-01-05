package com.vital.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalTime;

@AllArgsConstructor
@Value
@Getter
public class LogFileRow {

    @Getter
    LocalTime time;
    @Getter
    String activityName;

}
