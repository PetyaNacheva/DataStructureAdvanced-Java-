import java.lang.management.CompilationMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MicrosystemImpl implements Microsystem {

    private Map<Integer, Computer> computers;

    public MicrosystemImpl() {
    this.computers = new HashMap<>();
    }

    @Override
    public void createComputer(Computer computer) {
        if(this.contains(computer.getNumber())){
            throw new IllegalArgumentException();
        }
        this.computers.put(computer.getNumber(),computer);
    }

    @Override
    public boolean contains(int number) {
        return this.computers.containsKey(number);
    }

    @Override
    public int count() {
      return  this.computers.size();
    }

    @Override
    public Computer getComputer(int number) {
        if(!this.contains(number)){
            throw new IllegalArgumentException();
        }
        return this.computers.get(number);
    }

    @Override
    public void remove(int number) {
        if(!this.contains(number)){
            throw new IllegalArgumentException();
        }
        this.computers.remove(number);
    }

    @Override
    public void removeWithBrand(Brand brand) {
       // for (Computer computer : computers.values()) {
        //this.computers.values().remove(); Премахваме докато итерираме
       // }
        Map<Integer, Computer>result =
        this.computers
                .values()
                .stream()
                .filter(c->!c.getBrand().equals(brand))
                .collect(Collectors.toMap(c->c.getNumber(),c->c));

        //ако работим с entrySet не с values
        //this.computers
        //        .entrySet()
        //        .stream()
        //        .filter(c->!c.getValue().getBrand().equals(brand))
        //        .collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

        if(this.count()==result.size()){
            throw new IllegalArgumentException();
        }

        this.computers=result;
    }

    @Override
    public void upgradeRam(int ram, int number) {
        Computer currComputer = this.getComputer(number);
        if(ram>currComputer.getRAM()){
            currComputer.setRAM(ram);
            this.computers.put(currComputer.getNumber(),currComputer);
        }

    }

    @Override
    public Iterable<Computer> getAllFromBrand(Brand brand) {
        return this.computers
                .values()
                .stream()
                .filter(c->c.getBrand().equals(brand))
                .sorted((l,r)->Double.compare(r.getPrice(),l.getPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Iterable<Computer> getAllWithScreenSize(double screenSize) {
        return this.computers
                .values()
                .stream()
                .filter(c->c.getScreenSize()==screenSize)
                .sorted((l,r)->Integer.compare(r.getNumber(), l.getNumber()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Iterable<Computer> getAllWithColor(String color) {
        return this.computers
                .values()
                .stream()
                .filter(c->c.getColor().equals(color))
                .sorted((l,r)->Double.compare(r.getPrice(),l.getPrice()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Iterable<Computer> getInRangePrice(double minPrice, double maxPrice) {
        return this.computers
                .values()
                .stream()
                .filter(c->c.getPrice()>=minPrice&&c.getPrice()<=maxPrice)
                .sorted((l,r)->Double.compare(r.getPrice(), l.getPrice()))
                .collect(Collectors.toUnmodifiableList());
    }
}
