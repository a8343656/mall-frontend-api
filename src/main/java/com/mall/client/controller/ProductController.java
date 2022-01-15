package com.mall.client.controller;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import com.mall.client.dto.ActionResult;
import com.mall.client.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired ModelMapper modelMapper;
	@Autowired ProductService productService;
	
	@GetMapping("/getProductList")
	public ActionResult getProductList (@RequestParam("page")@Min(1) Integer page ,@RequestParam("column")@NotEmpty String column) {
		
		Pageable pageable = PageRequest.of(page, 8, Sort.by(column).descending());
		return productService.getProductList(pageable);
		
	}
	
	@GetMapping("/getProductDetail")
	public ActionResult getProductDetail (@RequestParam("id") Long id) {
		
		return productService.getProductDetail(id);
		
	}
	
	
}
