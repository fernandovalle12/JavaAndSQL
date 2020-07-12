package exemplo;

import java.util.List;

import exemplo.modelo.Departamento;
import exemplo.modelo.Pessoa;
import exemplo.ui.MenuPrincipalTexto;

public class Principal {
	
	public static void main(String[] args) {

		MenuPrincipalTexto menu = new MenuPrincipalTexto();
		
		menu.executa();
	}

	public static void imprimePessoas(List<Pessoa> pessoas) {
		System.out.println("Lista de pessoas:");
        for (Pessoa pessoa : pessoas) {
			System.out.println(pessoa);
		}
	}
	
	public static void imprimeDepartamentos(List<Departamento> departamentos) {
		System.out.println("Lista de departamentos:");
        for (Departamento departamento : departamentos) {
			System.out.println(departamento);
		}
	}
}
