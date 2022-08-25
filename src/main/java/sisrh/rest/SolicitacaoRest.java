package sisrh.rest;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import sisrh.banco.Banco;
import sisrh.dto.Solicitacao;

@Api
@Path("/solicitacao")
public class SolicitacaoRest {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarSolicitacao() throws Exception {
		List<Solicitacao> lista = Banco.listarSolicitacoes();		
		GenericEntity<List<Solicitacao>> entity = new GenericEntity<List<Solicitacao>>(lista) {};
		return Response.ok().entity(entity).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obterSolicitacao(@PathParam("id") String id) throws Exception {
		try {
			Solicitacao solicitacao = Banco.buscarSolicitacaoPorId(Integer.parseInt(id));
			if ( solicitacao != null ) {
				return Response.ok().entity(solicitacao).build();
			}else {
				return Response.status(Status.NOT_FOUND)
						.entity("{ \"mensagem\" : \"Solicitacao nao encontrada!\" }").build();
			}
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha para obter solicitacao!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}
	}
	
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response incluirSolicitacao(Solicitacao solicitacao) {
		try {
			Solicitacao sol = Banco.incluirSolicitacao(solicitacao);
			return Response.ok().entity(sol).build();
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na inclusao da solicitação!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}		
	}

	@PUT	
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterarSolicitacao(@PathParam("id") String id, Solicitacao solicitacao)  {
		try {			
			if ( Banco.buscarSolicitacaoPorId(Integer.parseInt(id)) == null ) {				
				return Response.status(Status.NOT_FOUND)
						.entity("{ \"mensagem\" : \"Solicitacao nao encontrada!\" }").build();
			}				
			Solicitacao sol = Banco.alterarSolicitacao(Integer.parseInt(id), solicitacao);	
			return Response.ok().entity(sol).build();
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"mensagem\" : \"Falha na alteracao da solicitacao!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}
	}
	
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirSolicitacao(@PathParam("id") String id) throws Exception {
		try {
			if ( Banco.buscarSolicitacaoPorId(Integer.parseInt(id)) == null ) {				
				return Response.status(Status.NOT_FOUND).
						entity("{ \"mensagem\" : \"Solicitacao nao encontrada!\" }").build();
			}				
			Banco.excluirSolicitacao(Integer.parseInt(id));
			return Response.ok().entity("{ \"mensagem\" : \"Solicitacao "+ id + " excluida!\" }").build();	
		}catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).
					entity("{ \"mensagem\" : \"Falha na exclusao da solicitacao!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
		}		
	}

}
