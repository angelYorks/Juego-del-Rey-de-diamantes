/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;
import javax.swing.*;
/**
 *
 * @author USER
 */
public class Player {

    private int id;
    private int numero;
    private int puntaje = 0;
    private boolean linea = true;
    private boolean winner = false;
    public JLabel ImagenJ;    
    public Player(int id) {
        this.id = id;

    }

    @Override
    public String toString() {
        return "Player{" + "id=" + id + ", numero=" + numero + ", puntaje=" + puntaje + ", linea=" + linea + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public boolean getLinea() {
        return linea;
    }

    public void setLinea(boolean linea) {
        this.linea = linea;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    
}
