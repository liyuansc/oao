package com.liyu.oao.id;

import com.liyu.oao.common.model.Result;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by liyu on 2020/2/20
 */
//@FeignClient(name = App.ID.ID, fallbackFactory = IdClientFallbackFactory.class)
public interface IdClient {

    @GetMapping("/api/id/next")
    Result<String> nextId();
}
