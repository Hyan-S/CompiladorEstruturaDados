import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class TransformarTokens {
    
    String caminhoReferencias = "C:\\Users\\iurye\\Desktop\\Atividade Carmen\\CompiladorEstruturaDados\\Referencias\\Referencias.txt";
    String pastaSaida = "C:\\Users\\iurye\\Desktop\\Atividade Carmen\\CompiladorEstruturaDados\\Arquivos";
    String nomeArquivoTokens = "TokensTransformados.txt";
    
    String nomeArquivoErros = "RelatorioErros.txt"; 

    public void transformarTokens(String texto) {
        try {
            Path pathRef = Paths.get(caminhoReferencias);
            String refConteudo = Files.readString(pathRef).trim();
            
            String[] arrayReservadas = refConteudo.split("\\|");
            
            Set<String> palavrasReservadas = new HashSet<>();
            for (String palavra : arrayReservadas) {
                palavrasReservadas.add(palavra.trim());
            }

            String textoFormatado = texto.replaceAll("([():;<>=+*/\\-])", " $1 ");
            String[] tokensIniciais = textoFormatado.trim().split("\\s+");

            StringBuilder resultado = new StringBuilder();
            StringBuilder erros = new StringBuilder(); 
            
            for (String token : tokensIniciais) {
                if (token.isEmpty()) continue; 

                if (palavrasReservadas.contains(token)) {
                    resultado.append(String.format("[%s, %s]\n", token, token));
                } else if (token.equals("AND") || token.equals("OR") || token.equals("NOT")) {
                    resultado.append(String.format("[Ol, %s]\n", token));
                } else if (token.startsWith("\"") && token.endsWith("\"")) {
                    resultado.append(String.format("[Fr, %s]\n", token));
                } else if (token.matches("[<>=]")) {
                    resultado.append(String.format("[Or, %s]\n", token));
                } else if (token.matches("[+\\-*/]")) {
                    resultado.append(String.format("[Om, %s]\n", token));
                } else if (token.matches("\\d+")) {
                    resultado.append(String.format("[Nu, %s]\n", token));
                } else if (token.matches("[a-zA-Z]+")) {
                    resultado.append(String.format("[Id, %s]\n", token));
                } else {
                    erros.append(String.format("[ERRO_LEXICO] Token não reconhecido: %s\n", token));
                }
            }

            Path dirPath = Paths.get(pastaSaida);
            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            
            Path arquivoSaida = dirPath.resolve(nomeArquivoTokens);
            Files.writeString(arquivoSaida, resultado.toString());
            
            // Salva o Relatório de Erros
            Path arquivoErros = dirPath.resolve(nomeArquivoErros);
            Files.writeString(arquivoErros, erros.toString());
            
            System.out.println("Processamento concluído!");
            System.out.println("Tokens: " + arquivoSaida.toAbsolutePath());
            System.out.println("Erros: " + arquivoErros.toAbsolutePath());

        } catch (Exception ex) {
            System.out.println("Erro ao transformar tokens: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}