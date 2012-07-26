package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.ItemAuditoria;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;


/**
 * @author MAC
 */
@Remote
public interface ExcluirInstituicaoRemote{
	
	public List<Instituicao> listarTodasInstituicoes(Grupo grupo, Usuario usuario) throws ModeloException;
	/**
	 * M�todos faz uma requisi��o no banco de dados afim de encontrar todos 
	 * os usu�rios do mem�ria virtual que s�o administradores.
	 * @return
	 * Lista de usu�rios que s�o administradores.
	 * @throws ModeloException
	 */
	public List<Usuario> listarAdministradores() throws ModeloException;
	
	
	
	/**
	 * M�todo faz uma requisi��o no banco de dados afim de encontrar uma institui��o
	 * cujo nome corresponda a String que � recebida como par�metro e que n�o esteja mais ativa no 
	 * sistema do mem�ria virtual.
	 * @param nomeInstituicao
	 * Par�metro �til para encontrar a institui��o desejada.
	 * @return
	 * Retorna Objeto Instituicao cujo nome coincide com o par�metro passado.
	 * @throws ModeloException
	 */
	public Instituicao recuperarInstituicaoFalse(String nomeInstituicao) throws ModeloException ;
	
	
	/**
	 * M�todo faz uma requisi��o no banco de dados afim de encontrar um objeto ItemAuditoria
	 * @return
	 * Retorna 
	 * Objeto ItemAuditoria.
	 * @throws ModeloException
	 */
	public ItemAuditoria recuperarItemAuditoria(long chave) throws ModeloException;
	
	
	/**
	 * M�todo faz uma requisi��o no banco de dados afim de encontrar uma lista de objetos Acesso
	 * cujo a coluna instituicao corresponde ao par�metro instituicao, e que a coluna validade 
	 * corresponde ao par�metro validade.
	 * @param 
	 * instituicao
	 * @param 
	 * validade
	 * @return
	 * Objeto List<Acesso>
	 * @throws ModeloException
	 */
	public List<Usuario> recuperarGerentesdaInstituicao(Instituicao instituicao, boolean validade)throws ModeloException;
	
	
	/**
	 * Envia o email 
	 * @param Email
	 * @param assunto
	 * @param textoEmail
	 */
	public void enviaremail(String Email,String assunto,String textoEmail);
	
	
	/**
	 * M�todo faz uma requisi��o no banco de dados afim de encontrar um objeto Aprova��o
	 * cujo a coluna chaveEstrangeira corresponde ao par�metro chave, e que a coluna tabelaEstrangeira 
	 * corresponde a Instituicao.
	 * @param chave
	 * @return
	 * Objeto Aprovacao
	 * @throws ModeloException
	 */
	public Aprovacao recuperarAprovacao(long chave)
	throws ModeloException;
	
	
	/**
	 * M�todo faz uma requisi��o no banco de dados afim de encontrar um objeto Usuario
	 * @param coluna
	 * @return
	 * Objeto Usuario
	 * @throws ModeloException
	 */
	public Usuario getUsuario(String coluna, String parametro)
	throws ModeloException;
	/**
	 * M�todo efetiva a exclus�o da institui��o ou reativa a institui��o, dependendo do estado do flag acao
	 * ele tamb�m trata das rela��es com o objeto acesso. 
	 * @param instituicao
	 * @param acao
	 * @param flagAcesso
	 * @throws ModeloException
	 */
	public void validarExclusaoInstituicao(Instituicao instituicao, boolean acao,boolean flagAcesso)
	throws ModeloException;
	/**
	 * Registra um objeto Aprova��o no banco de dados
	 * @param validador
	 * @param instituicao
	 * @param dataValidade
	 * @return 
	 */
	public Aprovacao registrarAprovacao(Usuario validador, Instituicao instituicao,
			Date dataValidade);
	/**
	 * M�todo marca uma institui��o que deve ser aprovada para ser exclu�da
	 * @param instituicao
	 * @param acao
	 * @param flagAcesso
	 * @throws ModeloException
	 */
	public void marcarInstituicaoExcluida(Instituicao instituicao, boolean acao,boolean flagAcesso)
	throws ModeloException;
	public void excluirAprovacao(Aprovacao aprovacao);
	List<Instituicao> listarTodasInstituicoes() throws ModeloException;
	
}