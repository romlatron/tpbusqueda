/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpbusqueda;

/**
 *
 * @author Acer
 */
public class node {
        
    public int value;
    public node [] children;
    
    public node (int value, node [] children) {
        this.value = value;
        this.children = children;
    }
}
