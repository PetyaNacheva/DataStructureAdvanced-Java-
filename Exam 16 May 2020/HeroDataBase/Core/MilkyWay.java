package core;

import models.Planet;
import models.Star;

import java.util.*;
import java.util.stream.Collectors;

public class MilkyWay implements Galaxy {
    private Map<Star, Set<Planet>> planetsByStars;
    private Map<Integer, Star> starsByIds;
    private Map<Integer, Planet> planetsByIds;
    private Set<Star> stars;

    public MilkyWay() {
        this.planetsByStars = new LinkedHashMap<>();
        this.starsByIds = new HashMap<>();
        this.planetsByIds = new HashMap<>();
        this.stars = new TreeSet<>(Comparator
                .comparingInt(Star::getLuminosity)
                .reversed());
    }

    @Override
    public void add(Star star) {
        if (this.planetsByStars.containsKey(star)) {
            throw new IllegalArgumentException();
        }

        this.planetsByStars.put(star, new TreeSet<>(
                Comparator.comparing(Planet::getDistanceFromStar)
                        .thenComparing(Planet::getMass)));

        this.starsByIds.put(star.getId(), star);
        this.stars.add(star);
    }

    @Override
    public void add(Planet planet, Star star) {
        if (!this.planetsByStars.containsKey(star)) {
            throw new IllegalArgumentException();
        }

        if (this.planetsByIds.containsKey(planet.getId())) {
            throw new IllegalArgumentException();
        }

        this.planetsByStars.get(star).add(planet);
        this.planetsByIds.put(planet.getId(), planet);
    }

    @Override
    public boolean contains(Planet planet) {
        return this.planetsByIds.containsKey(planet.getId());
    }

    @Override
    public boolean contains(Star star) {
        return this.starsByIds.containsKey(star.getId());
    }

    @Override
    public Star getStar(int id) {
        Star star = this.starsByIds.get(id);
        if (star == null) {
            throw new IllegalArgumentException();
        }
        return star;
    }

    @Override
    public Planet getPlanet(int id) {
        Planet planet = this.planetsByIds.get(id);
        if (planet == null) {
            throw new IllegalArgumentException();
        }
        return planet;
    }

    @Override
    public Star removeStar(int id) {
        Star star = getStar(id);

        Set<Planet> remove = this.planetsByStars.remove(star);

        for (Planet planet : remove) {
            this.planetsByIds.remove(planet.getId());
        }

        this.starsByIds.remove(star.getId());
        this.stars.remove(star);

        return star;
    }

    @Override
    public Planet removePlanet(int id) {
        Planet planet = getPlanet(id);

        this.planetsByIds.remove(id);

        for (Set<Planet> value : planetsByStars.values()) {
            if (value.removeIf(p -> p.getId() == id)) {
                break;
            }
        }

        return planet;
    }

    @Override
    public int countObjects() {
        return this.starsByIds.size() +
                this.planetsByIds.size();
    }

    @Override
    public Iterable<Planet> getPlanetsByStar(Star star) {
        Set<Planet> planets = this.planetsByStars.get(star);
        if (planets == null) {
            return new HashSet<>();
        }
        return planets;
    }

    @Override
    public Iterable<Star> getStars() {
        return this.stars;
    }

    @Override
    public Iterable<Star> getStarsInOrderOfDiscovery() {
        return this.planetsByStars.keySet();
    }

    @Override
    public Iterable<Planet> getAllPlanetsByMass() {

        return this.planetsByIds
                .values()
                .stream()
                .sorted(Comparator.comparing(Planet::getMass).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Planet> getAllPlanetsByDistanceFromStar(Star star) {
        Set<Planet> planets = this.planetsByStars.get(star);

        if (planets == null) {
            return new HashSet<>();
        }

        return planets.stream()
                .sorted(Comparator.comparing(Planet::getDistanceFromStar))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Star, Set<Planet>> getStarsAndPlanetsByOrderOfStarDiscoveryAndPlanetDistanceFromStarThenByPlanetMass() {
        return this.planetsByStars;
    }
}
