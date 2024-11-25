package Logic;

import java.util.*;
import javax.swing.JOptionPane;

/**
 * Clase que contiene la lógica del juego.
 */
public class Logica {

    private static boolean mensaje1 = false;
    private static boolean mensaje2 = false;

    public static void Eliminar(ArrayList<Player> players) {
        for (Player player : players) {
            if (player.getPuntaje() <= -10) {
                player.setLinea(false);
                System.out.println("<<<<<<< Eliminado: player " + player.getId());
            }
        }
    }

    public static int Media(ArrayList<Player> players) {
        int media = 0;
        int size = 0;
        for (Player player : players) {
            if (player.getLinea()) {
                size++;
                media += player.getNumero();
            }
        }
        return !players.isEmpty() ? media / size : 0;
    }

    public static Double Result(ArrayList<Player> players) {
        int media = 0;
        int size = 0;
        for (Player player : players) {
            if (player.getLinea()) {
                size++;
                media += player.getNumero();
            }
        }
        return !players.isEmpty() ? (media / size) * 0.8 : 0;
    }

    public static void WinnerRound(ArrayList<Player> players) {

        Double resultado = Result(players);
        Double distancia;
        Double minimo = 101.00;

        boolean regla1 = false;
        boolean regla2 = false;

        // Lista para almacenar los ganadores
        List<Player> winners = new ArrayList<>();

        //aplicar regla 1 y 2
        regla1 = NewRuleOne(players);
        regla2 = NewRuleTwo(players);

        if (regla1) {
            showMessage("Se aplico regla 1");
        } else if (regla2) {
            for (Player player : players) {
                if (player.getNumero() == 100) {
                    winners.add(player);
                }
            }
        } else {
            for (Player player : players) {
                distancia = Math.abs(player.getNumero() - resultado);

                if (distancia < minimo) {
                    minimo = distancia;
                    winners.clear();
                    winners.add(player);
                } else if (Objects.equals(distancia, minimo)) {
                    winners.add(player);
                }
            }
        }

        if (winners.size() > 1) {
            showMessage("Hay " + winners.size() + " ganadores, nadie pierde puntos");
        } else if (winners.size() == 1) {
            showMessage("El ganador de la ronda es el JUGADOR " + winners.get(0).getId());
            ActualizarP(players, winners.get(0));
        } else {
            showMessage("No hay ganadores en esta ronda ");
        }
    }

    public static int Vivos(ArrayList<Player> players) {
        int vivos = 0;
        for (Player player : players) {
            if (player.getLinea()) {
                vivos++;
            }
        }
        return vivos;
    }

    public static boolean Winner(ArrayList<Player> players) {
        int winnerCount = 0;
        boolean reiniciar = false;
        Player winner = null;
        for (Player player : players) {
            if (player.getLinea()) {
                winnerCount++;
                winner = player;
                if (winnerCount > 1) {
                    winner = null;
                    break;
                }
            }
        }

        if (winnerCount == 1 && winner != null) {
            showMessage("¡El ganador es el jugador " + winner.getId() + "!");
            reiniciar = true;

        }
        return reiniciar;
    }

    public static void ActualizarP(ArrayList<Player> players, Player winner) {

        // Verifica si la lista de jugadores está vacía o es nula
        if (players == null || players.isEmpty()) {
            System.out.println("La lista de jugadores está vacía o es nula.");
            return; // Sale del método si no hay jugadores
        }

        // Actualiza puntajes de los jugadores
        if (winner != null) {
            for (Player player : players) {
                if (player != winner) {
                    player.setPuntaje(player.getPuntaje() - 1);
                }
            }
        }

        // Elimina a los jugadores que han alcanzado el puntaje mínimo
        Eliminar(players);

        // Revisa cuántos jugadores quedan vivos después de eliminar
        int vivosDespues = Vivos(players);

        // Aplicar reglas basadas en el número de jugadores vivos
        aplicarReglas(players, vivosDespues);
    }

    private static void aplicarReglas(ArrayList<Player> players, int vivosDespues) {
        if (vivosDespues < 5) {
            if (!mensaje1) {
                showMessage("Se activa una nueva regla: 1");
                mensaje1 = true;
            }
            NewRuleOne(players);
        }

        if (vivosDespues < 3) {
            if (!mensaje2) {
                showMessage("Se activa una nueva regla: 2");
                mensaje2 = true;
            }
            NewRuleTwo(players);
        }
    }

    private static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static boolean NewRuleOne(ArrayList<Player> players) {
        boolean ignorar = false;
        int eliminados = 0;
        for (Player player : players) {
            if (!player.getLinea()) {
                eliminados++;
            }
        }
        if (eliminados > 0) {
            Map<Integer, Integer> contadorNumeros = new HashMap<>();

            for (Player player : players) {
                int numero = player.getNumero();
                contadorNumeros.put(numero, contadorNumeros.getOrDefault(numero, 0) + 1);
            }

            boolean repeticiones = false;
            for (Map.Entry<Integer, Integer> entry : contadorNumeros.entrySet()) {
                if (entry.getValue() > 1) {
                    repeticiones = true;
                    ignorar = true;
                }
            }

            if (repeticiones) {
                for (Player player : players) {
                    player.setPuntaje(player.getPuntaje() + 1);
                }
            }
        }

        return ignorar;
    }

    public static boolean NewRuleTwo(ArrayList<Player> players) {

        boolean regla2 = false;
        Player winner = null;
        Player loser = null;
        ArrayList<Player> finalistas = new ArrayList<>();
        int vivos = 0;

        // Recoger jugadores vivos en la lista finalistas
        for (Player player : players) {
            if (player.getLinea()) {
                finalistas.add(player); // Agregar a finalistas
                vivos++;
                if (vivos > 2) {
                    break; // Solo necesitamos los dos primeros vivos
                }
            }
        }

        // Revisar finalistas para determinar el ganador y el perdedor
        if (vivos == 2) {
            regla2 = true;
            for (Player player : finalistas) {
                if (player.getNumero() == 0) {
                    loser = player;
                }
                if (player.getNumero() == 100) {
                    winner = player;
                }
            }

            // Eliminar al perdedor
            if (loser != null && winner != null) {
                loser.setLinea(false);
                Winner(players); // Determina el ganador
            }
        }
        return regla2;
    }
}
