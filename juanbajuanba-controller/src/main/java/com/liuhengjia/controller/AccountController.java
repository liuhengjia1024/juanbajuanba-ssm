package com.liuhengjia.controller;

import com.liuhengjia.util.CheckCodeUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequestMapping("/account")
@RestController
@SessionAttributes(names = "CHECK_CODE_SERVER")
public class AccountController {
    @PostMapping("/checkCode")
    public ResponseEntity<byte[]> getCheckCodeImage(Model model, HttpServletResponse response) throws IOException {
        // 设置响应头，告知客户端返回的是图片
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        // 禁止缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");

        // 生成验证码图片
        int width = 160;
        int height = 60;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(new Color(245, 247, 249));
        g.fillRect(0, 0, width, height);
        String checkCode = CheckCodeUtil.getCheckCode();
        model.addAttribute("CHECK_CODE_SERVER", checkCode);
        g.setColor(new Color(13, 110, 253));
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        g.drawString(checkCode, 45, 40);

        // 将生成的验证码图片写入响应体
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", bos);

        // 返回生成的验证码图片
        return new ResponseEntity<>(bos.toByteArray(), headers, HttpStatus.OK);
    }
}
