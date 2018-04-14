package com.epam.aviasales.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Flight implements Serializable {

  private Long id;
  private Airport fromAirportId;
  private Airport toAirportId;
  private Airplane airplaneId;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private Integer baseTicketPrice;
  private Integer extraBaggagePrice;
  private Integer freeSeatEconomy;
  private Integer freeSeatBusiness;
}
