package Utils;

public class Health {
    private int healthPool;
    private int healthAmount;

    public Health(int healthPool) {
        this.healthAmount = healthPool;
        this.healthPool = healthPool;
    }

    public void increaseHealthPool(int increaseHealthPool) {
        healthPool = Math.max(healthPool, healthPool + increaseHealthPool);
    }

    public void increaseHealthAmout(int increaseHealthAmount) {
        healthAmount = Math.min(healthPool, healthAmount + increaseHealthAmount);
    }
    public void fillHealthPool() {
        healthAmount = healthPool;
    }

    public int getHealthAmount() {
        return healthAmount;
    }
    public int getHealthPool() {
        return healthPool;
    }

    public int takeDamage(int damageTaken) {
        damageTaken = Math.max(0, damageTaken);//healthAmount - damageTaken);
        damageTaken = Math.min(damageTaken , healthAmount);
        healthAmount -= damageTaken;
        return damageTaken;
    }

    @Override
    public String toString() {
        return "health: " + healthAmount + "/" + healthPool;
    }
}
