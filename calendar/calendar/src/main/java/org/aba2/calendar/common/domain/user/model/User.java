package org.aba2.calendar.common.domain.user.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {

    private String id;

    private String password;

    private String name;

}

