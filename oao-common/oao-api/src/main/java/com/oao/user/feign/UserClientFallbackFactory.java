package com.oao.user.feign;

import com.oao.common.constant.App;
import com.oao.common.constant.ResultCode;
import com.oao.common.exception.InternalApiException;
import com.oao.common.model.Result;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.model.po.OaoApi;
import com.oao.user.model.po.OaoUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by liyu on 2020/2/20
 */
@Slf4j
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {
    private final String CLIENT_SERVICE_NAME = App.ID.USER;
    private final Integer CODE = ResultCode.R1101.code();
    private final String MSG = ResultCode.R1101.msg();

    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public Result<OaoUser> findByUsername(String username) {
                throw new InternalApiException(CLIENT_SERVICE_NAME, MSG, cause);
            }

            @Override
            public Result<OaoLoginUser> findLoginUserByUsername(String username) {
                throw new InternalApiException(CLIENT_SERVICE_NAME, MSG, cause);
            }

            @Override
            public Result<List<OaoApi>> findAllApi() {
                throw new InternalApiException(CLIENT_SERVICE_NAME, MSG, cause);
            }
        };
    }
}
