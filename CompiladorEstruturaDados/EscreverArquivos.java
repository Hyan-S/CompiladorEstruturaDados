import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EscreverArquivos {
    
    private final TransformarTokens TransformarTokens;

    public EscreverArquivos(TransformarTokens transformarTokens){
        this.TransformarTokens = transformarTokens;   }

    String pastaSaida = "C:\\Users\\iurye\\Desktop\\Atividade Carmen\\CompiladorEstruturaDados\\Arquivos";
    String nomeArquivoFiltradoString = "ArquivoFiltrado.txt";

    public void EscreverArquivo(String texto) {
        try {
            Path dirPath = Paths.get(pastaSaida);
            
            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Path novoArquivo = dirPath.resolve(nomeArquivoFiltradoString);
            System.out.println("Salvando limpo em: " + novoArquivo.toAbsolutePath());

            Files.writeString(novoArquivo, texto);
            System.out.println("Arquivo de referência gerado com sucesso!");

            TransformarTokens.transformarTokens(texto);

        } catch (Exception ex) {
            System.out.println("Erro durante a escrita: " + ex.getMessage());
            ex.printStackTrace(); 
        }
    }
}
