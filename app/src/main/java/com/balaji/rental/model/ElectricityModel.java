package com.balaji.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElectricityModel {
    String month;
    String dateTaken;
    Double readingValue;
}
