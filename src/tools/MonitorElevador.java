package tools;

import enums.DirecaoMotor;
import enums.EstadoPortas;

/**
 * Extensão do módulo Main (mais propriamente "MainControloElevador") 
 * que funciona como um "shared object" para facilitar a comunicação 
 * entre as diferentes threads.
 *
 * @author Asus
 */
public class MonitorElevador {

    private boolean flagFloorReached = true;
    private boolean flagFuncionamento = false;

    private final int NUM_PISOS = 4;
    private int pisoAtual = 0;

    private DirecaoMotor direcaoMotor;
    private EstadoPortas estadoPortas = EstadoPortas.ABERTO;

    public synchronized void acorda() {
        this.notify();
    }

    public synchronized void acordaTodas() {
        this.notifyAll();
    }

    public synchronized void espera() {
        try {
            //tive que adicionar a alteração da flag aqui porque o ElevatorRunTime
            //demorava muito tempo a faze-lo e este método não fazia o wait() ...
            //(estes algoritmos so dao trabalho)
            this.flagFloorReached = false;
            while (!flagFloorReached) {
                this.wait();
            }

        } catch (InterruptedException ie) {
        }
    }

    public synchronized void setPisoAtual(int piso) {
        this.pisoAtual = piso;
    }

    public synchronized int getPisoAtual() {
        return this.pisoAtual;
    }

    /**
     * Altera a direção do motor para se deslocar para cima ou para baixo.
     *
     * @param estado enum constant referente à direção
     */
    public synchronized void setDirecaoMotor(DirecaoMotor estado) {
        this.direcaoMotor = estado;
    }

    /**
     * Retorna o estado direcional do motor.
     *
     * @return enum constant sobre o estado atual do motor
     */
    public synchronized DirecaoMotor getDirecaoMotor() {
        return this.direcaoMotor;
    }

    /**
     * Retorna o estado atual das portas.
     *
     * @param estado enum constant referente ao estado das portas
     */
    public synchronized void setEstadoPortas(EstadoPortas estado) {
        this.estadoPortas = estado;
    }

    /**
     * Retorna o estado atual das portas.
     *
     * @return enum constant sobre o estado das portas
     */
    public synchronized EstadoPortas getEstadoPortas() {
        return this.estadoPortas;
    }

    /**
     * Altera a sinalização para se o elevador se encontra no piso em questão
     *
     * @param flag
     */
    public synchronized void setFloorReachedFlag(boolean flag) {
        this.flagFloorReached = flag;
    }

    /**
     * Retorna a sinalização sobre se o elevador se encontra no piso em questão
     *
     * @return retorna se o elevador se encontra num piso ou não
     */
    public synchronized boolean isFloorReached() {
        return this.flagFloorReached;
    }

    /**
     * Altera o estado funcional do elevador.
     *
     * @param flag booleano para o andamento do elevador
     */
    public synchronized void setFlagFuncionamento(boolean flag) {
        this.flagFuncionamento = flag;
    }

    /**
     * Retorna o estado funcional do elevador.
     *
     * @return retorna se o elevador está ou não em funcionamento
     */
    public synchronized boolean isEmFuncionamento() {
        return this.flagFuncionamento;
    }

}