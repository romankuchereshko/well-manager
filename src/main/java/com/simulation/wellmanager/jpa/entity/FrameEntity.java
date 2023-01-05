package com.simulation.wellmanager.jpa.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.simulation.wellmanager.util.UUIDConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FRAME")
public class FrameEntity {

    @Id
//    @Type(type="org.hibernate.type.UUIDCharType")
    @Convert(converter = UUIDConverter.class)
    @Column(name = "ID_FRAME")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "ID_WELL", nullable = false)
    private Long wellId;

    @Column(name = "VOLTAGE", nullable = false)
    private Double voltage;

    @Column(name = "ELECTRIC_CURRENT", nullable = false)
    private Double current;

    @Column(name = "SPEED", nullable = false)
    private Double speed;

    @Column(name = "FREQUENCY", nullable = false)
    private Double frequency;

    @Column(name = "PRESSURE", nullable = false)
    private Double pressure;

    @Column(name = "TEMPERATURE", nullable = false)
    private Double temperature;

    @Column(name = "LIQUID_FLOW_RATE", nullable = false)
    private Double liquidFlowRate;

    @Column(name = "CREATION_DATE_TIME", nullable = false)
    private LocalDateTime creationDateTime;

}
