import java.time.Duration;
import java.time.Instant;

public class Cliente {
    private String nome;
    private int numeroPessoas;
    private Instant horaEntrada;
    private Instant horaSaida;

    public Cliente(String nome, int numeroPessoas) {
        this.nome = nome;
        this.numeroPessoas = numeroPessoas;
        this.horaEntrada = Instant.now();
    }

    public Duration calcularTempoPermanencia() {
        if (horaSaida != null) {
            return Duration.between(horaEntrada, horaSaida);
        } else {
            Instant agora = Instant.now();
            return Duration.between(horaEntrada, agora);
        }
    }

    public void registrarSaida() {
        horaSaida = Instant.now();
    }

    public int getNumeroPessoas() {
        return numeroPessoas;
    }
}
