package sisrh.soap;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import sisrh.banco.Banco;
import sisrh.dto.Solicitacao;
import sisrh.dto.Solicitacoes;
import sisrh.seguranca.Autenticador;

import javax.xml.ws.WebServiceContext;


@WebService
@SOAPBinding(style = Style.RPC)
public class ServicoSolicitacao {

	@Resource
	WebServiceContext context;


	@WebMethod(action = "listarsolicita")
	public Solicitacoes listarSolicitacao() throws Exception {

        Autenticador.autenticarUsuarioSenha(context);
        
        String usuario = Autenticador.getUsuario(context);

		Solicitacoes solicitacoes = new Solicitacoes();

		List<Solicitacao> lista = Banco.listarSolicitacoes(usuario);
		for (Solicitacao sol : lista) {
			solicitacoes.getSolicitacoes().add(sol);
		}
		return solicitacoes;
	}
}
