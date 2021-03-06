package cn.dangao.service;

import cn.dangao.dao.Type.TypeDao;
import cn.dangao.entity.Type;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Service
public class TypeServiceImpl implements  TypeService {

    @Resource
   private TypeDao typeDao;

    //TypeDao typeDao=new TypeDao();

    public List<Type> GetAllType() {
        List<Type> list=null;
        try {
            list= typeDao.GetAllType();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Type selectTypeNameByID(int typeid) {
        Type type=null;
        try {
            type= typeDao.selectTypeNameByID(typeid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }
    public Type select(int id) {
        Type t=null;
        try {
            t = typeDao.select(id);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return t;
    }
    public void insert(Type t) {
        try {
            typeDao.insert(t);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void update(Type t) {
        try {
            typeDao.update(t);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public boolean delete(int id) {
        try {
            typeDao.delete(id);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
