package ucl.ee.sec.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ucl.ee.sec.entity.Product;
import ucl.ee.sec.mapper.ProductMapper;

@RestController
@Slf4j
public class AdminModifyController {

    @Autowired
    private ProductMapper productMapper;

    //using Restful style url to transfer parameter
    @GetMapping("admin_modify/{productid}/{changenum}")
    public JSONObject modifyProduct(@PathVariable("productid") int productId, @PathVariable("changenum") int changeNum) {
        Product product = productMapper.getProductById(productId);
        int productNum = product.getProductnum() + changeNum;
        int result = productMapper.updateProductNumById(productId, productNum);
        JSONObject object = new JSONObject();
        object.put("status", result);
        return object;
    }


}
