package cn.dangao.service;

import cn.dangao.entity.Page;
import cn.dangao.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {


    public boolean register(User user);

    public User login(String ue,String password) ;
    public User selectById(int id) ;
    public void updateUserAddress(User user) ;
    public void updatePwd(User user) ;

    public Page getUserPage(int pageNumber) ;

    public boolean delete(int id ) ;
}
