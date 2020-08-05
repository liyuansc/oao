package com.oao.id.feign;

import com.oao.common.constant.ResultCode;
import com.oao.common.model.App;
import com.oao.common.model.Result;
import com.oao.user.feign.UserClientFallbackFactory;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liyu on 2020/2/20
 */
public class IdClientFallbackFactory implements FallbackFactory<IdClient> {
    private Logger logger = LoggerFactory.getLogger(UserClientFallbackFactory.class);
    private final Integer CODE = ResultCode.R1101.code();
    private final String MSG = ResultCode.R1101.msg();
    private final String CLIENT_SERVICE_NAME = App.ID.ID;

    @Override
    public IdClient create(Throwable cause) {
        return new IdClient() {
            @Override
            public Result<String> nextId() {
                String msg = MSG + ": " + CLIENT_SERVICE_NAME;
                logger.debug(msg, cause);
                return new Result<>(CODE, msg);
            }
        };
    }
}
