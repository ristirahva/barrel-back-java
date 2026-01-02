package org.km.db.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.km.db.entity.Barrel;
import org.km.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Converter(autoApply = true)
public class BurnLevelEnumConverter implements AttributeConverter<Barrel.BurnLevel, String> {

    private static final Logger log = LoggerFactory.getLogger(BurnLevelEnumConverter.class);

    @Override
    public String convertToDatabaseColumn(Barrel.BurnLevel burnLevel) {
        if (burnLevel == null) {
            log.debug("Уровень обжига не задан");
            return null;
        }

        String result = burnLevel.getName();
        log.debug("Преобразование уровня обжига в БД: {} -> {}", burnLevel, result);
        return result;
    }

    @Override
    public Barrel.BurnLevel convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            log.debug("Уровень обжига не задан");
            return null;
        }

        Barrel.BurnLevel result = Barrel.BurnLevel.fromName(dbData);
        log.debug("Converting from DB: {} -> {}", dbData, result);
        return result;
    }
}