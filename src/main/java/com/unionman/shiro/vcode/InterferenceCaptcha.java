package com.unionman.shiro.vcode;

import com.alibaba.fastjson.util.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @description:  jpg 干扰格式验证码
 * @author Rong.Jia
 * @date 2019/09/28 12:04
 */
public class InterferenceCaptcha extends Captcha {

    public InterferenceCaptcha() {
    }

    public InterferenceCaptcha(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public InterferenceCaptcha(int width, int height, int len){
        this(width,height);
        this.len = len;
    }

    public InterferenceCaptcha(int width, int height, int len, Font font){
        this(width,height,len);
        this.font = font;
    }

    @Override
    public void out(OutputStream os) {

        graphicsImage(alphas(), os);
    }

    /**
     * 画随机码图
     * @param strs 文本
     * @param out 输出流
     */
    private boolean graphicsImage(char[] strs, OutputStream out){

        boolean ok = false;
        try {

            // 根据宽高创建 BufferedImage图片对象
            BufferedImage bi = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);

            // 获取图片对象的画笔对象Graphic
            Graphics2D g = bi.createGraphics();

            // 画笔画入数据（背景色，边框，字体，字体位置，颜色等）

            // 背景色
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            //边框颜色
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, width-1, height-1);

            // 字体
            //Font font = new Font("Fixedsys", Font.PLAIN, height - 2);
            //Font font = new Font("微软雅黑", Font.ROMAN_BASELINE, height - 2);
            g.setFont(font);

            int len = strs.length;

            // 添加干扰线：坐标/颜色随机
            Random random = new Random();
            for (int i = 0; i < (len * 2); i++) {

                g.setColor(getRandomColor());

                g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
            }

            // 添加噪点:
            for(int i = 0;i < (len * 3);i++){
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                g.setColor(getRandomColor());
                g.fillRect(x, y, 2,2);
            }

            int h  = height - ((height - font.getSize()) >>1),
                    w = width/len,
                    size = w-font.getSize()+1;

            AlphaComposite ac3;

            //画字符串
            for(int i = 0; i < len; i++) {

                // 指定透明度
                ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
                g.setComposite(ac3);

                // 对每个字符都用随机颜色
                g.setColor(getRandomColor());

                //设置字体旋转角度

                //角度小于30度
                int degree = random.nextInt() % 30;

                //正向旋转
                //g.rotate(degree * Math.PI / 180, w, 45);
                g.drawString(strs[i]+"",(width-(len-i)*w)+size, h - 3);
                //反向旋转
                //g.rotate(-degree * Math.PI / 180, w, 45);

                ac3 = null;
            }

            ImageIO.write(bi, "png", out);
            out.flush();
            ok = true;
        }catch (IOException e){
            ok = false;
        }finally {
            IOUtils.close(out);
        }
        return ok;
    }

    /**
     * 随机取色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        return new Color(ran.nextInt(256),ran.nextInt(256), ran.nextInt(256));
    }

}



