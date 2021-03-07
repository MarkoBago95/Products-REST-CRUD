package com.example.demo;

import java.net.BindException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class ProductController {
	@Autowired
	private ProductService service;
	
	@RequestMapping("")
	public String viweHomePage() {
		return "index";
	}

	
	@RequestMapping("/list")
	public String viewHomePage(Model model) {
		List<Product> listProduct = service.listAll();
		model.addAttribute("listProducts",listProduct);
		return "listOfAll";
	}
	
	@RequestMapping("/new")
	public String createNew(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "new_product";
	}
	@ExceptionHandler({SQLIntegrityConstraintViolationException.class,BindException.class,NumberFormatException.class})
    public String handleException() {
		return "error";
    }
	
	@RequestMapping(value="/save" ,method=RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		service.save(product);
		return "redirect:/list";
		
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView createNew(@PathVariable(name="id") int id) {
		ModelAndView mav = new ModelAndView("edit_product");
		Product product = service.get(id);
		mav.addObject("product", product);
		return mav;
	}
	
	@RequestMapping("delete/{id}")
	public String deleteProduct(@PathVariable(name="id") int id) {
		service.delete(id);
		return "redirect:/list";
	}
	
	
}
