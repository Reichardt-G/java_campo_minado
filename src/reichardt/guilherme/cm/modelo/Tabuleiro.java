package reichardt.guilherme.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarOsVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		try {
			campos.parallelStream()
				.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
				.findFirst()
				.ifPresent(c -> c.abrir());;
			//Filtro acima elimina a necessidade de percorrer todo o tabuleiro duas vezes porque já verifica ambas linha e coluna.
		}catch(ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	public void marcar(int linha, int coluna) {
		campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.alternarMarcacao());
	}
	
	private void gerarCampos() {
		for(int linha = 0; linha<linhas; linha++) {
			for(int coluna = 0;  coluna<colunas;  coluna++) {
				campos.add(new Campo(linha,  coluna));
			}
		}
	}
	
	private void associarOsVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			int aleatorio = (int)(Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas < minas);
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	private String strTop;
	private String strTopIndex;
	private String strBottom;
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		strTopIndex = "   ";
		strTop = "   ";
		strBottom = "   ";
		for(int l = 0; l < colunas; l++) {
			strTopIndex = strTopIndex + " " + l + " ";
			strTop = strTop + "...";
			strBottom = strBottom + "'''";
		}
		
		
		int i = 0;
		sb.append(strTopIndex + "\n");
		sb.append(strTop + "\n");
		for(int l = 0; l < linhas; l++) {
			sb.append(l + " |");
			for(int c = 0; c < colunas; c++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("|");
			if (l<linhas-1) {
				sb.append("\n");
				//Este if serve para regular e colocar quebra de linha em todas as linhas menos na última, para a borda do tabuleiro.
			}
		}
		sb.append("\n" + strBottom);
		
		return sb.toString();
	}
	
}
