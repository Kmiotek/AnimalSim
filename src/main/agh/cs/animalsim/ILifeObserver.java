package agh.cs.animalsim;

public interface ILifeObserver {
    void died(IMapElement object);

    void wasBorn(IMapElement object);
}
