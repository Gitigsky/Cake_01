package cn.dangao.Controller.Goods;

import cn.dangao.entity.Goods;
import cn.dangao.entity.Order;
import cn.dangao.entity.Page;
import cn.dangao.entity.Type;
import cn.dangao.service.GoodsService;
import cn.dangao.service.TypeService;
import cn.dangao.service.TypeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
public class GoodstController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private TypeService typeService;
    @RequestMapping("/index")
    public String index( Model model) {
        Map<String,Object> ScrollGood= goodsService.getScrollGood();
        model.addAttribute("scroll",ScrollGood);

        List<Map<String,Object>> newList= goodsService.getGoodsList(3);
        model.addAttribute("newList",newList);

        List<Map<String,Object>>hotList= goodsService.getGoodsList(2);
        model.addAttribute("hotList",hotList);
        System.out.println(hotList.size()+"个");
        return "index";
    }

    @RequestMapping("/goodsrecommend_list")
    public String GoodRecommendList(String type, @RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber, Model model) {

        if (pageNumber <= 0)
            pageNumber = 1;
        Page p = goodsService.getGoodsRecommendPage(Integer.parseInt(type), pageNumber);

        if (p.getTotalPage() == 0) {
            p.setTotalPage(1);
            p.setPageNumber(1);
        } else {
            if (pageNumber>= p.getTotalPage() + 1) {
                p = goodsService.getGoodsRecommendPage(Integer.parseInt(type), p.getTotalPage());
            }
        }
        model.addAttribute("p", p);
        model.addAttribute("t", type);
        return "goodsrecommend_list";
    }


    @RequestMapping("/goods_buy")
    @ResponseBody
    public String GoodsBuy(HttpSession session,Integer goodsid)
    {
        Order o = null;
        if(session.getAttribute("order") != null) {
            o = (Order) session.getAttribute("order");
        }else {
            o = new Order();
            session.setAttribute("order", o);
        }
        Goods goods = goodsService.getGoodsById(goodsid);
        if(goods.getStock()>0) {
            o.addGoods(goods);
            return "ok";
        }else {
            return "fail";
        }
    }


    @RequestMapping("goods_delete")
    @ResponseBody
    public String GoodsDelete(HttpSession session,Integer goodsid)
    {
        Order o = (Order)  session.getAttribute("order");
        o.delete(goodsid);
        return "ok";
    }

    @RequestMapping("goods_detail")
    public String GoodsDetail(Integer id,Model model)
    {
        Goods g = goodsService.getGoodsById(id);
        model.addAttribute("g", g);
        return "goods_detail";
    }
    @RequestMapping("goods_lessen")
    @ResponseBody
    public String goods_lessen(HttpSession session,Integer goodsid)
    {
        Order o = (Order) session.getAttribute("order");
        o.lessen(goodsid);

        return "ok";
    }

    @RequestMapping("goods_list")
    public String goods_list(@RequestParam(value = "typeid" ,defaultValue = "1") Integer typeid,@RequestParam(value = "pageNumber" ,defaultValue = "1") Integer pageNumber,Model model)
    {
        Type t=null;
        if(typeid!=0)
        {
            t=typeService.selectTypeNameByID(typeid);
        }
        model.addAttribute("t",t);
        //List<Goods> list=goodsService.selectGoodsByTypeID(id,1,8);
        //request.setAttribute("goodsList",list);
        if(pageNumber<=0)
            pageNumber=1;
        Page p= goodsService.selectPageByTypeID(typeid,pageNumber);
        System.out.println(p.getList().size()+"个");
        if(p.getTotalPage()==0)
        {
            p.setTotalPage(1);
            p.setPageNumber(1);
        }
        else {
            if(pageNumber>=p.getTotalPage()+1)
            {
                p= goodsService.selectPageByTypeID(typeid,p.getTotalPage());
            }
        }

        model.addAttribute("p",p);
        model.addAttribute("id",String.valueOf(typeid));
        return "goods_list";
    }

    @RequestMapping("goods_search")
    public String goods_search(String keyword,Integer pageNumber,Model model)
    {
        int pageNumber2 = 1;
        if(pageNumber!= null) {
            try {
                pageNumber2=pageNumber;
            }
            catch (Exception e)
            {

            }
        }
        if(pageNumber2<=0)
        {
            pageNumber2=1;
        }
        Page p = goodsService.getSearchGoodsPage(keyword,pageNumber2);

        if(p.getTotalPage()==0)
        {
            p.setTotalPage(1);
            p.setPageNumber(1);
        }
        else {
            if(pageNumber2>=p.getTotalPage()+1)
            {
                p = goodsService.getSearchGoodsPage(keyword,pageNumber2);
            }
        }
        model.addAttribute("p", p);
        try {
            model.addAttribute("keyword", URLEncoder.encode(keyword,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "goods_search";
    }

}
