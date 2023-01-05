package com.simulation.wellmanager.util;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UUIDConverter implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(UUID attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        return dbData == null ? null : UUID.fromString(dbData);
    }
}
