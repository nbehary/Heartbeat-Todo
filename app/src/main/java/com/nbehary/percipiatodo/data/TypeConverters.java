package com.nbehary.percipiatodo.data;

import androidx.room.TypeConverter;

import java.util.Date;

public class TypeConverters {

    //These are straight copied from the Room documentation: https://developer.android.com/training/data-storage/room/referencing-data
    //TODO: Rewrite to use OffsetDateTime...using Date is bad.
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
