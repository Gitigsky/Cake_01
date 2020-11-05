package cn.dangao.Controller.Type;
import cn.dangao.entity.Type;
import cn.dangao.service.TypeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TypeController {

    @Resource
    private TypeServiceImpl service;

    /**
     * 新增
     * @param
     * @return
     */
    @RequestMapping("/admin/type_add")
    public String addType(String name){
        service.insert(new Type(name));
        return "redirect:/aType_list";
    }


    /**
     * 删除的方法
     * @param id
     * @return
     */
    @RequestMapping("/admin/type_delete")
    public String deleteType(Integer id){
       // Map<String,Object> map = new HashMap<>();
        service.delete(id);
       /* if (b){
            map.put("verify","true");
        }else{
            map.put("verify","false");
        }
        return map;*/
        return "redirect:/aType_list";
    }

    @RequestMapping("/admin/type_edit")
    public String updateDoType(Integer id,Model model){
        Type type = service.selectTypeNameByID(id);
        model.addAttribute("param",type);
        return "statics/admin/type_edit";
    }
    /**
     * 修改的方法
     * @param type
     * @return
     */
    @RequestMapping(value = "/admin/doType_edit",method = RequestMethod.POST)
    public String updateType(Type type){
        service.update(type);
        return "redirect:/aType_list";
    }


    @RequestMapping("/aType_list")
    public String select(Model model){
        List<Type> types = service.GetAllType();
        model.addAttribute("types",types);
        return "statics/admin/type_list";
    }



}
