package com.machine.client.iam.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthTokenQuerySeriesDto {

    public AuthTokenQuerySeriesDto(String series) {
        this.series = series;
    }

    private String series;
}
