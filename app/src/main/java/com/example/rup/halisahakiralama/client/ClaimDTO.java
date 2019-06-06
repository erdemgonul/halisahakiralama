package com.example.rup.halisahakiralama.client;

import com.google.api.client.util.Key;

public class ClaimDTO {
    @Key public Long id;
    @Key public String willing;
    @Key public String stadium;
    @Key public String reservationDate;
    @Key public String beginHour;
    @Key public String endHour;
}
