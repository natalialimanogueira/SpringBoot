package br.com.alura.forum.controller;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

// obs adicionar no arquivo pom.xml a dependencia dvtool, pois ela faz xom que as alterações
//feitas no projeto são entendidas e alteradas no servidor sem ter que parar o que está rodando 

@RestController// é referente os retornos dos métodos para nao
// ter que ficar colocando ResponseBody em todos os metodos
@RequestMapping("/topicos") // essa anotação diz que sempre que chamarmos na URL topicos será o Controller que ira´
//gerenciar
public class TopicosContoller {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public   List<TopicoDto> lista(String nomeCurso){//Dto para epecificar quais atributos serao passados
	//(flexibilidade em escolher o que irá passar)
		if(nomeCurso == null) {
			List<Topico>topicos = topicoRepository.findAll();
			return TopicoDto.converter(topicos);
		}else {
			List<Topico>topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);
			
			}
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Validated TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));	
	}
	
	@GetMapping("/{id}")
	public DetalhesDoTopicoDto detahar(@PathVariable Long id) {
		Topico topico = topicoRepository.getReferenceById(id);
	        return new DetalhesDoTopicoDto(topico);
	        }
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Validated AtualizacaoTopicoForm form) {
	    Topico topico = form.atualizar(id, topicoRepository);
	        return ResponseEntity.ok(new TopicoDto(topico));        
	}

}
