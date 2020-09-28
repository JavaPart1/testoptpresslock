package bank;

import javax.persistence.*;

public class CreateCredit {
   public static void main(String[] args) {
      EntityManagerFactory emf = null;
      EntityManager em = null;
      try {
         emf = Persistence.createEntityManagerFactory("course");
         em = emf.createEntityManager();
         EntityTransaction tx = em.getTransaction();
         tx.begin();
         Credit credit = new Credit(1L);
         credit.setBalance(100F);
         em.persist(credit);
         tx.commit();
         System.out.println("Credit created");
      } finally {
         if (em != null)
            em.close();
         if (emf != null)
            emf.close();
      }
   }
}
