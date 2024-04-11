import java.time.Duration;
import java.time.Instant;

public class Cliente {
    private int qntdPessoa;
    private Instant horaEntrada;
    private Instant horaSaida;

    public Cliente(int qntdPessoa) {
        this.qntdPessoa = qntdPessoa;
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

    public int getqntdPessoa() {
        return qntdPessoa;
    }    
}
