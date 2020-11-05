package cn.dangao.service;

import cn.dangao.entity.Type;

import java.sql.SQLException;
import java.util.List;

public interface TypeService {

    public List<Type> GetAllType() ;
    public Type selectTypeNameByID(int typeid) ;
    public Type select(int id) ;
    public void insert(Type t);
    public void update(Type t);
    public boolean delete(int id) ;
}
