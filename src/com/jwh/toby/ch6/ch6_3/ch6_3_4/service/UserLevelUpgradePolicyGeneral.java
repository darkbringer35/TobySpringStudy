package com.jwh.toby.ch6.ch6_3.ch6_3_4.service;

import com.jwh.toby.ch6.ch6_3.ch6_3_4.dao.UserDao;
import com.jwh.toby.ch6.ch6_3.ch6_3_4.domain.Level;
import com.jwh.toby.ch6.ch6_3.ch6_3_4.domain.User;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class UserLevelUpgradePolicyGeneral implements UserLevelUpgradePolicy {
    private UserDao userDao;
    private MailSender mailSender;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER:
                return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }

    @Override
    public void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("useradmin@ksug.org");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());

        this.mailSender.send(mailMessage);
    }
}
