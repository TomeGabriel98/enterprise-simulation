package br.gov.sp.fatec.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {

	@CrossOrigin
	@RequestMapping(value = "/")
	public String hello() {
		return "Olá";
	}

}
