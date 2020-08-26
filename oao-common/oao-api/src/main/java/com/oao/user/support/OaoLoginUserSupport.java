package com.oao.user.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.oao.common.constant.OaoSecurityConstant;
import com.oao.common.util.UrlCoder;
import com.oao.user.feign.UserClient;
import com.oao.user.model.OaoLoginUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Data
@NoArgsConstructor
public class OaoLoginUserSupport {
    @Autowired(required = false)
    private UserClient userClient;

    public OaoLoginUserSupport(UserClient userClient) {
        this.userClient = userClient;
    }

    public OaoLoginUser getUser(HttpServletRequest request, boolean isFull) {
        OaoLoginUser loginUser;
        String iUser = request.getHeader(OaoSecurityConstant.HttpHeader.I_USER);
        if (StringUtils.isEmpty(iUser)) {
            loginUser = new OaoLoginUser();
            loginUser.setLogin(false);
        } else {
            loginUser = JSON.parseObject(UrlCoder.decode(iUser), new TypeReference<OaoLoginUser>() {
            });
            String userId = loginUser.getId();
            if (isFull && userClient != null) {
                OaoLoginUser full = userClient.findLoginUser(userId).check().getData();
                BeanUtils.copyProperties(full, loginUser);
            }
            loginUser.setLogin(true);
            loginUser.setPassword(null);
        }
        return loginUser;
    }
}
