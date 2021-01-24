package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.model.Ticket;

public abstract class AbstractDAO<T> {
    public DataBaseConfig dataBaseConfig = new DataBaseConfig();
    abstract <S extends T> S update(S object);
}
