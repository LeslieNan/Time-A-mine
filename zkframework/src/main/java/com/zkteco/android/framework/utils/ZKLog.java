package com.zkteco.android.framework.utils;

import android.util.Log;

public class ZKLog {
    //develop set true,market need to set false
    public final static boolean DEBUG = true;
    //set whether can write to file
    public final static boolean SD_LOG = true;
    /**
     * Log output level
     */
    public enum LEVEL {

        VERBOSE(VERBOSE_LEVEL), DEBUG(DEBUG_LEVEL), INFO(INFO_LEVEL), WARN(WARN_LEVEL), ERROR(
                ERROR_LEVEL);

        private int level;

        LEVEL(final int level) {
            this.level = level;
        }

        public int getLevel() {
            return this.level;
        }
    }

    //private static final String TAG = ZKLog.class.getName();

    private static final int VERBOSE_LEVEL = 1;

    private static final int DEBUG_LEVEL = 2;

    private static final int INFO_LEVEL = 3;

    private static final int WARN_LEVEL = 4;

    private static final int ERROR_LEVEL = 5;

    public static int sLevel = LEVEL.VERBOSE.getLevel();

    public static void v(String tag, String msg) {
        if (DEBUG && sLevel < DEBUG_LEVEL) {
            Log.v(tag, msg);
            if (SD_LOG) {
            }
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG && sLevel < INFO_LEVEL) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG && sLevel < WARN_LEVEL) {
            Log.i(tag, msg);
            if (SD_LOG) {
            }
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG && sLevel < ERROR_LEVEL) {
            Log.w(tag, msg);
            if (SD_LOG) {
            }
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (DEBUG && sLevel < ERROR_LEVEL) {
            Log.w(tag, msg, tr);
            if (SD_LOG) {
            }
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
            if (SD_LOG) {
            }
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (DEBUG) {
            Log.e(tag, msg, e);
            if (SD_LOG) {
            }
        }
    }
}
