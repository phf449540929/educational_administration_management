package com.cdu.edu.index;

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
 * description: the controller to the index
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:24
 * @since jdk
 */
@Controller
public class IndexController {

    /**
     * description: find the index.html
     *
     * @param model the model of SpringMVC
     * @return java.lang.String
     */
    @RequestMapping("")
    public String index(Model model) {
        LoginForm loginForm = new LoginForm();
        loginForm.setIdentity(Identity.STUDENT);
        model.addAttribute("loginForm", loginForm);
        return "index";
    }

    /**
     * description: to check the verification code and direct the login to other controller
     *
     * @param loginForm          the loginForm from the form
     * @param redirectAttributes the redirectAttributes of SpringMVC
     * @param session            the session to get the verification
     * @return java.lang.String
     */
    @RequestMapping("login.do")
    public String login(LoginForm loginForm, HttpSession session, RedirectAttributes redirectAttributes) {

        String verification = loginForm.getVerification();
        if ("".equals(verification)) {
            loginForm.setMessage("验证码不能为空");
            return "index";
        }
        boolean isVerify = verification.equals(session.getAttribute("verification"));
        if(!isVerify){
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
                return "redirect:index";
            default:
                return "redirect:index";
        }
    }

    /**
     * description: get the verify util
     *
     * @param response get the servlet's response to write the image
     * @param session  get the servlet's session to add into the verification
     */
    @RequestMapping(value = "verifyUtil.do")
    public void getVerifyUtil(HttpServletResponse response, HttpSession session) {
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objects = VerifyUtil.createImage();
        //将验证码存入Session
        session.setAttribute("verification", objects[0]);
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objects[1];
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException ioe) {
            System.out.println("something wrong");
        }
    }

}
