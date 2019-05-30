package com.example.rup.halisahakiralama.client;

import com.google.api.client.util.Key;

import java.math.BigDecimal;

public class Player {
    @Key public long id;
    @Key public String name;
    @Key public String surName;
    @Key public String position;
    @Key public String address;
    @Key public BigDecimal rate;
}
