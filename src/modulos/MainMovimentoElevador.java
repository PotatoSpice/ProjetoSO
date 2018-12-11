/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulos;

import java.util.logging.Level;
import java.util.logging.Logger;
import tools.MonitorElevador;

public class MainMovimentoElevador extends Thread {

    private final int FlOOR_WAIT_TIME_MS = 5000;
    //número de pisos a deslocar ...
    private int numOfFloors = 0;
    MonitorElevador monitor;
    Motor motor;

    public MainMovimentoElevador(MonitorElevador monitor, Motor motor, int num) {
        this.monitor = monitor;
        this.numOfFloors = num;
        this.motor = motor;
    }

    @Override
    public void run() {
        try {
            this.monitor.setFloorReachedFlag(false);
            //para dar tempo para o codigo correr ...
            Thread.sleep(10);
            //apeteceu-me pôr o nome ...
            Thread.currentThread().setName("[RunningElevator] "
                    + "NumberOfFloorToMove: " + this.numOfFloors
                    + " WaitTimeBetweenFloors: " + this.FlOOR_WAIT_TIME_MS);

            //vai alterando o piso atual ...
            //assim quando estivermos a usar os botoes de paragem e assim
            //já fica guardado o piso atual.
            System.out.println("[PISO ATUAL]: " + this.monitor.getPisoAtual());
            for (int i = 0; i < this.numOfFloors; i++) {
                Thread.sleep(FlOOR_WAIT_TIME_MS);
                if (this.monitor.getDirecaoMotor().toString().equals("CIMA")) {
                    this.monitor.setPisoAtual(this.monitor.getPisoAtual() + 1);
                } else {
                    this.monitor.setPisoAtual(this.monitor.getPisoAtual() - 1);
                }
                System.out.println("[PISO ATUAL]: " + this.monitor.getPisoAtual());
            }

            this.monitor.setFloorReachedFlag(true);
        } catch (InterruptedException ex) {
            Logger.getLogger(MainMovimentoElevador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}