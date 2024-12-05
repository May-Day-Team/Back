package org.aba2.calendar.common.domain.record.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Weather {

    Sunny("Sunny"),
    Rainy("Rainy"),
    Cloudy("Cloudy"),
    Snowy("Snowy")
    ;

    private final String weather;
}
