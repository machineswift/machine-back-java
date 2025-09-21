package com.machine.client.iam.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamAuthTokenQuerySeriesDto {

    public IamAuthTokenQuerySeriesDto(String series) {
        this.series = series;
    }

    private String series;
}
