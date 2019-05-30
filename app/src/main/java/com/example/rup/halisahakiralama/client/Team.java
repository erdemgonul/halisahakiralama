package com.example.rup.halisahakiralama.client;

import com.google.api.client.util.Key;

import java.math.BigDecimal;
import java.util.List;

public class Team {
    @Key public String name;
    @Key public String owner;
    @Key public BigDecimal rate;
    @Key public List<String> players;
}
