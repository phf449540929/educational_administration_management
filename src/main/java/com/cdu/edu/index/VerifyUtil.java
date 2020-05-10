package com.cdu.edu.index;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * description: 生成验证码的工具类
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/10/10 0010 下午 15:19
 * @since jdk 10.0.1
 */
@Component
public class VerifyUtil {

    /**
     * 验证码字符集
     */
    private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
            'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    /**
     * 字符数量
     */
    private static final int SIZE = 4;
    /**
     * 干扰线数量
     */
    private static final int LINES = 5;
    /**
     * 宽度
     */
    private static final int WIDTH = 80;
    /**
     * 高度
     */
    private static final int HEIGHT = 40;
    /**
     * 字体大小
     */
    private static final int FONT_SIZE = 30;

    /**
     * description: 生成随机验证码及图片
     *
     * @return Object[0]：验证码字符串；Object[1]：验证码图片。
     */
    public Object[] createImage() {
        StringBuilder builder = new StringBuilder();
        // 1.创建空白图片
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 2.获取图片画笔
        Graphics graphic = image.getGraphics();
        // 3.设置画笔颜色
        graphic.setColor(Color.LIGHT_GRAY);
        // 4.绘制矩形背景
        graphic.fillRect(0, 0, WIDTH, HEIGHT);
        // 5.画随机字符
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            // 取随机字符索引
            int n = random.nextInt(CHARS.length);
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 设置字体大小
            graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
            // 画字符
            graphic.drawString(CHARS[n] + "", i * WIDTH / SIZE, HEIGHT * 2 / 3);
            // 记录字符
            builder.append(CHARS[n]);
        }
        // 6.画干扰线
        for (int i = 0; i < LINES; i++) {
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 随机画线
            graphic.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT), random.nextInt(WIDTH)
                    , random.nextInt(HEIGHT));
        }
        // 7.返回验证码和图片
        Object[] objects = new Object[2];
        objects[0] = builder.toString();
        objects[1] = image;
        return objects;
    }

    /**
     * description: 随机取色
     *
     * @return java.awt.Color 随机生成的颜色
     */
    private Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
