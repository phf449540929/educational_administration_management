package com.cdu.edu.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * description: 登录控制器
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:24
 * @since jdk 10.0.1
 */
@Controller
public class IndexController {

    @Autowired
    VerifyUtil verifyUtil;

    /**
     * description: 转向index页面
     *
     * @param model Model组件
     * @return java.lang.String 模版路径
     */
    @RequestMapping("")
    public String index(Model model) {
        LoginForm loginForm = new LoginForm();
        loginForm.setIdentity(Identity.STUDENT);
        model.addAttribute("loginForm", loginForm);
        return "index";
    }

    /**
     * description: 登录动作
     *
     * @param loginForm          登录表单的JavaBean
     * @param redirectAttributes 重定向组件
     * @param session            Session组件
     * @return java.lang.String  模版路径
     */
    @RequestMapping("login.do")
    public String login(LoginForm loginForm, HttpSession session,
                        RedirectAttributes redirectAttributes) {
        String verification = loginForm.getVerification();
        if ("".equals(verification)) {
            loginForm.setMessage("验证码不能为空");
            return "index";
        }
        boolean isVerify = verification.equals(session.getAttribute("verification"));
        if (!isVerify) {
            loginForm.setMessage("验证码错误");
            return "index";
        }

        redirectAttributes.addAttribute("username", loginForm.getUsername());
        redirectAttributes.addAttribute("password", loginForm.getPassword());

        switch (loginForm.getIdentity()) {
            case DEPARTMENT:
                return "redirect:department/login.do";
            case TEACHER:
                return "redirect:teacher/login.do";
            case STUDENT:
                return "redirect:student/login.do";
            case VISITOR:
                return "redirect:";
            default:
                return "error";
        }
    }

    /**
     * description: 获取验证码
     *
     * @param response Response组件
     * @param session  Session组件
     */
    @RequestMapping(value = "verifyUtil.do")
    public void getVerifyUtil(HttpServletResponse response, HttpSession session) {
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objects = verifyUtil.createImage();
        //将验证码存入Session
        session.setAttribute("verification", objects[0]);
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objects[1];
        response.setContentType("image/png");
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException ioe) {
            System.out.println("something wrong");
        }
    }

}
