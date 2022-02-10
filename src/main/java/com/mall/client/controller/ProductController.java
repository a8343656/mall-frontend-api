package com.mall.client.controller;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.product.AddShoppingCarDTO;
import com.mall.client.service.ProductService;
import com.mall.client.service.UtilService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired ProductService productService;
	@Autowired UtilService utilService;
	
	@GetMapping("/getProductList")
	public ActionResult getProductList (
			@RequestParam(value="page" ,defaultValue = "1") Integer page
			,@RequestParam(value="column",defaultValue = "id") String column) {
		
		Pageable pageable = utilService.pageRequest(page, 8, column, "DESC");
		return productService.getProductList(pageable);
	
	}
	
	@GetMapping("/getProductDetail")
	public ActionResult getProductDetail (@RequestParam("id") Long id) {
		
		return productService.getProductDetail(id);
		
	}
	
}
