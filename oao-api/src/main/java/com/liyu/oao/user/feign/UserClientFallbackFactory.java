package com.liyu.oao.user.feign;

import com.liyu.oao.common.constant.ResultCode;
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
    private final Integer CODE = ResultCode.R1001.code();
    private final String MSG = ResultCode.R1001.msg();
    private final String CLIENT_SERVICE_NAME = App.ID.USER;

    @Override
    public IUserClient create(Throwable cause) {
        return new IUserClient() {
            @Override
            public Result<User> findUserByUsername(String username) {
                return res(cause);
            }
        };
    }

    public Result res(Throwable cause) {
        String msg = MSG + ": " + CLIENT_SERVICE_NAME;
        logger.debug(msg, cause);
        return new Result(CODE, msg);
    }
}