package bank;

import javax.persistence.*;
import javax.swing.JOptionPane;

public class ChangeCreditWithCheck {

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
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            emf.getCache().evict(Credit.class,
                  credit.getId());
            credit = em.find(Credit.class, credit.getId(),
                  LockModeType.PESSIMISTIC_WRITE);
            float amount = Float.parseFloat(answer);
            if ((credit.getBalance() + amount) >= 0F) {
               credit.setBalance(
                     credit.getBalance() + amount);
               System.out.println("Credit changed");
            } else {
               System.out.println("Unsufficient credit");
            }
            tx.commit();
            em.close();
         }
      } catch (OptimisticLockException ex) {
         JOptionPane.showMessageDialog(null, ex.getMessage(),
               "Error", JOptionPane.ERROR_MESSAGE);

      } finally {
         if (emf != null)
            emf.close();
      }
   }
}
