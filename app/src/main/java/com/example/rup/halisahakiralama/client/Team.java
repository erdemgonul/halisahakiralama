package com.example.rup.halisahakiralama.client;

import com.google.api.client.util.Key;

import java.math.BigDecimal;
import java.util.List;

public class Team {
    @Key public Long id;
    @Key public String name;
    @Key public String cityName;
    @Key public BigDecimal rate;

}
