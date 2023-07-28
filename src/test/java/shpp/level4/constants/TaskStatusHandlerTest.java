package shpp.level4.constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shpp.level4.configs.MvcConfig;
import shpp.level4.configs.TaskStatusHandlerConfig;

import static org.junit.jupiter.api.Assertions.*;
import static shpp.level4.constants.TaskStatuses.*;

class TaskStatusHandlerTest {

    private TaskStatusHandler taskStatusHandler;

    @BeforeEach
    void setUp() {
        TaskStatusHandlerConfig config = new TaskStatusHandlerConfig();
        taskStatusHandler = config.getTaskStatusHandler();
    }

    @Test
    void testValidTransitions() {
        assertTrue(taskStatusHandler.isHandleTransition(PLANNED, WORK_IN_PROGRESS));
        assertTrue(taskStatusHandler.isHandleTransition(PLANNED, POSTPONED));
        assertTrue(taskStatusHandler.isHandleTransition(PLANNED, CANCELLED));
        assertTrue(taskStatusHandler.isHandleTransition(PLANNED, PLANNED));

        assertTrue(taskStatusHandler.isHandleTransition(WORK_IN_PROGRESS, NOTIFIED));
        assertTrue(taskStatusHandler.isHandleTransition(WORK_IN_PROGRESS, SIGNED));
        assertTrue(taskStatusHandler.isHandleTransition(WORK_IN_PROGRESS, CANCELLED));
        assertTrue(taskStatusHandler.isHandleTransition(WORK_IN_PROGRESS, WORK_IN_PROGRESS));

        assertTrue(taskStatusHandler.isHandleTransition(POSTPONED, NOTIFIED));
        assertTrue(taskStatusHandler.isHandleTransition(POSTPONED, SIGNED));
        assertTrue(taskStatusHandler.isHandleTransition(POSTPONED, CANCELLED));
        assertTrue(taskStatusHandler.isHandleTransition(POSTPONED, POSTPONED));

        assertTrue(taskStatusHandler.isHandleTransition(NOTIFIED, SIGNED));
        assertTrue(taskStatusHandler.isHandleTransition(NOTIFIED, CANCELLED));
        assertTrue(taskStatusHandler.isHandleTransition(NOTIFIED, DONE));
        assertTrue(taskStatusHandler.isHandleTransition(NOTIFIED, NOTIFIED));

        assertTrue(taskStatusHandler.isHandleTransition(SIGNED, DONE));
        assertTrue(taskStatusHandler.isHandleTransition(SIGNED, CANCELLED));
        assertTrue(taskStatusHandler.isHandleTransition(SIGNED, SIGNED));

        assertTrue(taskStatusHandler.isHandleTransition(DONE, DONE));
        assertTrue(taskStatusHandler.isHandleTransition(CANCELLED, CANCELLED));
    }

    @Test
    void testInvalidTransitions() {

        assertFalse(taskStatusHandler.isHandleTransition(PLANNED, NOTIFIED));
        assertFalse(taskStatusHandler.isHandleTransition(WORK_IN_PROGRESS, PLANNED));
        assertFalse(taskStatusHandler.isHandleTransition(POSTPONED, WORK_IN_PROGRESS));
        assertFalse(taskStatusHandler.isHandleTransition(NOTIFIED, WORK_IN_PROGRESS));
        assertFalse(taskStatusHandler.isHandleTransition(SIGNED, PLANNED));
        assertFalse(taskStatusHandler.isHandleTransition(CANCELLED, NOTIFIED));
        assertFalse(taskStatusHandler.isHandleTransition(DONE, POSTPONED));
    }
}