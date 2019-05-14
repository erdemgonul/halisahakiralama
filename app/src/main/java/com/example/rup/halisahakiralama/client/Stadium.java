package com.example.rup.halisahakiralama.client;

import com.google.api.client.util.Key;
import egcom.hsk.entity.metadata.City;
import egcom.hsk.entity.metadata.District;

import java.math.BigDecimal;

public class Stadium {
    @Key public long id;
    @Key public String name;
    @Key public double latitude;
    @Key public double longitude;
    @Key public String city;
    @Key public String district;
    @Key public String address;
    @Key public Integer intervalMinutes;
    @Key public BigDecimal amount;
}
