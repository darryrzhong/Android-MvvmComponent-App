package com.drz.base.model;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public interface IPagingModelListener<T> extends IBaseModelListener
{
    /**
     * 数据加载完成
     * 
     * @param model model
     * @param data 数据
     * @param isEmpty 数据是否为空
     * @param isFirstPage 是否是第一页
     * @param hasNextPage 是否还有下一页
     */
    void onLoadFinish(BasePagingModel model, T data, boolean isEmpty, boolean isFirstPage);
    
    /**
     * 数据加载失败
     * 
     * @param model model
     * @param prompt 错误
     * @param isFirstPage 是否是第一页
     */
    void onLoadFail(BasePagingModel model, String prompt, boolean isFirstPage);
}
