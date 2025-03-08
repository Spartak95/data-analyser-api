package com.xcoder.data.analyser.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "data")
@NoArgsConstructor
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sensorId;
    private LocalDateTime timestamp;
    private double measurement;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MeasurementType measurementType;

    @Override
    public String toString() {
        return "Data{" +
            "sensorId=" + sensorId +
            ", timestamp=" + timestamp +
            ", measurement=" + measurement +
            ", measurementType=" + measurementType +
            '}';
    }
}
