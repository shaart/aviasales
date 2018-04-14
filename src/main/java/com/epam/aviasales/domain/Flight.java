package com.epam.aviasales.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Flight implements Serializable {

  private Long id;
  private Airport fromAirport;
  private Airport toAirport;
  private Airplane airplane;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private Integer baseTicketPrice;
  private Integer extraBaggagePrice;
  private Integer freeSeatEconomy;
  private Integer freeSeatBusiness;
}
