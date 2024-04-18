public class Mesa {
    private String id;
    private int lugares;
    private boolean ocupado;

    public Mesa(String id, int lugares) {
        this.id = id;
        this.lugares = lugares;
        this.ocupado = false;
    }

    public String getId() {
        return id;
    }

    public int getLugares() {
        return lugares;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
}
