/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapgen;

import java.util.ArrayList;

/**
 *
 * @author Honey
 */
enum TypeCell{
    Sand,
    Rock,
    Water,
    Empty
}
enum Direction{
    Left,
    Up,
    Right,
    Down
}

public class MapCell {
    public MapCell(){
        type = TypeCell.Empty;
    }
    
    TypeCell type;    
    ArrayList<MapCell> array;
}
