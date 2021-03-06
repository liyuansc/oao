package com.oao.id.feign;

import com.oao.common.constant.ResultCode;
import com.oao.common.constant.App;
import com.oao.common.model.Result;
import com.oao.user.feign.UserClientFallbackFactory;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liyu on 2020/2/20
 */
@Slf4j
public class IdClientFallbackFactory implements FallbackFactory<IdClient> {
    private final Integer CODE = ResultCode.R1101.code();
    private final String MSG = ResultCode.R1101.msg();
    private final String CLIENT_SERVICE_NAME = App.ID.ID;

    @Override
    public IdClient create(Throwable cause) {
        return new IdClient() {
            @Override
            public Result<String> nextId() {
                String msg = MSG + ": " + CLIENT_SERVICE_NAME;
                log.debug(msg, cause);
                return new Result<>(CODE, msg);
            }
        };
    }
}
