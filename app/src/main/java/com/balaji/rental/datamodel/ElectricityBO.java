package com.balaji.rental.datamodel;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Builder
public class ElectricityBO {

    String flatName;

    double meterReading;

    String date;

}
