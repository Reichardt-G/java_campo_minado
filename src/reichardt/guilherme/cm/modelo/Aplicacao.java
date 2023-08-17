package reichardt.guilherme.cm.modelo;

public class Aplicacao {
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(8,8,6);
		new TabuleiroConsole(tabuleiro);
	
	}
}
