package com.situ.ws.service.impl;

import com.situ.ws.bean.common.Email;
import com.situ.ws.bean.entries.Users;
import com.situ.ws.service.MsgService;
import com.situ.ws.util.RegexUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MsgServiceImpl implements MsgService {

    private final JavaMailSender javaMailSender;
    private final Email email;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void sendMsg(Users users) {

        String msg = RegexUtils.msgCode();

        redisTemplate.opsForValue().set("lease:msg:phone:" + users.getPhone(),msg, 2, TimeUnit.MINUTES);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(this.email.getFrom() + "(" + this.email.getName() + ")");

//            helper.setTo(users.getEmail());
//            helper.setTo("2542356211@qq.com");
            helper.setTo("2138549461@qq.com");

            helper.setSubject(this.email.getSubject());

            String content = this.email.getContextPrefix() + msg + this.email.getContextSuffix();
            helper.setText(content,true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("邮箱地址不存在！");
        }

        log.info("发送成功!");
    }
}
