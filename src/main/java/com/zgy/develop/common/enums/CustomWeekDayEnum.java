package com.zgy.develop.common.enums;

/**
 * @author zgy
 * @data 2021/4/18 13:29
 */

public class CustomWeekDayEnum {

    public static final CustomWeekDayEnum MONDAY;
    public static final CustomWeekDayEnum TUESDAY;
    public static final CustomWeekDayEnum WEDNESDAY;
    public static final CustomWeekDayEnum THURSDAY;
    public static final CustomWeekDayEnum FRIDAY;
    public static final CustomWeekDayEnum SATURDAY;
    public static final CustomWeekDayEnum SUNDAY;
    private static final CustomWeekDayEnum[] VALUES;

    static {
        MONDAY = new CustomWeekDayEnum(1, "星期一");
        TUESDAY = new CustomWeekDayEnum(2, "星期二");
        WEDNESDAY = new CustomWeekDayEnum(3, "星期三");
        THURSDAY = new CustomWeekDayEnum(4, "星期四");
        FRIDAY = new CustomWeekDayEnum(5, "星期五");
        SATURDAY = new CustomWeekDayEnum(6, "星期六");
        SUNDAY = new CustomWeekDayEnum(7, "星期日");
        VALUES = new CustomWeekDayEnum[] {
                MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
        };
    }

    public final Integer code;
    public final String desc;

    private CustomWeekDayEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public CustomWeekDayEnum[] values() {
        return this.VALUES;
    }
}
