package cn.dangao.dao.Goods;

import cn.dangao.entity.Goods;
import cn.dangao.entity.Recommend;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.*;

public interface GoodsDao {
    //select g.id,g.name,g.cover,g.price,t.name typename from recommend r,goods g,type t where type=2 and r.goods_id=g.id and g.type_id=t.id
    public List<Map<String,Object>> getGoodsList(@Param("recommendType") int recommendType) throws SQLException ;

    public Map<String,Object> getScrollGood()throws SQLException;

    public List<Goods> selectGoodsByTypeID(@Param("typeID") int typeID,@Param("pageNumber")int pageNumber,@Param("pageSize")int pageSize) throws SQLException;

    public int getCountOfGoodsByTypeID(@Param("typeID")int typeID) throws SQLException ;

    public List<Goods> selectGoodsbyRecommend(@Param("type") int type,@Param("pageNumber")int pageNumber,@Param("pageSize")int pageSize) throws SQLException ;

    public int getRecommendCountOfGoodsByTypeID(@Param("type") int type) throws SQLException;


    public Goods getGoodsById(@Param("id") int id) throws SQLException ;


    public int getSearchCount(@Param("keyword") String keyword) throws SQLException;



    public List<Goods> selectSearchGoods(@Param("keyword")String keyword, @Param("pageNumber")int pageNumber, @Param("pageSize")int pageSize) throws SQLException;


    public Recommend isRecommend(@Param("g")Goods g,@Param("type")int type) throws SQLException ;

    public void addRecommend(@Param("id")int id,@Param("type")int type) throws SQLException;

    public void removeRecommend(@Param("id")int id,@Param("type")int type) throws SQLException;


    public void insert(@Param("g") Goods g) throws SQLException ;

    public void update(@Param("g")Goods g) throws SQLException ;
    public void delete(@Param("id") int id) throws SQLException ;
}
