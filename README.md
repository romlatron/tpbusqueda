# Métodos de búsqueda informados y no informados
## Sistemas de Inteligencia Artificial

Este repositorio contiene el informe, la presentación, el binario ejecutable y el código fuente para la implementación de un motor de búsqueda informada y no informada que incluye los algoritmos: ID, BFS, DFS, A* y Greedy Search. Y una implementación para resolver el juego rolling cubes.

Para ejecutar el programa se debe clonar el repositorio y correr el siguiente comando:
```sh
java -jar grupo12.jar [method] [?heuristic]
```
Donde `method` puede ser `DFS`, `BFS`, `ID`, `AStar` o `Greedy`. Por otro lado `heuristic` es solo obligatorio para los últimos 2 métodos y puede ser uno de los siguientes valores: `ImproveHeuristic` y `ColorCubesHeuristic`.

Así finalmente un ejemplo de la ejecución del programa sería

```sh
java -jar grupo12.jar Greedy ImproveHeuristic
```
