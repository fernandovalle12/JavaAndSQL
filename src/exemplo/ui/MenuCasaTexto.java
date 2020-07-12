package exemplo.ui;

import exemplo.dao.CasaDao;
import exemplo.modelo.Casa;
import exemplo.modelo.Pessoa;

import java.util.List;

public class MenuCasaTexto extends MenuEspecificoTexto {
    private CasaDao dao;

    public MenuCasaTexto() {
        super();
        dao = new CasaDao();
    }

    private int obterIdCasa() {
        System.out.print("Escolha o id da casa: ");
        int id = entrada.nextInt();
        entrada.nextLine();

        return id;
    }

    private Casa obterDadosCasa(Casa casa) {
        Casa p;
        Pessoa pessoa = new Pessoa();

        if (casa == null) {
            p = new Casa();
        } else {
            p = casa;
        }

        System.out.print("Digite o endereco da casa: ");
        String endereco = entrada.nextLine();

        System.out.print("Digite o nome do proprietario: ");
        pessoa.setNome(entrada.nextLine());

        entrada.nextLine();

        p.setEndereco(endereco);
        p.setProprietario(pessoa);

        return p;
    }

    @Override
    public void adicionar() {
        System.out.println("Adicionar Casa");
        System.out.println();

        // obter dados da casa
        Casa novaCasa = obterDadosCasa(null);

        // inserir no banco a casa -> DAO
        dao.insert(novaCasa);
    }

    @Override
    public void editar() {
        System.out.println("Editar Casa");
        System.out.println();

        // listar as casa
        imprimirCasas();

        // pedir um id de casa
        int id = obterIdCasa();

        Casa casaAModificar = dao.getById(id);

        // obter os dados da casa
        Casa novaCasa = obterDadosCasa(casaAModificar);

        // atualizar casa no banco
        novaCasa.setId(casaAModificar.getId());
        dao.update(novaCasa);
    }

    @Override
    public void excluir() {
        System.out.println("Excluir Casa");
        System.out.println();

        // listar as casa
        imprimirCasas();
        // pedir um id de casa
        int id = obterIdCasa();

        // remover casa do banco
        dao.delete(id);
    }

    @Override
    public void listarTodos() {
        System.out.println("Lista de Casas");
        System.out.println();

        imprimirCasas();
    }

    private void imprimirCasas() {
        // obter casas do banco
        List<Casa> casas = dao.getAll();

        for (Casa p : casas) {
            System.out.println(p.getId() + "\t" + p.getEndereco() + "\t" + p.getProprietario());
        }
    }
}
