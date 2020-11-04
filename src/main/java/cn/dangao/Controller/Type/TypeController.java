package cn.dangao.Controller.Type;
import cn.dangao.entity.Type;
import cn.dangao.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TypeController {

    @Resource
    private TypeService service;

    /**
     * 新增
     * @param name
     * @return
     */
    @RequestMapping("/admin/type_add")
    public String addType(String name){
        service.insert(new Type(name));
        return "redirect:/admin/type_list";
    }


    /**
     * 删除的方法
     * @param id
     * @return
     */
    @RequestMapping("/admin/type_delete")
    @ResponseBody
    public Map deleteType(Integer id){
        Map<String,Object> map = new HashMap<>();
        boolean b = service.delete(id);
        if (b){
            map.put("verify","true");
        }else{
            map.put("verify","false");
        }
        return map;
    }

    @RequestMapping("/admin/type_edit")
    public String updateDoType(Integer id,Model model){
        Type type = service.selectTypeNameByID(id);
        model.addAttribute("type",type);
        return "type_edit";
    }
    /**
     * 修改的方法
     * @param type
     * @return
     */
    @RequestMapping("/admin/doType_edit")
    public String updateType(Type type){
        service.update(type);
        return "redirect:/admin/type_list";
    }


    @RequestMapping("/admin/type_list.html")
    public String select(Model model){
        List<Type> types = service.GetAllType();
        model.addAttribute("types",types);
        return "redirect:/admin/type_list";
    }

}
