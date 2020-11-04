package cn.dangao.dao.Type;
import cn.dangao.entity.Type;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface TypeDao {
    /**
     * 获取所有类别
     * @return
     * @throws SQLException
     */
    public List<Type> GetAllType() throws SQLException;


    /**
     * 根据类别id查询
     * @param typeid
     * @return
     * @throws SQLException
     */
    public Type selectTypeNameByID(@Param("typeid") int typeid) throws SQLException;

    /**
     * 根据id 查询
     * @param id
     * @return
     * @throws SQLException
     */
    public Type select(@Param("id") int id) throws SQLException;

    /**
     * 新增
     * @param t
     * @throws SQLException
     */
    public void insert(@Param("t") Type t) throws SQLException;

    /**
     * 修改
     * @param t
     * @throws SQLException
     */
    public void update(@Param("t") Type t) throws SQLException;

    /**
     * 删除
     * @param id
     * @throws SQLException
     */
    public void delete(@Param("id") int id) throws SQLException;
}
