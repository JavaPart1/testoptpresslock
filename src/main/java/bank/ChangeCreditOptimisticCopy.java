package bank;

import javax.persistence.*;
import javax.swing.*;

public class ChangeCreditOptimisticCopy {
   public static void main(String[] args) {
      EntityManagerFactory emf = null;
      EntityManager em = null;
      try {
         emf = Persistence.createEntityManagerFactory("course");
         em = emf.createEntityManager();
         EntityTransaction tx = em.getTransaction();
         tx.begin();
         Credit credit = em.find(Credit.class, 1L);
         tx.commit();
         em.close();

         String answer = JOptionPane.showInputDialog(
               "Current balance: " + credit.getBalance()
                     + ". Change with: ");

         if (answer != null) {
            float amount = Float.parseFloat(answer);
            if ((credit.getBalance() + amount) >= 0F) {
               credit.setBalance(credit.getBalance() + amount);

               em = emf.createEntityManager();
               tx = em.getTransaction();
               tx.begin();
               em.merge(credit);
               tx.commit();
               em.close();
               System.out.println("Credit changed");
            } else {
               System.out.println("Unsufficient credit");
            }
         }
      } catch (OptimisticLockException ex) {
         JOptionPane.showMessageDialog(null, ex.getMessage(),
               "Error", JOptionPane.ERROR_MESSAGE);
      } finally {
         if (em != null)
            em.close();
         if (emf != null)
            emf.close();
      }
   }
}
