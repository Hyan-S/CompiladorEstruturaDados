import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Manipulador {

    String nome;
    String texto = "";
    String line;

    String caminhoLeituraArq = "C:\\Users\\HyanCosta\\Desktop\\Compilador\\Arquivos";
    String pastaSaida = "C:\\Users\\HyanCosta\\Desktop\\Compilador\\Arquivos";
    String nomeArquivoFiltradoString = "ArquivoFiltrado.txt";

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

            EscreverArquivo(texto);
            lerArquivo.close();
            
        } catch (Exception ex) {
            System.out.println("Erro de leitura de arquivo: " + ex.getMessage());
        }
    }

    private void EscreverArquivo(String texto) {
        try {
            Path dirPath = Paths.get(pastaSaida);
            
            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Path novoArquivo = dirPath.resolve(nomeArquivoFiltradoString);
            System.out.println("Salvando limpo em: " + novoArquivo.toAbsolutePath());

            Files.writeString(novoArquivo, texto);
            System.out.println("Arquivo de referência gerado com sucesso!");

        } catch (Exception ex) {
            System.out.println("Erro durante a escrita: " + ex.getMessage());
            ex.printStackTrace(); 
        }
    }

}