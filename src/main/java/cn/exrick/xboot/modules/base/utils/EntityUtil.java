package cn.exrick.xboot.modules.base.utils;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.modules.base.entity.User;
import cn.hutool.core.date.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityUtil {
    @Autowired
    SecurityUtil securityUtil;

    public void initEntity(XbootBaseEntity xbootBaseEntity) {
        User user = securityUtil.getCurrUser();
        xbootBaseEntity.setCreateBy(user.getCreateBy());
        xbootBaseEntity.setCreateTime(DateTime.now());
        xbootBaseEntity.setDelFlag(0);
        xbootBaseEntity.setUpdateBy(user.getUpdateBy());
        xbootBaseEntity.setUpdateTime(user.getUpdateTime());
    }
}
