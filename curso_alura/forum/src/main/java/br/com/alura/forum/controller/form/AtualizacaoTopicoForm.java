package br.com.alura.forum.controller.form;

import org.springframework.lang.NonNull;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

public class AtualizacaoTopicoForm {
	 	@NonNull 
	    private String titulo;
	    @NonNull 
	    private String mensagem;
		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		public String getMensagem() {
			return mensagem;
		}
		public void setMensagem(String mensagem) {
			this.mensagem = mensagem;
		}
		public Topico atualizar(Long id, TopicoRepository topicoRepository) {
			Topico topico = topicoRepository.getReferenceById(id);
			topico.setTitulo(this.titulo);
			topico.setMensagem(this.mensagem);
			return topico;
		}
	  
	    
}
