/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mapgen;
import java.util.ArrayList;
import java.util.Random;
import static mapgen.PossibleDirection.Down;
import static mapgen.PossibleDirection.Left;
import static mapgen.PossibleDirection.Right;
import static mapgen.PossibleDirection.Up;

/**
 *
 * @author Honey
 */
public class MapGen {
    private static Random ran = new Random();
    private static ArrayList<MapCell> cells;
    private static MapCell[][] map;
    
    public static MapCell[][] generate(){
        cells = new ArrayList<MapCell>();
        map = new MapCell[5][5];
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[j][i] = new MapCell(j, i);
            }
        }
        
        MapCell start = map[0][ran.nextInt(0, 5)];
        start.type = TypeCell.Start;
           
        for (int i = 0; i < 2; i++) {
            MapCell neighbour = findNeighbour(start);
            cells.add(findNeighbour(start));
            neighbour.type = TypeCell.Sand;
            start.addNeighbour(neighbour);
        }
        

        int countIteration = ran.nextInt(1, cells.size());
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < countIteration; j++) {
                MapCell cell = cells.get(ran.nextInt(cells.size()));
                MapCell neighbour = findNeighbour(cell);
                cells.add(neighbour);
                if(neighbour.type == TypeCell.Empty)
                    neighbour.type = TypeCell.Sand;
                cell.addNeighbour(neighbour);
            }
            countIteration = ran.nextInt(1, cells.size());            
        }
        
        return map;
    }
    
    private static MapCell findNeighbour(MapCell cell){
        PossibleDirection dir = choiseDirection(cell.ways);
        
        switch (dir){
            case Left :
                return map[cell.position.getX() - 1][cell.position.getY()];            
            case Up :
                return map[cell.position.getX()][cell.position.getY() - 1];
            case Right :
                return map[cell.position.getX() + 1][cell.position.getY()];            
            case Down :
                return map[cell.position.getX()][cell.position.getY() + 1];            
                
            case default :
                return map[-1][-1];
        }
    }    
    private static PossibleDirection choiseDirection(ArrayList<PossibleWay> ways){
        ArrayList<PossibleWay> freeWays = new ArrayList<PossibleWay>();
        
        for(PossibleWay way : ways)
            if(way.getStatus() == StatusCell.No_Conections)
                freeWays.add(way);
        
        return freeWays.get(ran.nextInt(ways.size())).getDirection();
    }    
}
