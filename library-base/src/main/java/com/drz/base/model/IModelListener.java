package com.drz.base.model;

/**
 * 应用模块: model
 * <p>
 * 类描述: 通用model
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public interface IModelListener<T> extends IBaseModelListener
{
    /**
     * 数据加载完成
     * 
     * @param model model
     * @param data 数据
     */
    void onLoadFinish(BaseModel model, T data);
    
    /**
     * 数据加载失败
     * 
     * @param model model
     * @param prompt 错误
     */
    void onLoadFail(BaseModel model, String prompt);
}
