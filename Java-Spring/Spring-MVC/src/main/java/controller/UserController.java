package controller;

import domain.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
//@RequestMapping("/usr")
public class UserController {

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public String save() {
        System.out.println("user save ...");
        return "{'module':'usr save'}";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable Integer id) {
        System.out.println("user delete ..." + id);
        return "{'module':'usr delete'}";
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @ResponseBody
    public String update(@RequestBody User user) {
        System.out.println("user update ..." + user);
        return "User updated";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getById(@PathVariable Integer id) {
        System.out.println("user update ..." + id);
        return "User Id Got";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public String getAll() {
        System.out.println("user getAll ...");
        return "User get All";
    }

//    @RequestMapping("/commonPar")
//    @ResponseBody
//    public String commonPar(String name, int age) {
//        System.out.println("name ==> " + name);
//        System.out.println("Age ==> " + age);
//        return "{'module':'common Param'}";
//    }
//
//    @RequestMapping("/pojoPar")
//    @ResponseBody
//    public String pojoPar(@RequestBody User user) {
//        System.out.println(user);
//        return user.toString();
//    }
//
//    @RequestMapping("/likes")
//    @ResponseBody
//    public String arrayPars(String[] likes) {
//        System.out.println(Arrays.toString(likes));
//        return Arrays.toString(likes);
//    }
//
//    @RequestMapping("/likesArray")
//    @ResponseBody
//    public String arrayPars(@RequestParam List<String> likes) {
//        System.out.println(likes);
//        return likes.toString();
//    }
//
//    @RequestMapping("/likesJson")
//    @ResponseBody
//    public String listParsForJson(@RequestBody List<String> likes) {
//        System.out.println(likes);
//        return likes.toString();
//    }
//
//    @RequestMapping("/dateRqt")
//    @ResponseBody
//    public String dateRqt(Date date, @DateTimeFormat(pattern = "dd-MM-yyyy") Date date1) {
//        System.out.println(date);
//        System.out.println(date1);
//        return date.toString() + date1.toString();
//    }
//
//    @RequestMapping("/toJumpPage")
//    public String toJumpPage() {
//        System.out.println("页面跳转");
//        return "page.jsp";
//    }
//
//    @RequestMapping("/toText")
//    @ResponseBody
//    public String toText() {
//        System.out.println("返回纯文本数据");
//        return "Response text";
//    }
//
//    @RequestMapping("/toJsonPOJO")
//    @ResponseBody
//    public User toJsonPOJO() {
//        System.out.println("返回json对象数据");
//        User user = new User();
//        user.setName("xiaohu");
//        user.setAge(17);
//        return user;
//    }
}
