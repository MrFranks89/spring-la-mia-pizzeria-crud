package it.lessons.pizzeria.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.lessons.pizzeria.model.Pizza;
import it.lessons.pizzeria.repository.PizzaRepository;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

	@Autowired
	private PizzaRepository pizzaRepo;

	@GetMapping
	public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		
		List<Pizza> allPizze = pizzaRepo.findAll();
		
		if(keyword !=null && !keyword.isBlank()) {
			allPizze = pizzaRepo.findByNomeContaining(keyword);
			model.addAttribute("keywor", keyword);
		} else {
			allPizze = pizzaRepo.findAll();
		}
		

		model.addAttribute("pizze", allPizze);

		return "pizze/index";
	}

	@GetMapping("/show/{id}")
	public String show(@PathVariable(name = "id") Long id, Model model) {

		Optional<Pizza> pizzaOptional = pizzaRepo.findById(id); // contenitore

		if (pizzaOptional.isPresent()) {
			model.addAttribute("pizza", pizzaOptional.get()); // tira fuori il contenuto dal contenitore
		}

		return "/pizze/show";
	}
}
