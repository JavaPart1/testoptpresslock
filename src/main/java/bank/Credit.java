package bank;

import javax.persistence.*;

@Entity
public class Credit {
   @Id
   private long id;
   private float balance = 0F;
   
   @Version
   private int version;
      
   public Credit() {
   }
   
   public Credit(long id) {
      this.id = id;
   }
   
   public float getBalance() {
      return balance;
   }
   public void setBalance(float balance) {
      this.balance = balance;
   }
   public long getId() {
      return id;
   }   
}
