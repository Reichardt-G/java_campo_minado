package reichardt.guilherme.cm.modelo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {
	
	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}
	
	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while(continuar) {
				//Enquanto a booleana for verdadeira, o loop do jogo continua sendo executado.
				cicloDoJogo();
				
				System.out.println("Outra partida? (S/n) ");
				String resposta = entrada.nextLine();
				
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				}else {
					tabuleiro.reiniciar();
				}
				
			}
			
		}catch(SairException e) {
			System.out.println("Fim!");
		}finally {
			entrada.close();
		}
		
		
	}

	private void cicloDoJogo() {
		try {
			
			//Enquanto o objetivo do jogo n�o for alcan�ado, o loop do jogo continua sendo executado.
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				
				String digitado = capturarValorDigitado("Digite (linha,coluna): ");
				
				//System.out.println(Arrays.toString(digitado.split(",")));
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
										.map(e -> Integer.parseInt(e.trim())).iterator();
				
				digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)marcar: ");
				
				if("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				} else if("2".equals(digitado)){
					tabuleiro.marcar(xy.next(), xy.next());
				}
				
			}
			
			System.out.println(tabuleiro);
			System.out.println("Voc� Ganhou!");
			
		}catch(ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Voc� Perdeu!");
			
		}
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;
	}

}
