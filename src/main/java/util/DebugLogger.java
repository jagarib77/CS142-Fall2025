package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static util.config.SimulationConstants.*;

public class DebugLogger {
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static boolean enabled = ENABLE_DEFAULT;

    private DebugLogger() {
    }

    public static void setEnabled(boolean on) {
        enabled = on;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    private static void log(String level, String message) {
        if (!enabled) return;

        String timestamp = LocalDateTime.now().format(TIME);
        System.out.printf("[%s] [%5s] %s%n", timestamp, level, message);
    }

    public static void info(String msg) {
        log(LOG_INFO, msg);
    }

    public static void turn(String msg) {
        log(LOG_TURN, msg);
    }

    public static void spawn(String msg) {
        log(LOG_SPAWN, msg);
    }

    public static void death(String msg) {
        log(LOG_DEATH, msg);
    }

    public static void infect(String msg) {
        log(LOG_INFECT, msg);
    }

    public static void combat(String msg) {
        log(LOG_COMBAT, msg);
    }

    public static void undead(String msg) {
        log(LOG_UNDEAD, msg);
    }

    public static void settlement(String msg) {
        log(LOG_SETTLEMENT, msg);
    }

    public static void entity(String msg) {
        log(LOG_ENTITY, msg);
    }

    public static void debug(String msg) {
        log(LOG_DEBUG, msg);
    }
    public static void pickup(String msg) {
        log(LOG_PICKUP, msg);
    }
}
