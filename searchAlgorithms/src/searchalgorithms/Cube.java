/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchalgorithms;

/**
 *
 * @author Acer
 */
public class Cube 
{
    public enum color {WHITE, BLACK, WLEFT, WRIGHT, WUP, WDOWN, EMPTY };
    private color currentColor;
    
    public Cube() { this.currentColor = color.BLACK; }
    
    public Cube(color c) { this.currentColor = c; }

    public color getCurrentColor() { return currentColor; }

    public void setCurrentColor(color currentColor) { this.currentColor = currentColor; }
    
    
}
