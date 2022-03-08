package models;

public class MiningAlgorithm {
    private String name;
    private Double hashRate;
    private String unit;

    public MiningAlgorithm(String name, Double hashRate, String unit) {
        this.name = name;
        this.hashRate = hashRate;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHashRate() {
        return hashRate;
    }

    public void setHashRate(Double hashRate) {
        this.hashRate = hashRate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
