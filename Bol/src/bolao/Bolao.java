/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bolao;

/**
 *
 * @author TAIRON
 */
public class Bolao {

   private String name;
   private int id;
   private int pont;
   
   public void  Jogador(String nome){
       this.name = nome;
   }
   
   public String GetNome(){
       return this.name;
   }
    
   public void Senha(int senha){
       this.id = senha;
   }
   
   
   public int GetSenha(){
       return this.id;
   }
   
   public int pontuacao(){
       
       
   }
   
   
   
}
