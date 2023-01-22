package com.simulation.wellmanager.storage.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FRAME")
public class FrameEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FRAME")
    private Long id;

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

    @Column(name = "IS_CRITICAL", nullable = false)
    private Boolean isCritical;

    @Accessors(chain = true)
    @Column(name = "CREATION_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Accessors(chain = true)
    @Column(name = "UPDATING_DATE_TIME", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        final LocalDateTime currentDateTime = LocalDateTime.now();
        if (Objects.isNull(this.createdAt)) {
            this.createdAt = currentDateTime;
        }
        this.updatedAt = currentDateTime;
    }

    @PreUpdate
    public void preUpdate() {
        if (Objects.isNull(this.updatedAt)) {
            this.updatedAt = LocalDateTime.now();
        }
    }

}
