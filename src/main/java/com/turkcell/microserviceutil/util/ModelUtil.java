package com.turkcell.microserviceutil.util;

import com.turkcell.microserviceutil.model.AbstractEntity;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;
import java.util.UUID;

public class ModelUtil extends UUIDGenerator
{
    public static String generateUUID()
    {
        return UUID.randomUUID().toString();
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException
    {
        if (object instanceof AbstractEntity)
        {
            AbstractEntity abstractEntity = (AbstractEntity) object;
            if (abstractEntity.getId() != null)
            {
                return abstractEntity.getId();
            }
        }
        return super.generate(session, object);
    }
}