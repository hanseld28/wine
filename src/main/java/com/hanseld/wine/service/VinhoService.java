package com.hanseld.wine.service;

import java.util.List;
import java.util.Optional;

import javax.xml.ws.http.HTTPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanseld.wine.model.Vinho;
import com.hanseld.wine.repository.VinhoRepository;
import com.hanseld.wine.storage.FotoStorage;

@Service
public class VinhoService {
	
	@Autowired
	private VinhoRepository vinhos;
	
	@Autowired 
	private FotoStorage fotoStorage;
	
	public void salvar(Vinho vinho) { 
		this.vinhos.save(vinho);
	}

	public List<Vinho> listarTodos() {
		return this.vinhos.findAll();
	}

	public Optional<Vinho> procurarUmPeloId(Long id) {
		return vinhos.findById(id);
	}
	
	public String salvarFoto(Long idVinho, MultipartFile foto) {
		Vinho vinho = vinhos.findById(idVinho)
					.orElseThrow(() -> new HTTPException(404));
		
		String nomeFoto = fotoStorage.salvar(foto);
		vinho.setFoto(nomeFoto);
		vinhos.save(vinho);
		
		return fotoStorage.getUrl(nomeFoto);
	}
}
