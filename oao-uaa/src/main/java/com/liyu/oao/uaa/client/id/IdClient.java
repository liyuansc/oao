package com.liyu.oao.uaa.client.id;

import com.liyu.oao.common.model.App;
import com.liyu.oao.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by liyu on 2020/2/20
 */
@FeignClient(name = App.Id.ID, fallbackFactory = IdClientFallbackFactory.class)
public interface IdClient {

    @GetMapping("/api/id/next")
    Result<String> nextId();

    default String nextIdOrThrow() {
        return this.nextId().check().getData();
    }
}
