package com.example.demo.model.dto.airline;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddAirlineReviewDto {

    private int reviewerId;

    private int airlineId;

    private String review;

}
