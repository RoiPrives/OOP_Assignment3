package Model.Utils;

public class Health {
    private int healthPool;
    private int healthAmount;

    public Health(int healthPool) {
        this.healthAmount = healthPool;
        this.healthPool = healthPool;
    }

    public int takeDamage(int damageTaken) {
        damageTaken = Math.max(0, healthAmount - damageTaken);
        damageTaken = Math.min(damageTaken , healthAmount);
        healthAmount -= damageTaken;
        return damageTaken;
    }

}
