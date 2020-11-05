package cn.dangao.service;

import cn.dangao.entity.Goods;
import cn.dangao.entity.Page;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface GoodsService {

    public List<Map<String,Object>> getGoodsList(int recommendType);

    public Map<String,Object> getScrollGood();

    public List<Goods> selectGoodsByTypeID(int typeID, int pageNumber, int pageSize);

    public Page getGoodsRecommendPage(int type,int pageNumber);

    public Page selectPageByTypeID(int typeID, int pageNumber);


    public Goods getGoodsById(int id);

    public Page getSearchGoodsPage(String keyword, int pageNumber) ;

    public void addRecommend(int id,int type);

    public void removeRecommend(int id,int type) ;
    public void insert(Goods goods) ;

    public void update(Goods goods) ;

    public void delete(int id);
}
