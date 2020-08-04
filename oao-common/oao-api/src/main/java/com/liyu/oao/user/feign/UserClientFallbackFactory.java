package com.liyu.oao.user.feign;

import com.liyu.oao.api.model.LoginUser;
import com.liyu.oao.common.constant.ResultCode;
import com.liyu.oao.common.exception.InternalApiException;
import com.liyu.oao.common.model.App;
import com.liyu.oao.common.model.Result;
import com.liyu.oao.user.model.po.User;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liyu on 2020/2/20
 */
public class UserClientFallbackFactory implements FallbackFactory<IUserClient> {
    private Logger logger = LoggerFactory.getLogger(UserClientFallbackFactory.class);
    private final String CLIENT_SERVICE_NAME = App.ID.USER;
    private final Integer CODE = ResultCode.R1101.code();
    private final String MSG = ResultCode.R1101.msg();

    @Override
    public IUserClient create(Throwable cause) {
        return new IUserClient() {
            @Override
            public Result<User> findByUsername(String username) {
                throw new InternalApiException(CLIENT_SERVICE_NAME, MSG, cause);
            }

            @Override
            public Result<LoginUser> findLoginUserByUsername(String username) {
                throw new InternalApiException(CLIENT_SERVICE_NAME, MSG, cause);
            }
        };
    }
}
