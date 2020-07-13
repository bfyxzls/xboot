package cn.exrick.xboot.modules.base.service;

public interface EmailService {
    void sendMail(String receiver);

    String sendFindPassworldMail(String receiver);

    Boolean verifyCode(String username, String code);
}
