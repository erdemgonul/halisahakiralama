package com.example.rup.halisahakiralama.client;

import com.google.api.client.util.Key;

public class Reservation {
    @Key public Long id;
    @Key public String stadium;
    @Key public String user;
    @Key public String reservationDate;
    @Key public String beginHour;
    @Key public String endHour;
    @Key public String reservationStatus;
    @Key public String comment;
}
