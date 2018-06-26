import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.ArtistaDAO;
import dao.EventoDAO;
import dao.LocalDAO;
import pojo.Local;
import pojo.Evento;
import pojo.Artista;

public class Main {

	public static void main(String[] args) throws SQLException {

		Scanner input = new Scanner(System.in);
		boolean sair = false;
		EventoDAO eventoDAO = new EventoDAO();
		ArtistaDAO artistaDAO = new ArtistaDAO();
		LocalDAO localDAO = new LocalDAO();
		List<Integer> listaArtistas = new ArrayList<>();
		System.out.println(" -***** QUAL A BOA DE HOJE? *****- ");
		while (!sair) {

			System.out.print(">>");
			String linha = input.nextLine();
			String[] comando = linha.split(" ");
			String operacao = comando[0];
			switch (operacao) {

			case "criarEvento":

				System.out.println("** ASSISTENTE PARA CRIAÇÃO DO EVENTO **\n");

				System.out.println("Qual o nome do Evento?");
				String nomeEvento = input.nextLine();

				System.out.println("Qual data deseja realizar o evento?");
				String dataEvento = input.nextLine();

				System.out.println("Digite uma descrição para o seu evento.");
				String descricaoEvento = input.nextLine();
				
				System.out.println("Qual o nome do local que acontecerá o evento?.");
				String localEvento = input.nextLine();
				Local local = localDAO.getLocalByName(localEvento);
				if(local == null) {
					System.out.println("Qual o nome da rua do evento?.");
					String enderecoRua = input.nextLine();
					System.out.println("Qual o bairro?.");
					String enderecoBairro = input.nextLine();
					System.out.println("Qual é o nome da cidade?.");
					String enderecoCidade = input.nextLine();
					local = new Local(localEvento, enderecoRua, enderecoBairro, enderecoCidade);
					localDAO.create(local);
				}
				
				System.out.println("Quantos grupos vão se apresentar?");
				int qtdGrupos = Integer.parseInt(input.nextLine());

				System.out.println("-- ASSISTENTE PARA CADASTRAR DOS ARTISTAS --\n");
				for (int i = 0; i < qtdGrupos; i++) {
					System.out.println("Qual o nome do grupo?");
					String nomeGrupo = input.nextLine();
					int codigoArtista = artistaDAO.getIdArtistaByName(nomeGrupo);
					if (codigoArtista != 0)
						listaArtistas.add(codigoArtista);
					else {
						System.out.println("Qual estilo desse grupo?");
						String estiloGrupo = input.nextLine();
						listaArtistas.add(artistaDAO.create(new Artista(nomeGrupo, estiloGrupo)));
					}
				}

				eventoDAO.create(nomeEvento, dataEvento, descricaoEvento, local, listaArtistas);
				listaArtistas.clear();
				break;
			case "consultaPorNome":
				System.out.println("Qual o nome do evento?");
				String nome = input.nextLine();
				Evento e1 = eventoDAO.getEventoByName(nome);
				System.out.println("Nome do Evento: "+e1.getNome());
				System.out.println("Descrição: "+e1.getDescricao());
				System.out.println("Data: "+e1.getData());
				System.out.println("Local: "+e1.getEndereco().getNome() + " Rua: "+e1.getEndereco().getRua()+ " Bairro: "+e1.getEndereco().getBairro()+
						" Cidade: "+e1.getEndereco().getCidade());
				System.out.println("Grupos que irão se apresentar: ");
				System.out.println(e1.getGrupos());
				break;
			case "exibirTodos":
				List<Evento> listaEvento = eventoDAO.getAllEventos();
				System.out.println(listaEvento);
				break;
			case "deletaEvento":
				System.out.println("Qual o nome do evento?");
				String nome1 = input.nextLine();
				if(eventoDAO.getEventoByName(nome1) == null) {
					System.out.println("Esse evento não existe");
					break;
				}
				eventoDAO.delete(nome1);
				break;
			case "alteraEvento":
				System.out.println("Qual o nome do evento?");
				String nome2 = input.nextLine();
				if(eventoDAO.getEventoByName(nome2) == null) {
					System.out.println("Evento não existe");
					break;
				}
				
				System.out.println("Qual novo nome do evento?");
				String nomeNovo = input.nextLine();
				
				System.out.println("Qual data?");
				String dataNova = input.nextLine();

				System.out.println("Digite uma descrição para o seu evento.");
				String descricaoNovo = input.nextLine();
				
				System.out.println("Qual o nome do local que acontecerá o evento?.");
				String novoLocal = input.nextLine();
				Local local2 = localDAO.getLocalByName(novoLocal);
				if(local2 == null) {
					System.out.println("Qual o nome da rua do evento?.");
					String enderecoRua = input.nextLine();
					System.out.println("Qual o bairro?.");
					String enderecoBairro = input.nextLine();
					System.out.println("Qual é o nome da cidade?.");
					String enderecoCidade = input.nextLine();
					local2 = new Local(novoLocal, enderecoRua, enderecoBairro, enderecoCidade);
					localDAO.create(local2);
				}
				
				System.out.println("Quantos grupos vão se apresentar?");
				int qtd = Integer.parseInt(input.nextLine());

				System.out.println("-- ASSISTENTE PARA CADASTRAR DOS ARTISTAS --\n");
				for (int i = 0; i < qtd; i++) {
					System.out.println("Qual o nome do grupo?");
					String grupo = input.nextLine();
					int codigo = artistaDAO.getIdArtistaByName(grupo);
					if (codigo != 0)
						listaArtistas.add(codigo);
					else {
						System.out.println("Qual estilo desse grupo?");
						String estilo = input.nextLine();
						listaArtistas.add(artistaDAO.create(new Artista(grupo, estilo)));
					}
				}

				eventoDAO.update(nome2, nomeNovo, dataNova, descricaoNovo, local2, listaArtistas);
				listaArtistas.clear();

				break;
			case "ajuda":
				System.out.println(" -***** QUAL A BOA DE HOJE? *****- ");
				System.out.println(" criarEvento      ******  Cria um novo evento");
				System.out.println(" consultaPorNome  ******  procura um evento pelo nome");
				System.out.println(" exibirTodos      ******  exibe todos os eventos");
				System.out.println(" deletaEvento     ******  Apagar um evento");
				System.out.println(" alteraEvento     ******  altera informacoes do evento");
				System.out.println(" ajuda            ******  menu de ajuda");
				System.out.println(" sair             ******  encerra o sistema");
				break;
			case "sair":
				sair = true;
				System.out.println("Fechando o sistema...");
				System.exit(1);
				break;
			default:
				System.out.println("Comando não encontrado!");
				break;
			}
		}

	}

}
