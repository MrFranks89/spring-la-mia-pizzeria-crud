package it.lessons.pizzeria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.pizzeria.model.Pizza;
import it.lessons.pizzeria.repository.PizzaRepository;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

	@Autowired
	private PizzaRepository pizzaRepo;
	
	@GetMapping
	public String index(Model model) {
		List<Pizza> allPizze = pizzaRepo.findAll();
		
		model.addAttribute("pizze", allPizze);
		
		return "pizze/index";
	}
}
