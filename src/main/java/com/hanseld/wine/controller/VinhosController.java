package com.hanseld.wine.controller;

import com.hanseld.wine.service.VinhoService;
import com.hanseld.wine.storage.FotoStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.xml.ws.http.HTTPException;

import com.hanseld.wine.model.TipoVinho;
import com.hanseld.wine.model.Vinho;

@Controller
@RequestMapping("/vinhos")
public class VinhosController {

	@Autowired
	private VinhoService vinhoService;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@GetMapping
	public ModelAndView pesquisa() {
		ModelAndView mv = new ModelAndView("/vinho/ListagemVinhos");
		mv.addObject("vinhos", vinhoService.listarTodos());
		
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Vinho vinho) {
		ModelAndView mv = new ModelAndView("/vinho/CadastroVinho");
		mv.addObject("tipos", TipoVinho.values());
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Vinho vinho, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(vinho);
		}
		vinhoService.salvar(vinho);
		attributes.addFlashAttribute("mensagem", "Vinho salvo com sucesso!");
		return new ModelAndView("redirect:/vinhos/novo");
	} 
	
	@GetMapping("/{id}")
	public ModelAndView visualizar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("/vinho/VisualizacaoVinho");
		Vinho vinho = vinhoService
						.procurarUmPeloId(id)
						.orElseThrow(() -> new HTTPException(404));
		
		if (vinho.temFoto()) {
			vinho.setUrl(fotoStorage.getUrl(vinho.getFoto()));
		}
		
		mv.addObject("vinho", vinho);
		
		return mv;
	}
}
