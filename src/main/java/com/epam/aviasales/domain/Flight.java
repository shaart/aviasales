package com.epam.aviasales.domain;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

  @Override
  public String toString() {
    return fromAirport.getName()+" ("+departureTime+") -> "+toAirport.getName()+" ("+arrivalTime+")";
  }
}
