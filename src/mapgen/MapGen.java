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
    private static final Random ran = new Random();
    private static ArrayList<MapCell> cellParents;
    private static Map map;
    
    public static MapCell[][] prepareMap(int width, int height) throws Exception{
        cellParents = new ArrayList<MapCell>();
        map = new Map(width,height);
        generateFirstPoint();
        
        return map.cells;
    }    
    public static MapCell[][] oneIteration() throws Exception{
        int countEmptyCells = map.size - cellParents.size();
        int countIteration = countEmptyCells < cellParents.size() ? countEmptyCells : cellParents.size();        
        
        for (int j = 0; j < countIteration; j++) {
            MapCell newNeighbor = addNearestNeighbor();
            //removeUnnecessary();
            if(newNeighbor.type == TypeCell.Empty){
                //newNeighbor.type = TypeCell.values()[ran.nextInt(4, 7)];
                newNeighbor.type = TypeCell.Rock;
                cellParents.add(newNeighbor);
                countEmptyCells--;
            }
        }
            
        return map.cells;
    }
    public static MapCell[][] lastIteration() throws Exception{
        generateLastPoints();
            
        return map.cells;
    }
    
    public static MapCell[][] generate(int width, int height) throws Exception{
        cellParents = new ArrayList<MapCell>();
        map = new Map(width,height);
        
        generateFirstPoint();
        generateMainPoints();
        //generateLastPoints();
        setPointsStartAndExit();
        
        return map.cells;
    }
    
    private static ArrayList<MapCell> createListEmptyCells(){
        ArrayList<MapCell> _cells = new ArrayList<MapCell>();
        for (int i = 0; i < map.height; i++)
            for (MapCell[] cell : map.cells)
                if(cell[i].type == TypeCell.Empty)
                    _cells.add(cell[i]);
        return _cells;
    }
    
    private static void generateFirstPoint(){
        MapCell firstPointGenerated = map.cells[ran.nextInt(map.width)][ran.nextInt(map.height)];
        //firstPointGenerated.type = TypeCell.values()[ran.nextInt(4, 7)];
        firstPointGenerated.type = TypeCell.FirstGeneration;
        cellParents.add(firstPointGenerated);
    }
    
    private static void generateMainPoints() throws Exception{        
        int allIteration = (int)(Math.sqrt(map.size));
        int countIteration = cellParents.size();        
        int countEmptyCells = map.size - cellParents.size();
        
        for (int i = 0; i < allIteration; i++) {
            for (int j = 0; j < countIteration; j++) {
                MapCell newNeighbor = addNearestNeighbor();
                if(newNeighbor.type == TypeCell.Empty){
                    //newNeighbor.type = TypeCell.values()[ran.nextInt(4, 7)];
                    newNeighbor.type = TypeCell.Rock;
                    cellParents.add(newNeighbor);
                    countEmptyCells--;
                }
            }
            countIteration = countEmptyCells < cellParents.size() ? countEmptyCells : cellParents.size();
            removeUnnecessary();
            if(countEmptyCells < cellParents.size())
                break;
        }
    }
    
     private static void removeUnnecessary(){
        for (int i = 0; i < cellParents.size(); i++) {
            if(isGoodChoise(4 - cellParents.get(i).getCountFreeWays(), 4, 1))
                cellParents.remove(i);
        }
    }
     
    private static void generateLastPoints() throws Exception{
        if(!cellParents.isEmpty()){
            do{
                cellParents = createListEmptyCells();

                for (int i = 0; i < cellParents.size(); i++){
                    MapCell cell = cellParents.get(i);
                    MapCell neighbour;
                    neighbour = getNeighbour(cellParents.get(i), cell.ways.get(ran.nextInt(cell.ways.size())).getDirection());
                    if(neighbour.type != TypeCell.Empty){
                        if(isGoodChoise(neighbour.getCountFreeWays(), 4, 2)){
                        //cellParents.get(i).type = TypeCell.values()[ran.nextInt(4, 7)];
                        cellParents.get(i).type = TypeCell.Sand;
                        cellParents.get(i).registerNeighbours(neighbour);
                        cellParents.remove(i);
                        break;
                        }
                    }
                }
            }while(!cellParents.isEmpty());
        }
        else
            throw new Exception("Array parent cells is Empty");
    }
    
    private static MapCell addNearestNeighbor() throws Exception{
        MapCell cellParent = selectCellParent();
        PossibleDirection dir = selectFreeDirection(cellParent);
        MapCell neighbour = getNeighbour(cellParent, dir);
        tieNeighbors(cellParent, neighbour);
        
        return neighbour;
    }
    private static MapCell selectCellParent() throws Exception{
        if(!cellParents.isEmpty()){
            MapCell cellParent;            
            do{
                cellParent = cellParents.get(ran.nextInt(cellParents.size()));                
            }while(!isGoodChoise(cellParent.getCountFreeWays(), 4, 2));
            
            return cellParent;
        }
        else
            throw new Exception("Array parent cells is Empty");
    }
    private static boolean isGoodChoise(int value, int ultimateChance, int divider){        
        int chance = (int)((Math.pow(value, divider) / Math.pow(ultimateChance, divider)) * 100);
        return chance > ran.nextInt(100);
    }    
    private static PossibleDirection selectFreeDirection(MapCell cell)throws Exception{
        ArrayList<PossibleWay> freeWays = cell.getFreeWays();
        if(!freeWays.isEmpty()){
            MapCell neighbour;
            PossibleDirection dir;
            do{
                dir = freeWays.get(ran.nextInt(freeWays.size())).getDirection();
                neighbour = getNeighbour(cell, dir);
            }while(!isGoodChoise(neighbour.getCountFreeWays(), 4, 2));
            return dir;
            //return freeWays.get(ran.nextInt(freeWays.size())).getDirection();
        }
        throw new Exception("No free ways");
    }
    private static MapCell getNeighbour(MapCell cell, PossibleDirection dir)throws Exception{       
        switch (dir){
            case Left -> {
                return map.cells[cell.position.getX() - 1][cell.position.getY()];
            }
            case Up -> {
                return map.cells[cell.position.getX()][cell.position.getY() - 1];
            }
            case Right -> {
                return map.cells[cell.position.getX() + 1][cell.position.getY()];
            }
            case Down -> {
                return map.cells[cell.position.getX()][cell.position.getY() + 1];
            }
            default -> throw new Exception("Failed to get neighbor");
        }
    }    
    private static void tieNeighbors(MapCell cell1, MapCell cell2){
        cell1.registerNeighbours(cell2);
    }
    
    private static void setPointsStartAndExit(){
        setPoint(TypeCell.Start, PossibleDirection.Left);
        //map.cells[0][ran.nextInt(0, map.height)].type = TypeCell.Start;
        //map.cells[map.width - 1][ran.nextInt(map.height)].type = TypeCell.Exit;
        setPoint(TypeCell.Exit, PossibleDirection.Right);
    }
    
    private static void setPoint(TypeCell type, PossibleDirection dir){
        ArrayList<MapCell> cells = new ArrayList<>();
        if(dir == PossibleDirection.Left){
            int counter = -1;
            do{
                counter++;
                for (int i = 0; i < map.height; i++) {
                    for (int j = 0; j < map.width; j++) {
                        if(map.cells[j][i].position.getX() == counter && map.cells[j][i].type != TypeCell.Empty)
                            cells.add(map.cells[j][i]);
                    }
                }
            }while(cells.isEmpty());
            cells.get(ran.nextInt(cells.size())).type = type;
        }
        else{
            int counter = map.width;
            do{
                counter--;
                for (int i = 0; i < map.height; i++) {
                    for (int j = 0; j < map.width; j++) {
                        if(map.cells[j][i].position.getX() == counter && map.cells[j][i].type != TypeCell.Empty)
                            cells.add(map.cells[j][i]);
                    }
                }
            }while(cells.isEmpty());
            cells.get(ran.nextInt(cells.size())).type = type;
        }
    }
}