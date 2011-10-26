package br.usp.memoriavirtual.controle;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroProprioRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;

public class EditarCadastroProprioMB {

	@EJB
	private EditarCadastroProprioRemote editarCadastroProprioEJB;
	private Usuario usuario;
	private String novoEmail;
	private String novoNomeCompleto;
	private String novoTelefone;
	private String novaSenha;
	private String confirmacaoNovaSenha;

	public EditarCadastroProprioMB() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		this.usuario = (Usuario) request.getSession().getAttribute("usuario");
		setNovoNomeCompleto(getNomeCompleto());
		setNovoEmail(getEmail());
		setNovoTelefone(getTelefone());
	}

	public String editarCadastroProprio() {
		if (this.novoEmail == null || this.novoNomeCompleto == null
				|| this.novaSenha == null)
			return "Incompleto";
		try {
			this.editarCadastroProprioEJB.atualizarDadosUsuario(
					usuario.getId(), getNovoEmail(), getNovoNomeCompleto(),
					getNovoTelefone(), getNovaSenha());
			MensagensDeErro.getSucessMessage("cadastro_concluido", "resultado");
			return "Sucesso";
			/** Atualizar os dados da sess�o de usuario */
		} catch (Exception e) {
			return "Falha";
		}
	}

	public String getNomeCompleto() {
		return this.usuario.getNomeCompleto();
	}

	public String getEmail() {
		return this.usuario.getEmail();
	}

	public String getTelefone() {
		return this.usuario.getTelefone();
	}

	public String getSenha() {
		return this.usuario.getSenha();
	}

	public void setNovoEmail(String novoEmail) {
		this.novoEmail = novoEmail;
	}

	public void setNovoNomeCompleto(String novoNomeCompleto) {
		this.novoNomeCompleto = novoNomeCompleto;
	}

	public void setNovoTelefone(String novoTelefone) {
		this.novoTelefone = novoTelefone;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
		this.confirmacaoNovaSenha = confirmacaoNovaSenha;
	}

	public String getNovoNomeCompleto() {
		return this.novoNomeCompleto;
	}

	public String getNovoTelefone() {
		return this.novoTelefone;
	}

	public String getNovoEmail() {
		return this.novoEmail;
	}

	public String getNovaSenha() {
		return this.novaSenha;
	}

	public String getConfirmacaoNovaSenha() {
		return this.confirmacaoNovaSenha;
	}

	public void validateSenha() {
		if (this.novaSenha.equals("")) {
			String[] argumentos = { "senha" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoSenha");
		} else if (this.novaSenha.length() < 6) {
			String[] argumentos = { "senha", "senha_minima" };
			MensagensDeErro.getErrorMessage("tamanho_minimo", argumentos,
					"validacaoSenha");
		}
	}

	public void validateConfirmacaoSenha() {
		if (confirmacaoNovaSenha.equals("")) {
			String[] argumentos = { "confirmacao_senha" };
			MensagensDeErro.getErrorMessage("campo_vazio", argumentos,
					"validacaoConfirmacaoSenha");
		} else if (!this.confirmacaoNovaSenha.equals(this.novaSenha)) {
			String[] argumentos = { "confirmacao_senha", "senha" };
			MensagensDeErro.getErrorMessage("confirmacao_errado", argumentos,
					"validacaoConfirmacaoSenha");
		}
	}

}
