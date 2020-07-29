package com.shennong.smart.resource.itf.proxy;
import com.shennong.smart.resource.itf.proto.goods.*;


/**
 * GoodsService
 * 类作用：商品类的实体类。
 * @author dds-Swallow_Birds_000001
 * @date 2020/6/7
 */
public interface GoodsService {
    //新增商品
    void createGoods(CreateGoodsReq goods);
    //删除商品
    void delectGoods(DeleteGoodsReq deleteGoodsReq);
    //修改商品
    void updateGoods(CreateGoodsReq goods);
    //查询商品列表
    SelectGoodsRsp selectGoods(SelectGoodsReq selectGoodsReq);
    //新增产品
    void createProduct(CreateProductReq createProductReq);
    //修改产
    void updateProduct(CreateProductReq createProductReq);
    //删除商品
    void deleteProduct(SelectProductReq selectProductReq);
    //新增分类
    void createClassification(Classification classification);
    //修改分类
    void updateClassification(Classification classification);
    //删除分类
    void deleteClassification(DeleteClassificationReq deleteClassificationReq);
}
