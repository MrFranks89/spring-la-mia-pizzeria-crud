package it.lessons.pizzeria.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.lessons.pizzeria.model.Pizza;
import it.lessons.pizzeria.repository.PizzaRepository;
import jakarta.validation.Valid;

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
			model.addAttribute("keyword", keyword);
		} else {
			allPizze = pizzaRepo.findAll();
		}
		
		if(keyword == null || keyword.isBlank() || keyword.equals("null")) {
			model.addAttribute("pizzaUrl","/pizze");
		} else {
			model.addAttribute("pizzaUrl","/pizze?keyword=" + keyword);
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
	
	 @GetMapping("/create")
	 public String create(Model model) {
		 model.addAttribute("pizza", new Pizza());
		 
		 return "pizze/create";
	 }
	 
	 @PostMapping("/create")
	 public String store(@Valid @ModelAttribute("pizza") Pizza formPizza,
			 BindingResult bindingResult,
			 Model model, RedirectAttributes redirectAttributes) {
		 
		 if(bindingResult.hasErrors())  {
			 return "pizze/create";
			 
		 }
		 
		 pizzaRepo.save(formPizza);
		 
		 redirectAttributes.addFlashAttribute("successMessage", "Pizza Creata!");
		 
		 return "redirect:/pizze";
	 }
}
