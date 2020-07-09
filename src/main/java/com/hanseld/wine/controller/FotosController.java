package com.hanseld.wine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hanseld.wine.dto.FotoDTO;
import com.hanseld.wine.service.VinhoService;
import com.hanseld.wine.storage.FotoReader;

@RestController
@RequestMapping("/fotos")
public class FotosController {
	
	@Autowired
	private VinhoService vinhoService;
	
	@Autowired(required = false)
	private FotoReader fotoReader;
	
	@PostMapping(value = "/{idVinho}",consumes = "multipart/form-data")
	public FotoDTO upload(@PathVariable("idVinho") Long idVinho, 
			@RequestParam("files[]") MultipartFile[] files) {
		
		MultipartFile foto = files[0];
		
		String url = vinhoService.salvarFoto(idVinho, foto);
		
		return new FotoDTO(url);
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperar(@PathVariable String nome) {
		return fotoReader.recuperar(nome);
	}
}
