package invaders.entities.Strategy;

public class SlowStraight implements ShootBehaviour {

    @Override
    public double apply(double speed) {
        return speed;
    }
}
