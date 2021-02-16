package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;

public abstract class AbstractDAO<T> {
    public DataBaseConfig dataBaseConfig = new DataBaseConfig();
    abstract <S extends T> S update(S object);
}
