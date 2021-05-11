package com.gdpu.homework.Entity.Config;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class Code {
    private int  width=80; //图片宽度
    private int height=35;//图片高度
    private String code="QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm123456789"; //A-Z，a-z,0-9随机选取
    private String[] fontNames={"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};
    private Color bgcolor=new Color(255,255,255);    //图片背景颜色，白色
    private Random r=new Random();
    private String text;//记录图片中的字符串，以便后期验证
/*
测试方法

    public static void main(String[] args) {
      new code().run();
    }
    private  void run(){
        code vcode=new code();
        BufferedImage bufferedImage=vcode.getlmage();
        try {
            vcode.output(bufferedImage,new FileOutputStream(new File("/vcode.jpg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(vcode.getText());
    }

 */
    public BufferedImage getlmage(){
        BufferedImage bufferedImage = createlmage();
        //画笔工具，获取绘图环境
        Graphics2D graphics2D= (Graphics2D) bufferedImage.getGraphics();
        //stringBuilder保存图片内容结果
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<4;i++)
        {
            String s=randomChar()+"";//间接将char类型转换为string型
            stringBuilder.append(s); //结果储存在stringbuilder里面
            int x=i*width/4;
            graphics2D.setFont(randomFont());
            graphics2D.setColor(randomColor());
            graphics2D.drawString(s,x,height-5);

        }
        this.text=stringBuilder.toString();
        drawLine(bufferedImage);//画干扰线
        return  bufferedImage;
    }

    private void drawLine(BufferedImage bufferedImage) {    //画干扰线
        Graphics2D graphics2D= (Graphics2D) bufferedImage.getGraphics();
        for(int i=0;i<4;i++)                   //加四条干扰线，且干扰线均在字符串附近
        {
            int x1=r.nextInt(width/4)+i*width/4;
            int y1=r.nextInt(height);
            int x2=r.nextInt(width/4)+i*width/4;
            int y2=r.nextInt(height);
            graphics2D.setColor(Color.blue);
            graphics2D.drawLine(x1,y1,x2,y2);
        }

    }

    private Color randomColor() {       //产生随机颜色
        int red=r.nextInt(255);
        int green=r.nextInt(255);
        int blue=r.nextInt(255);
        Color color=new Color(red,green,blue);
        return color;
    }

    private Font randomFont() {             //产生随机字体
        int index=r.nextInt(fontNames.length);
        String fontName=fontNames[index];
        int style=r.nextInt(4);//字体形态，一共有四个
        int size=r.nextInt(5)+24;
        Font font=new Font(fontName,style,size);
        return  font;

    }

    private BufferedImage createlmage() {
        BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D= (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setColor(this.bgcolor);   //填充背景颜色
        graphics2D.fillRect(0,0,width,height);  //填充大小即图片大小
        return bufferedImage;
    }


    private char randomChar() {
        int index= r.nextInt(this.code.length());
        return  code.charAt(index);
    }
    public String  getText(){       //返回text以便验证
        return this.text;
    }
    public static void output(BufferedImage bufferedImage, HttpServletResponse response){ //
        try{
            ImageIO.write(bufferedImage, "jpeg", response.getOutputStream());
        }catch (IOException e){

        }

    }
}