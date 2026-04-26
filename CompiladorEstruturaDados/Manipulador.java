import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Manipulador {

    String nome;
    String texto = "";
    String line;

    String caminhoLeituraArq = "C:\\Users\\iurye\\Desktop\\Atividade Carmen\\CompiladorEstruturaDados\\Arquivos";

    private final EscreverArquivos EscreverArquivo;

    public Manipulador(EscreverArquivos escreverArquivo) {
        this.EscreverArquivo = escreverArquivo;
    }

    static Scanner ler = new Scanner(System.in);

    public void ManipularArquivo() {
        try {
            System.out.print("Digite o nome do arquivo e extensão (ex: codigo.txt): ");
            nome = ler.nextLine();

            FileReader arquivo = new FileReader(caminhoLeituraArq + "\\" + nome);
            BufferedReader lerArquivo = new BufferedReader(arquivo);

            System.out.println("Lendo e filtrando o arquivo...");

            while ((line = lerArquivo.readLine()) != null) {
                
                int indexComentario = line.indexOf("//");
                if (indexComentario != -1) {
                    line = line.substring(0, indexComentario);
                }

                line = line.trim().replaceAll("\\s+", " ");

                if (!line.isEmpty()) {
                    texto += line + "\n";
                }
            }

            
            EscreverArquivo.EscreverArquivo(texto);
            lerArquivo.close();
            
        } catch (Exception ex) {
            System.out.println("Erro de leitura de arquivo: " + ex.getMessage());
        }
    }
}