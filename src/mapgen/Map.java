/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapgen;

/**
 *
 * @author Honingard
 */
public class Map {    
    public MapCell[][] Cells;
    
    public void generate() throws Exception{
        Cells = MapGen.generate();
    }
    
    public MapCell getCell(Dot2D dot){
        return Cells[dot.getX()][dot.getY()];
    }
    public MapCell getCell(int x, int y){
        return Cells[x][y];
    }
    
    public void printInfo(){
        for (int i = 0; i < Cells.length; i++) {
            for (int j = 0; j < Cells.length; j++) {
                Cells[j][i].printInfo();
                System.out.print("\n");
            }
        }
    }
}
