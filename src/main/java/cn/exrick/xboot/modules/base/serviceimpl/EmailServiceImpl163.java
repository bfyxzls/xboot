package cn.exrick.xboot.modules.base.serviceimpl;

import cn.exrick.xboot.common.utils.SnowFlakeUtil;
import cn.exrick.xboot.modules.base.entity.User;
import cn.exrick.xboot.modules.base.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl163 implements cn.exrick.xboot.modules.base.service.EmailService {

    private static String sender = "bjjbdpg@163.com";
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(receiver);
        message.setSubject("大老杨");
        message.setText("你好你好你好！");
        try {
            javaMailSender.send(message);
            log.info("简单邮件已经发送。");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
    }

    /**
     * 发给找回密码邮件.
     *
     * @param receiver
     */
    @Override
    public String sendFindPassworldMail(String receiver) {
        User user = userService.findByUsername(receiver);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(user.getEmail());
        message.setSubject("找回密码");
        String code = String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId());
        redisTemplate.opsForValue().set("vode:" + receiver, code);
        message.setText("你找回密码需要的验证码是：" + code);
        try {
            javaMailSender.send(message);
            log.info("简单邮件已经发送。");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
        return user.getEmail();
    }

    /**
     * 验证
     *
     * @param code
     * @return
     */
    @Override
    public Boolean verifyCode(String username, String code) {
        return redisTemplate.opsForValue().get("vode:" + username).equals(code);
    }
}
