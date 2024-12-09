package org.aba2.calendar.common.domain.record.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Weather {

    SUNNY("Sunny"),
    RAINY("Rainy"),
    CLOUDY("Cloudy"),
    SNOWY("Snowy")
    ;

    private final String weather;
}
