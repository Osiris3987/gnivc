package com.example.logist_sevice.model.race;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "geo_positions")
@Embeddable
@Data
public class GeoPosition {
    @Column(name = "point")
    private String point;
}
