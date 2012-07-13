package br.usp.memoriavirtual.servlet;

import java.io.DataInputStream;
import java.io.IOException;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.controle.CadastrarInstituicaoMB;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.utils.FacesUtil;

public class CadastrarInstituicaoUpload  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -413155777348292080L;

	
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
		
		
		String content = req.getContentType();
		
		if((content !=  null )&& (content.indexOf("multipart/form-data")>= 0 )){
			
			
			DataInputStream stream = new DataInputStream(req.getInputStream()); 
			
			int tamanho = req.getContentLength();
			
			byte bytes[] = new byte[tamanho];
			
			int byteslidos = 0 ;
			int totalbyteslidos = 0 ;
			
			while ( totalbyteslidos < tamanho){
				
				byteslidos = stream.read(bytes, totalbyteslidos, tamanho);
				
				totalbyteslidos += byteslidos;
				 
				
			}
			String temp = new String (bytes);
			temp = temp.substring(0, 200);
			
			String nomeArquivo = temp.substring(temp.indexOf("filename=\"")+10, 200);
			nomeArquivo = nomeArquivo.substring(0, nomeArquivo.indexOf('"'));
			
			String contentType = temp.substring(temp.indexOf("Content-Type:")+13, 200);
			contentType = contentType.substring(0, contentType.indexOf('\n'));
			
			Multimidia imagem = new Multimidia(nomeArquivo, contentType , bytes , tamanho);
			
			//Antecipando a instancia do ManegedBean antes mesmo que a p�gina esteja carregada
			FacesContext facesContext = FacesUtil.getFacesContext(req, response);
			ELResolver resolver = facesContext.getApplication().getELResolver();   
			CadastrarInstituicaoMB  bean = (CadastrarInstituicaoMB) resolver.getValue(facesContext.getELContext(), null, "cadastrarInstituicaoMB");		
			
			bean.adicionarArquivo(imagem);
			
		}
	}
}