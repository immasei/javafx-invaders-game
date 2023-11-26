package invaders.entities.Strategy;

public class FastStraight implements ShootBehaviour {

    @Override
    public double apply(double speed) {
        return speed*2;
    };
}
