package com.liyu.oao.uaa.client.id;

import com.liyu.oao.common.constant.ResultCode;
import com.liyu.oao.common.model.App;
import com.liyu.oao.common.model.Result;
import feign.hystrix.FallbackFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by liyu on 2020/2/20
 */
public class IdClientFallbackFactory implements FallbackFactory<IdClient> {
    private Log log = LogFactory.getLog(IdClientFallbackFactory.class);
    private final Integer CODE = ResultCode.R1001.code();
    private final String MSG = ResultCode.R1001.msg();
    private final String CLIENT_SERVICE_NAME = App.Id.ID;

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
