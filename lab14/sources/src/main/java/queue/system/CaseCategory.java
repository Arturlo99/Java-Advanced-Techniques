package queue.system;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeDataView;
import javax.management.openmbean.CompositeType;
import java.beans.ConstructorProperties;

public class CaseCategory implements CompositeDataView {
    private String name;
    private String symbol;
    private int priority;
    private int counter;

    public CaseCategory(String name, String symbol, int priority){
        this.name = name;
        this.priority = priority;
        this.symbol = symbol;
        this.counter = 0;
    }

    public static CaseCategory from(CompositeData cd) {
        return new CaseCategory((String) cd.get("name"),
                (String) cd.get("symbol"),(int)cd.get("priority"),
                (int) cd.get("counter"));
    }

    @ConstructorProperties({ "name", "symbol", "priority", "counter"})
    public CaseCategory(String name, String symbol, int priority, int counter ){
        this.name = name;
        this.symbol = symbol;
        this.priority = priority;
        this.counter = counter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public CompositeData toCompositeData(CompositeType ct) {
        try {
            CompositeData cd = new CompositeDataSupport(ct, new String[] { "name", "symbol", "priority", "counter" },
                    new Object[] { name, symbol, priority, counter });
            assert ct.isValue(cd);
            return cd;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return name + " - " + priority;
    }

}
