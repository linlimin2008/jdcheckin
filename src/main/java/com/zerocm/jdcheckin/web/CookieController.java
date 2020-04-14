package com.zerocm.jdcheckin.web;

import com.zerocm.jdcheckin.mapper.JdCookieMapper;
import com.zerocm.jdcheckin.pojo.domain.JdCookie;
import com.zerocm.jdcheckin.service.JdCookieService;
import com.zerocm.jdcheckin.service.StartCheckIn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "cookie")
@Slf4j
public class CookieController {

    @Autowired
    private JdCookieMapper jdCookieMapper;
    @Autowired
    private JdCookieService jdCookieService;
    @Autowired
    private StartCheckIn startCheckIn;

    @GetMapping(value = "userInfo")
    public ModelAndView getCookiesInfo() {
        ModelAndView mv = new ModelAndView("userInfo");
        List<JdCookie> cookieList = jdCookieMapper.selectAllCookie();
        mv.addObject("cookieList", cookieList);
//        mv.setViewName("listUserInfo2");
        log.info("获取所有用户cookie列表");
        return mv;
    }

    @RequestMapping(value = "/test")
    public String test() {
        return "demo/page";
    }

    @RequestMapping(value = "deleteById", method = RequestMethod.GET)
    public String deleteById(Integer id) {
        jdCookieMapper.deleteByPrimaryKey(id);
        return "deleted";
    }

    /**
     * 查询list集合
     */
    @GetMapping("/user")
    public String list(ModelMap map) {
        List<JdCookie> list = jdCookieService.getAllCoookieUser();
        try {
            // 将邮箱名部分隐去
            list.stream().forEach(jdCookie -> jdCookie.setRemark(jdCookie.getRemark().replaceAll(jdCookie.getRemark().substring(4, jdCookie.getRemark().lastIndexOf("@")), "*****")));
        } catch (Exception e) {
            list.stream().filter(jdCookie -> jdCookie.getRemark()
                    .matches("^((?!@).)*$"))
                    .forEach(jdCookie -> {
                        log.info("姓名为【" + jdCookie.getJdusername() + "】的用户，邮箱格式存在问题！！！" + " --- 邮箱📮「" + jdCookie.getRemark() + "」");
                    });
        }
        map.put("list", list);
        return "demo/list";
    }

    /**
     * 跳转到增加页面
     */
    @GetMapping("addCookie")
    public String addCookie() {
        return "demo/add";
    }

    /**
     * 增加
     *
     * @param
     * @return
     */
    @PostMapping("/addUser")
    public String save(JdCookie jdCookie) {
        List<JdCookie> cookieList = new ArrayList<>();
        if ( jdCookie != null ) {
            jdCookieMapper.insertSelective(jdCookie);
            cookieList.add(jdCookie);
            log.info(jdCookie.getJdusername() + " ======》》》新增记录后第一次执行");
            startCheckIn.startCheckIn(cookieList);
        }
        return "redirect:/cookie/user";
    }

    /**
     * 跳转到修改页面
     */
    @RequestMapping("/updatePage/{id}")
    public String updatePage(@PathVariable Integer id, ModelMap map) {
        JdCookie jdCookie = jdCookieMapper.selectByPrimaryKey((id));
        map.put("jdCookie", jdCookie);
        return "demo/update";
    }

    /**
     * 修改数据
     */
    @PostMapping("/updateCookie")
    public String update(JdCookie jdCookie) {
        List<JdCookie> cookieList = new ArrayList<>();
        if ( jdCookie != null ) {
            if (jdCookieMapper.selectByPrimaryKey(jdCookie.getId()).getJdcookie().equals(jdCookie.getJdcookie())
                    && jdCookieMapper.selectByPrimaryKey(jdCookie.getId()).getRemark().equals(jdCookie.getRemark()) ){
                jdCookieMapper.updateByPrimaryKey(jdCookie);
                return "redirect:/cookie/user";
            }
            jdCookieMapper.updateByPrimaryKey(jdCookie);
            cookieList.add(jdCookie);
            log.info(jdCookie.getJdusername() + " 修改信息后第一次执行《《《======");
            startCheckIn.startCheckIn(cookieList);
        }
        return "redirect:/cookie/user";
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteCookie/{id}")
    public String del(@PathVariable Integer id) {
        jdCookieMapper.deleteByPrimaryKey(id);
        return "redirect:/cookie/user";
    }
}
