package cn.dangao.Controller.Goods;


import cn.dangao.entity.Goods;
import cn.dangao.entity.Page;
import cn.dangao.service.GoodsService;
import cn.dangao.service.TypeService;
import com.sun.deploy.net.HttpRequest;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.List;

@Controller
public class AdminGoodsController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private TypeService typeService;


    //查询
    @RequestMapping("/admin/goods_list")
    public String goods_list(@RequestParam(value = "type",defaultValue = "0") Integer type, @RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber, Model model)
    {

        if(pageNumber<=0)
            pageNumber=1;
        Page p = goodsService.getGoodsRecommendPage(type, pageNumber);
        if(p.getTotalPage()==0)
        {
            p.setTotalPage(1);
            p.setPageNumber(1);
        }
        else {
            if(pageNumber>=p.getTotalPage()+1)
            {
                p = goodsService.getGoodsRecommendPage(type, pageNumber);
            }
        }
        model.addAttribute("p", p);
        model.addAttribute("type", type);
        return "/statics/admin/goods_list";
    }

    //新增食品
    @RequestMapping("/admin/goods_add")
    public String goods_add(HttpServletRequest request) throws IOException {
        DiskFileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> list = upload.parseRequest(request);
            Goods g = new Goods();
            for(FileItem item:list) {
                if(item.isFormField()) {
                    switch(item.getFieldName()) {
                        case "name":
                            g.setName(item.getString("utf-8"));
                            break;
                        case "price":
                            g.setPrice(Integer.parseInt(item.getString("utf-8")));
                            break;
                        case "intro":
                            g.setIntro(item.getString("utf-8"));
                            break;
                        case "stock":
                            g.setStock(Integer.parseInt(item.getString("utf-8")));
                            break;
                        case "typeid":
                            g.setTypeid(Integer.parseInt(item.getString("utf-8")));
                            break;
                    }
                }else {
                    if(item.getInputStream().available()<=0)continue;
                    String fileName = item.getName();
                    fileName = fileName.substring(fileName.lastIndexOf("."));
                    fileName = "/"+new Date().getTime()+fileName;
                    String path = request.getServletContext().getRealPath("/picture")+fileName;
                    InputStream in = item.getInputStream();
                    FileOutputStream out = new FileOutputStream(path);
                    byte[] buffer = new byte[1024];
                    int len=0;
                    while( (len=in.read(buffer))>0 ) {
                        out.write(buffer);
                    }
                    in.close();
                    out.close();
                    item.delete();
                    switch(item.getFieldName()) {
                        case "cover":
                            g.setCover("/picture"+fileName);
                            break;
                        case "image1":
                            g.setImage1("/picture"+fileName);
                            break;
                        case "image2":
                            g.setImage2("/picture"+fileName);
                            break;
                    }
                }
            }
            goodsService.insert(g);
          return "static/admin/goods_list";
        } catch (FileUploadException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }

    //删除商品
    @RequestMapping("/admin/goods_delete")
    public String goods_delete(Integer id)
    {
        goodsService.delete(id);

        return "redirect:/admin/goods_list";
    }


    //修改商品
    @RequestMapping("admin/goods_edit")
    public String xx(HttpServletRequest request) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> list = upload.parseRequest(request);
            Goods g = new Goods();
            int pageNumber = 1;
            int type = 0;
            for (FileItem item : list) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "id":
                            g.setId(Integer.parseInt(item.getString("utf-8")));
                            break;
                        case "name":
                            g.setName(item.getString("utf-8"));
                            break;
                        case "price":
                            g.setPrice(Float.parseFloat(item.getString("utf-8")));
                            break;
                        case "intro":
                            g.setIntro(item.getString("utf-8"));
                            break;
                        case "cover":
                            g.setCover(item.getString("utf-8"));
                            break;
                        case "image1":
                            g.setImage1(item.getString("utf-8"));
                            break;
                        case "image2":
                            g.setImage2(item.getString("utf-8"));
                            break;
                        case "stock":
                            g.setStock(Integer.parseInt(item.getString("utf-8")));
                            break;
                        case "typeid":
                            g.setTypeid(Integer.parseInt(item.getString("utf-8")));
                            break;
                        case "pageNumber":
                            pageNumber = Integer.parseInt(item.getString("utf-8"));
                            break;
                        case "type":
                            type = Integer.parseInt(item.getString("utf-8"));
                            break;
                    }
                } else {
                    if (item.getInputStream().available() <= 0) continue;
                    String fileName = item.getName();
                    fileName = fileName.substring(fileName.lastIndexOf("."));
                    fileName = "/" + new Date().getTime() + fileName;
                    String path = request.getServletContext().getRealPath("/picture") + fileName;
                    InputStream in = item.getInputStream();
                    FileOutputStream out = new FileOutputStream(path);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer);
                    }
                    in.close();
                    out.close();
                    item.delete();
                    switch (item.getFieldName()) {
                        case "cover":
                            g.setCover("/picture" + fileName);
                            break;
                        case "image1":
                            g.setImage1("/picture" + fileName);
                            break;
                        case "image2":
                            g.setImage2("/picture" + fileName);
                            break;
                    }
                }
            }
            goodsService.update(g);
            return "static/admin/goods_list?pageNumber=" + pageNumber + "&type=" + type;
        } catch (FileUploadException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //修改页面
    @RequestMapping("admin/goods_editshow")
    public String goods_editshow(Integer id,Model model)
    {
        Goods g = goodsService.getGoodsById(id);
        model.addAttribute("g", g);
       return "statics/admin/goods_edit";
    }
}
