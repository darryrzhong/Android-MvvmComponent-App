package com.drz.common.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.drz.base.utils.GsonUtils;

import java.lang.reflect.Type;

/**
 * 应用模块:
 * <p>
 * 类描述: 如果需要传递自定义对象，新建一个类（并非自定义对象类），然后实现
 * SerializationService,并使用@Route注解标注(方便用户自行选择序列化方式)
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-27
 */
@Route(path = "/video/json")
public class JsonServiceImpl implements SerializationService
{

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return GsonUtils.fromLocalJson(input,clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return GsonUtils.toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return GsonUtils.fromLocalJson(input,clazz);
    }

    @Override
    public void init(Context context) {

    }
}
