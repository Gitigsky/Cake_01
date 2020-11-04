package cn.dangao.dao.User;

import cn.dangao.entity.User;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface UserDao{
    public void addUser(@Param("user") User user) throws SQLException ;

    public User isUsernameExist(@Param("username") String username) throws SQLException ;

    public User isEmailExist(@Param("email") String email) throws SQLException ;

    public User selectByUsernamePassword(@Param("username") String username,@Param("password") String password) throws SQLException ;

    public User selectByEmailPassword(@Param("email") String email,@Param("email") String password) throws SQLException ;

    public User selectById(@Param("id") int id) throws SQLException ;

    public void updateUserAddress(@Param("user") User user) throws SQLException ;

    public void updatePwd(@Param("user") User user) throws SQLException ;

    public int selectUserCount() throws SQLException ;

    public List selectUserList(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize) throws SQLException ;

    public void delete(@Param("id") int id) throws SQLException ;
}
