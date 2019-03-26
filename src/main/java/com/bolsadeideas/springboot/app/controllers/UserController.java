package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.app.models.entity.User;
import com.bolsadeideas.springboot.app.models.service.IUserService;

@Controller
@SessionAttributes("user")
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/listarUser", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("users", userService.findAll());
		return "listarUser";
	}

	@RequestMapping(value = "/Userform")
	public String crear(Map<String, Object> model) {

		User user = new User();
		model.put("user", user);
		model.put("titulo", "Formulario de usuarios");
		return "Userform";
	}
	
	@RequestMapping(value="/Userform/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		
		User user = null;
		
		if(id > 0) {
			user = userService.findOne(id);
		} else {
			return "redirect:/listar";
		}
		model.put("user", user);
		model.put("titulo", "Editar Usuario");
		return "Userform";
	}

	@RequestMapping(value = "/Userform", method = RequestMethod.POST)
	public String guardar(@Valid User user, BindingResult result, Model model, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Usuario");
			return "Userform";
		}

		userService.save(user);
		status.setComplete();
		return "redirect:listarUser";
	}
	
	@RequestMapping(value="/eliminarUser/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		
		if(id > 0) {
			userService.delete(id);
		}
		return "redirect:/listarUser";
	}
}
