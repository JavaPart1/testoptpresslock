package bank;

import javax.persistence.*;
import javax.swing.JOptionPane;

public class ChangeCreditPessimistic {
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

         String answer = JOptionPane
               .showInputDialog("Your current credit is "
                     + credit.getBalance() + ". Change with:");

         if (answer != null) {
            float amount = Float.parseFloat(answer);
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            credit = em.find(Credit.class, 1L,
                  LockModeType.PESSIMISTIC_WRITE);
            if ((credit.getBalance() + amount) >= 0F) {
               credit.setBalance(credit.getBalance() + amount);
               System.out.println("Credit changed");
            } else {
               System.out.println("Unsufficient credit");
            }
            tx.commit();
            em.close();
         }
      } finally {
         if (em != null) {
            em.close();
         }
         if (emf != null)
            emf.close();
      }
   }
}
