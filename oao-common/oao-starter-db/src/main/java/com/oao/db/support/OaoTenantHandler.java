package com.oao.db.support;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;

public class OaoTenantHandler implements TenantHandler {
    public final static String TENANT_ID = "tenant_id";

    @Override
    public Expression getTenantId(boolean select) {
        // select since: 3.3.2，参数 true 表示为 select 下的 where 条件,false 表示 insert/update/delete 下的条件
        // 只有 select 下才允许多参(ValueListExpression),否则只支持单参
//        if (!select) {
//            return new LongValue(1);
//        }
//        ValueListExpression expression = new ValueListExpression();
//        ExpressionList list = new ExpressionList(new LongValue(1), new LongValue(2));
//        expression.setExpressionList(list);
//        return expression;
        return new NullValue();
    }

    @Override
    public String getTenantIdColumn() {
        return TENANT_ID;
    }

    @Override
    public boolean doTableFilter(String tableName) {
        //true代表不走租户
        return true;
    }
}
