package com.vital.report;

import com.vital.parser.ActivityUtil;
import com.vital.parser.LogFileDay;
import com.vital.parser.LogFileRow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.function.BiConsumer;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityUtilUnitTest {

    private ActivityUtil activityUtil = new ActivityUtil();

    @Test
    void consumeActivityDurationTest(@Mock LogFileDay logFileDay, @Mock BiConsumer<LogFileRow, LogFileRow> consumer) {
       var rows = List.of(
                new LogFileRow(LocalTime.now(), "activity"),
                new LogFileRow(LocalTime.now(), "activity"));

        doReturn(rows).when(logFileDay).getRows();

        activityUtil.consumeActivityDuration(logFileDay, consumer);

        verify(consumer).accept(rows.get(0), rows.get(1));
    }
}