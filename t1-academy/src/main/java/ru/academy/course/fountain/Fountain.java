package ru.academy.course.fountain;

public class Fountain {

    private final String name;
    private Integer force;
    private final Integer maxForce;
    private Boolean enabled;
    private Boolean isSuppliedWithWater;

    public Fountain(Integer maxForce, String name) {
        this.isSuppliedWithWater = Boolean.FALSE;
        this.enabled = Boolean.FALSE;
        this.maxForce = maxForce;
        this.name = name;
    }

    public void enable() {
        if (isSuppliedWithWater) {
            this.enabled = Boolean.TRUE;
        } else {
            System.out.println("Невозможно включить фонтан без обеспечением водой. Подключите фонтан к водопроводу.");
        }
    }

    public void disable() {
        this.enabled = Boolean.FALSE;
    }

    public void connectToWaterSupply() {
        isSuppliedWithWater = Boolean.TRUE;
    }

    public void disconnectFromWaterSupply() {
        isSuppliedWithWater = Boolean.TRUE;
        enabled = Boolean.FALSE;
    }

    public Integer increaseAndGetForce() {
        if (force == null) {
            force = 0;
        }
        if (enabled == Boolean.FALSE) {
            System.out.println("Фонтан выключен. Невозможно поднять мощность.");
            return force;
        }
        if (force.equals(maxForce)) {
            System.out.println("Фонтан бъет на максимум.");
        } else {
            force++;
        }

        return force;
    }

    public Integer decreaseAndGetForce() {
        if (force == null) {
            force = 0;
        }
        if (enabled == Boolean.FALSE) {
            System.out.println("Фонтан выключен. Невозможно снизить мощность.");
            return force;
        }
        if (force.equals(0)) {
            System.out.println("Фонтан бъет на минимум");
        } else {
            force--;
        }

        return force;
    }

    public Integer getMaxForce() {
        return maxForce;
    }

    public String getName() {
        return name;
    }

}
