package cn.dangao.service;

import cn.dangao.dao.Goods.GoodsDao;
import cn.dangao.entity.Goods;
import cn.dangao.entity.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Service
public class GoodsServiceImpl implements GoodsService {


    @Resource
   private GoodsDao goodsDao;
    public List<Map<String,Object>> getGoodsList(int recommendType) {
        List<Map<String,Object>> list=null;
        try {
            list= goodsDao.getGoodsList(recommendType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Map<String,Object> getScrollGood()
    {
        Map<String,Object> scroolGood=null;
        try {
            scroolGood= goodsDao.getScrollGood();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scroolGood;
    }
    public List<Goods> selectGoodsByTypeID(int typeID, int pageNumber, int pageSize)
    {
        List<Goods> list=null;
        try {
            list= goodsDao.selectGoodsByTypeID(typeID,(pageNumber-1)*pageSize,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Page selectPageByTypeID(int typeID,int pageNumber)
    {
        Page p=new Page();
        p.setPageNumber(pageNumber);
        int totalCount=0;
        try {
            totalCount= goodsDao.getCountOfGoodsByTypeID(typeID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        p.SetPageSizeAndTotalCount(8,totalCount);

        List list=null;
        try {
            list= goodsDao.selectGoodsByTypeID(typeID,(pageNumber-1)*8,8);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        p.setList(list);
        return p;
    }
    public Page getGoodsRecommendPage(int type,int pageNumber) {
        Page p = new Page();
        p.setPageNumber(pageNumber);
        int totalCount = 0;
        try {
            totalCount = goodsDao.getRecommendCountOfGoodsByTypeID(type);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.SetPageSizeAndTotalCount(8, totalCount);
        List list=null;
        try {
            list = goodsDao.selectGoodsbyRecommend(type,(pageNumber-1)*8, 8);
            for(Goods g : (List<Goods>)list) {
                g.setScroll(goodsDao.isRecommend(g,1)==null?false:true);
                g.setHot(goodsDao.isRecommend(g,2)==null?false:true);
                g.setNew(goodsDao.isRecommend(g,3)==null?false:true);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.setList(list);
        return p;
    }
    public Goods getGoodsById(int id) {

        Goods g=null;
        try {
            g = goodsDao.getGoodsById(id);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return g;
    }
    public Page getSearchGoodsPage(String keyword, int pageNumber) {
        Page p = new Page();
        p.setPageNumber(pageNumber);
        int totalCount = 0;
        try {
//			totalCount = goodsDao.getGoodsCount(typeId);
            totalCount = goodsDao.getSearchCount(keyword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.SetPageSizeAndTotalCount(8, totalCount);
        List list=null;
        try {
//			list = goodsDao.selectGoods(keyword, pageNo, 8);
            list = goodsDao.selectSearchGoods(keyword,(pageNumber-1)*8,8);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.setList(list);
        return p;
    }
    public void addRecommend(int id,int type) {
        try {
            goodsDao.addRecommend(id, type);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void removeRecommend(int id,int type) {
        try {
            goodsDao.removeRecommend(id, type);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void insert(Goods goods) {
        try {
            goodsDao.insert(goods);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void update(Goods goods) {
        try {
            goodsDao.update(goods);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        try {
            goodsDao.delete(id);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
