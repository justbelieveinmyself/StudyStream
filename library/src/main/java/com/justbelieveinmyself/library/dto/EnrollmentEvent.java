package com.justbelieveinmyself.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentEvent {
    private long userId;
    private String courseTitle;
    private EnrollmentEventType eventType;
}
