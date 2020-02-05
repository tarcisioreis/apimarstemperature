package com.apimars.apimarstemperature.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataDTO implements Serializable {
    private int sol;
    private Double average;
}
