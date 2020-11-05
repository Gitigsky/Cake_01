package cn.dangao.Controller.Users;


import cn.dangao.entity.User;
import cn.dangao.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class UserController {
    @Resource
    private UserServiceImpl userService;

    /**
     * 登入界面跳转
     * @return
     */
    @RequestMapping("/login.html")
    public String userLogin(){
        return "user_login";
    }

    /**
     * 处理登入
     * @param username
     * @param password
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "login.html",method = RequestMethod.POST)
    private String doUserLogin(String username, String password, HttpSession session, HttpServletRequest request){
        User user = userService.login(username, password);
        if(user==null) {
            request.setAttribute("failMsg", "用户名、邮箱或者密码错误，请重新登录！");
            return "user_login";
        }else {
            session.setAttribute("user", user);
            return "redirect:/user_center.html";
        }
    }

    /**
     * 注册界面跳转
     * @return
     */
    @RequestMapping("/register.html")
    public String userRegister(){
        return "user_register";
    }

    /**
     * 处理注册
     * @param user
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/register.html",method = RequestMethod.POST)
    public String doUserRegister(User user, HttpServletRequest request, Model model){
        if(userService.register(user)) {
            model.addAttribute("msg", "注册成功，请登录！");
            return "user_login";
        }else {
            model.addAttribute("msg", "用户名或邮箱重复，请重新填写！");
            return "user_register";
        }
    }

    /**
     * 个人中心跳转
     * @return
     */
    @RequestMapping("/center.html")
    public String userCenter(){
        return "user_center";
    }

    /**
     * 修改个人信息
     * @param model
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "enter.html",method = RequestMethod.POST)
    private String doUserCenter(Model model,String username,String password,HttpSession session){
        //修改收货信息
//        String ue = request.getParameter("ue");
//        String password = request.getParameter("password");
        User user = userService.login(username, password);
        if(user==null) {
            model.addAttribute("failMsg", "用户名、邮箱或者密码错误，请重新登录！");
            return "user_login";
        }else {
            session.setAttribute("user", user);
            return "user_center";
        }
    }

    /**
     * 修改安全信息
     * @param session
     * @param newPwd
     * @param password
     * @param model
     * @return
     */
    @RequestMapping(value = "enter.html",method = RequestMethod.POST)
    public String fixPwd(HttpSession session,String newPwd,String password,Model model){
//        String password = request.getParameter("password");
//        String newPwd = request.getParameter("newPassword");

        User u = (User) session.getAttribute("user");
        if(password.equals(u.getPassword())) {
            u.setPassword(newPwd);
            userService.updatePwd(u);
            model.addAttribute("msg", "修改成功！");
            return "user_center";
        }else {
            model.addAttribute("failMsg", "修改失败，原密码不正确，你再想想！");
            return "user_center";
        }
    }
    /**
     * 退出
     * @param session
     * @return
     */
    @RequestMapping(value = "/loginOut.html",method = RequestMethod.GET)
    public String longOut(HttpSession session){
        session.removeAttribute("user");
        return "index";
    }

    //后台用户管理

//    @RequestMapping("user_list.html")
//    public  String user_list(){
//        int pageNumber = 1;
//        if(request.getParameter("pageNumber") != null) {
//            try {
//                pageNumber=Integer.parseInt(request.getParameter("pageNumber") ) ;
//            }
//            catch (Exception e)
//            {
//
//            }
//
//        }
//        if(pageNumber<=0)
//            pageNumber=1;
//        Page p = uService.getUserPage(pageNumber);
//        if(p.getTotalPage()==0)
//        {
//            p.setTotalPage(1);
//            p.setPageNumber(1);
//        }
//        else {
//            if(pageNumber>=p.getTotalPage()+1)
//            {
//                p = uService.getUserPage(pageNumber);
//            }
//        }
//        request.setAttribute("p", p);
//        request.getRequestDispatcher("/statics/admin/user_list.jsp").forward(request, response);
//        return "";
//    }

    /**
     * 调价用户页面跳转
     * @return
     */
    @RequestMapping("/userAdd.html")
    public String userAdd(){
        return "user_add";
    }

    /**
     * 保存用户
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/userAdd.html",method = RequestMethod.POST)
    public String saveAddUser(User user,Model model){
        if(userService.register(user)) {
            model.addAttribute("msg", "客户添加成功！");
            return "redirect:/admin/user_list";
        }else {
            model.addAttribute("failMsg", "用户名或邮箱重复，请重新填写！");
            model.addAttribute("u",user);
            return "/admin/user_add";
        }
    }

    /**
     * 重置密码页面跳转
     * @return
     */
    @RequestMapping("/resetPwd.html")
    public String resetPwd(int id,Model model){
        User user = userService.selectById(id);
        model.addAttribute("param",user);
        return "user_reset";
    }

    /**
     * 保存重置密码
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/resetPwd.html",method = RequestMethod.POST)
    public String doResetPwd(Model model,User user){
        userService.updateUserAddress(user);
        return "redirect:/admin/user_list";
    }

    /**
     * 修改用户页面跳转
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/fixUser.html")
    public String fixUser(int id,Model model){
        User user = userService.selectById(id);
        model.addAttribute("u",user);
        return "user_edit";
    }

    /**
     *
     * @param u
     * @return
     */
    @RequestMapping(value = "/fixUser.html",method = RequestMethod.POST)
    public String saveFixUser(User u){
        userService.updateUserAddress(u);
        return "redirect:/admin/user_list";
    }

    /**
     * 删除用户
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/deleteUser.html")
    public String deleteUser(int id,Model model){
        boolean isSuccess = userService.delete(id);
        if(isSuccess) {
            model.addAttribute("msg", "客户删除成功");
        }else {
            model.addAttribute("failMsg", "客户有下的订单，请先删除该客户下的订单，再来删除客户！");
        }
        return "/admin/user_list";
    }

}
