package br.usp.memoriavirtual.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.utils.BeanComMidia;
import br.usp.memoriavirtual.utils.FacesUtil;
/**
 * Servlet implementation class MultiMidiaServlet
 */
@WebServlet(description = "Usado para responder com imagens do banco.", urlPatterns = { "/multimidia" })
public class MultiMidiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final int TAMANHO_PADRAO_BUFFER = 8192 ;//
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MultiMidiaServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String nameBean = request.getParameter("bean");
		
		if(null != nameBean){//Imagem do Controle
			Integer indice = null;
			try{
				indice= new Integer (request.getParameter("indice"));
			}catch(Exception e){
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_NOT_FOUND);//404
				return;
			}
			
			
			//Recuperar Referencia do Bean em quest�o
			FacesContext facesContext = FacesUtil.getFacesContext(request, response);
			ELResolver resolver = facesContext.getApplication().getELResolver();   
			BeanComMidia  bean = (BeanComMidia) resolver.getValue(facesContext.getELContext(), null, nameBean);
			
			List<Multimidia> midias = bean.recuperaColecaoMidia();
			
			if(midias.size() <= indice){
				response.sendError(HttpServletResponse.SC_NOT_FOUND);//404
				return;
			}
			
			this.enviarStream(response , midias.get(indice) );
			
		}else{//Testa se  imagem do Banco
			
			
		}
		
	}
	
	protected void enviarStream ( HttpServletResponse response,Multimidia midia ) throws IOException{
		
		//iniciando a resposta
		response.reset();
		response.setBufferSize(TAMANHO_PADRAO_BUFFER);
		response.setContentType(getServletContext().getMimeType(midia.getNome()));
		response.setHeader("Content-Length", String.valueOf(midia.getContent().length - 2 ));
		response.setHeader("Content-Disposition", "inline; filename=\"" + midia.getNome() + "\"");
		
		//inicia os streams
		BufferedInputStream in = null;
        BufferedOutputStream out = null;
		
        try {
            // Come�a os trabalhos
        	InputStream byteInputStream = new ByteArrayInputStream(midia.getContent(), 0 ,midia.getContent().length-2 );
        	
            in = new BufferedInputStream(  byteInputStream,TAMANHO_PADRAO_BUFFER);
            out = new BufferedOutputStream(response.getOutputStream(),TAMANHO_PADRAO_BUFFER);

            // Enviando a midia
            byte[] buffer = new byte[TAMANHO_PADRAO_BUFFER];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.flush();
        } finally {
        	
            //fechando as streams
            close(out);
            close(in);
        }
        
	}
	
	private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
