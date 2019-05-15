package com.example.rup.halisahakiralama.client;

import com.google.api.client.util.Key;

public class TimeSlot {
    @Key public String date;
    @Key public String beginHour;
    @Key public String endHour;
    @Key public long stadiumId;
    @Key public String reservationStatus;

}
